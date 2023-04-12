package com.northcastle.database;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * @Author: northcastle
 * @CreateTime: 2023-03-24  10:52
 * @Description: jdbc 的基础工具类
 */
public class JDBCUtile {

    /**
     * 上来就加载驱动
     * 驱动是固定的，直接字符串
     */
    static {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取数据库连接的配置信息
     * 配置文件的读取顺序：
     *   classpath:nc-jdbc.properties
     *   classpath:jdbc/nc-jdbc.properties
     * 采用覆盖的策略，后面的会覆盖前面的
     * @return
     */
    public static JDBCProperties obtainJDBCProperties(){
        JDBCProperties jdbcProperties = null;

        try {
            // 读取 properties 文件到程序中
            String[] filePathArray = {"nc-jdbc.properties", "jdbc/nc-jdbc.properties"};
            ClassLoader classLoader = JDBCUtile.class.getClassLoader();
            boolean findFileFlag = false;
            for (String filePath : filePathArray) {
                InputStream resourceAsStream = classLoader.getResourceAsStream(filePath);
                if (resourceAsStream != null){
                    findFileFlag = true;
                    System.out.println(filePath + "配置文件读取成功");
                    Properties properties = new Properties();
                    properties.load(resourceAsStream);
                    jdbcProperties = new JDBCProperties();
                    jdbcProperties.setUrl(properties.getProperty("nc.jdbc.url"));
                    jdbcProperties.setUsername(properties.getProperty("nc.jdbc.username"));
                    jdbcProperties.setPassword(properties.getProperty("nc.jdbc.password"));
                }
            }
            if (!findFileFlag){
                System.out.println("未找到可用的配置文件！"+Arrays.asList(filePathArray));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return jdbcProperties;
    }

    /**
     * 使用默认的配置参数获取连接
     * @return
     */
    public static Connection obtainConnection(){
        Connection connection = null;
        try {
            // 读取配置文件，创建连接对象
            JDBCProperties jdbcProperties = obtainJDBCProperties();
            connection = DriverManager.getConnection(jdbcProperties.getUrl(),jdbcProperties.getUsername(),jdbcProperties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return connection;
    }

    /**
     * 根据自己传入的参数，获取连接
     * @param url
     * @param username
     * @param password
     * @return
     */
    private static Connection obtainConnection(String url,String username,String password){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return connection;
    }

    /**
     * 执行关闭动作
     * @param resultSet
     * @param statement
     * @param connection
     */
    public static void close(ResultSet resultSet, Statement statement,Connection connection){
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试是否连接成功
     * @param connection
     * @return
     */
    public static boolean testConnection(Connection connection){
        // 连接成功的状态
        boolean connectionOK = false;
        if (connection != null){

            try {
                String testSql = "select 1 from dual";
                PreparedStatement preparedStatement = connection.prepareStatement(testSql);
                ResultSet resultSet = preparedStatement.executeQuery(testSql);
                if (resultSet.next()){
                    int res = resultSet.getInt(1);
                    if (res == 1){
                        connectionOK = true;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return connectionOK;

    }

    /**
     * 新增一条数据
     * @param connection
     * @param sql
     * @param params
     * @return
     */
    public static int insertOne(Connection connection,String sql,Object[] params){
        int res = 0;
        // 先进行链接校验
        boolean b = testConnection(connection);
        if (b){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                putParams(preparedStatement,params);

                // 直接执行，返回的是执行成功的条数
                res = preparedStatement.executeUpdate();
                // 关闭preparedStatement
                close(null,preparedStatement,null);

            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }

        return res;
    }

    /**
     * 批量插入
     * @param connection
     * @param sql
     * @param paramsArray
     * @param batchLength
     * @return
     */
    public static int insertBatch(Connection connection,String sql,Object[][] paramsArray,int batchLength){
        int res = 0;
        // 先进行链接校验
        boolean b = testConnection(connection);
        if (b){
            try {
                // 关闭自动提交
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                if (paramsArray != null){
                    int paramsArrayLength = paramsArray.length;
                    System.out.println("一共有【"+paramsArrayLength+"】条数据");
                    for(int m = 0;m < paramsArrayLength;m++){
                        Object[] params = paramsArray[m];

                        if (params != null){
                            // 放当前这一条的参数
                            for (int n = 0;n < params.length;n++){
                                preparedStatement.setObject(n+1,params[n]);
                            }
                            // 添加到批量的组里边
                            preparedStatement.addBatch();
                        }

                        // 攒够了一车了，直接开走
                        if ( (m+1) % batchLength == 0){
                            int[] resNum = preparedStatement.executeBatch();
                            preparedStatement.clearBatch();
                            res += Arrays.stream(resNum).sum();
                            System.out.println("第【"+(m+1)+"】条批量写入完成 "+System.currentTimeMillis());
                        }
                    }

                    // 最后一车
                    if (paramsArrayLength % batchLength != 0){
                        int[] resNum = preparedStatement.executeBatch();
                        preparedStatement.clearBatch();
                        res += Arrays.stream(resNum).sum();
                        System.out.println("最后一批写入完成");
                    }

                }
                // 手动提交事物
                connection.commit();
                // 关闭 prepareStatement
                close(null,preparedStatement,null);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return res;
    }


    /**
     * 删除某条数据
     * @param connection
     * @param sql
     * @param params
     * @return
     */
    public static int deleteOne(Connection connection,String sql,Object[] params){
        int res = 0;
        // 判断连接是否正常
        boolean b = testConnection(connection);
        if (b){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                putParams(preparedStatement,params);
                res = preparedStatement.executeUpdate();
                close(null,preparedStatement,null);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }

        return res;
    }

    /**
     * 更新某条数据 ： 与删除数据是一模一样的
     * @param connection
     * @param sql
     * @param params
     * @return
     */
    public static int updateOne(Connection connection,String sql,Object[] params){
        int res = 0;
        // 判断连接是否正常
        boolean b = testConnection(connection);
        if (b){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                putParams(preparedStatement,params);
                res = preparedStatement.executeUpdate();
                close(null,preparedStatement,null);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }
        return res;
    }

    /**
     * 查询结果 : 最通用的一个方法，返回集合list，所有的都可以进行处理
     * @param connection
     * @param sql
     * @param params
     * @return
     */
    public static List<Map> queryList(Connection connection,String sql,Object[] params){
        List<Map> resList = new ArrayList<>();
        boolean b = testConnection(connection);
        if (b){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                putParams(preparedStatement,params);
                // 执行查询，获取结果集
                ResultSet resultSet = preparedStatement.executeQuery();
                // 根据结果集，获取元数据 ： 列的属性
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                // 遍历结果集，进行数据封装
                while (resultSet.next()){
                    HashMap<String, Object> map = new HashMap<>();
                    for(int i = 0; i < columnCount; i++){
                        // getColumnLabel 返回的是SQL中的别名；
                        // getColumnName 返回的是表的列明;
                        // 索引是从1 开始的
                        String columnLabel = metaData.getColumnLabel(i+1);
                        Object columnValue = resultSet.getObject(columnLabel);

                        map.put(columnLabel,columnValue);
                    }
                    resList.add(map);
                }
                close(resultSet,preparedStatement,null);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return resList;
    }


    /**
     * 查询，返回某个类的一个对象，仅支持对应的属性对应
     * @param connection
     * @param sql : 要求SQL 中的返回值列必须和类的属性名一一对应
     * @param params
     * @param type
     * @return
     * @param <T>
     */
    public static <T> T queryClassObject(Connection connection,String sql,Object[] params,Class<T> type){
        T t = null;
        boolean b = testConnection(connection);
        if (b){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                putParams(preparedStatement,params);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    // 实际上就是来创建一个对象
                    t = type.newInstance();
                    // 拿到返回结果集的元数据
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    // 遍历元数据，通过反射的方式进行对象属性的附值
                    int columnCount = metaData.getColumnCount();
                    for (int i = 0; i < columnCount;i++){
                        String columnLabel = metaData.getColumnLabel(i + 1);
                        Object columnValue = resultSet.getObject(columnLabel);
                        // 通过反射，直接操作对象的属性值,爆破
                        try {
                            Field field = type.getDeclaredField(columnLabel);
                            field.setAccessible(true);
                            field.set(t,columnValue);
                        }catch (Exception e) {
                            // 跳过这个异常，直接执行下一个赋值
                        }
                    }
                }
                // 关闭
                close(resultSet,preparedStatement,null);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return t;
    }

    /**
     * 查询，返回某个类的一个对象，支持 通过注解 进行属性的匹配
     * @param connection
     * @param sql : 要求SQL 中的返回值列名 与属性一致 or 与对应的 注解一致
     *              先匹配属性，再匹配注解
     * @param params
     * @param type
     * @return
     * @param <T>
     */
    public static <T> T queryClassObjectWithAnnotation(Connection connection,String sql,Object[] params,Class<T> type){
        T t = null;
        boolean b = testConnection(connection);
        if (b){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                putParams(preparedStatement,params);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    // 实际上就是来创建一个对象
                    t = type.newInstance();
                    // 拿到返回结果集的元数据
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    // 遍历元数据，通过反射的方式进行对象属性的附值
                    int columnCount = metaData.getColumnCount();
                    // 1.第一个遍历，先把 能对上的属性的值进行对应上
                    // 保存匹配成功的属性
                    ArrayList<Field> fieldMapSucessList = new ArrayList<>();
                    // 保存 返回的列 没有匹配成功的
                    HashSet<String> columnLabelFailedSet = new HashSet<>();
                    for (int i = 0; i < columnCount;i++){
                        String columnLabel = metaData.getColumnLabel(i + 1);
                        Object columnValue = resultSet.getObject(columnLabel);
                        // 通过反射，直接操作对象的属性值,爆破
                        try {
                            // 先匹配是否存在属性可以对应上
                            Field field = type.getDeclaredField(columnLabel);
                            field.setAccessible(true);
                            field.set(t,columnValue);
                            // 还可以再进行优化一下，对属性的优化，保存一下已经被匹配上了的对象
                            fieldMapSucessList.add(field);

                        }catch (Exception e) {
                            // 如果没有属性，再进行注解的匹配，一个字段一个字段的进行注解匹配
                            columnLabelFailedSet.add(columnLabel);
                        }
                    }

                    // 2.再执行一次循环，进行注解的匹配
                    Field[] allFields = type.getDeclaredFields();
                    if (allFields != null && allFields.length > 0){
                        for (String columnLabel : columnLabelFailedSet) {
                            for (Field field : allFields) {
                                // 当这个值不包含在已经被匹配的列表中的时候，再进行注解的匹配
                                if (!fieldMapSucessList.contains(field)){
                                    JDBCColumnToAttribute annotation = field.getAnnotation(JDBCColumnToAttribute.class);
                                    if (annotation != null){
                                        String value = annotation.value();
                                        // 匹配上了注解的值 ： 如果有多个重复的会把所有匹配上的都赋值
                                        if (value.equals(columnLabel)){
                                            try {
                                                field.setAccessible(true);
                                                Object columnValue = resultSet.getObject(columnLabel);
                                                field.set(t,columnValue);
                                            }catch (Exception e1){
                                                e1.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // 关闭
                close(resultSet,preparedStatement,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return t;
    }


    /**
     * 查询：返回某个类型的对象集合,仅支持属性的匹配
     * @param connection
     * @param sql
     * @param params
     * @param type
     * @return
     * @param <T>
     */
    public static <T> List<T> queryClassList(Connection connection,String sql,Object[] params,Class<T> type){

        List<T> resList = new ArrayList<>();

        boolean b = testConnection(connection);
        if (b){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                putParams(preparedStatement,params);
                ResultSet resultSet = preparedStatement.executeQuery();
                // 拿到返回结果集的元数据
                ResultSetMetaData metaData = resultSet.getMetaData();
                // 遍历元数据，通过反射的方式进行对象属性的附值
                int columnCount = metaData.getColumnCount();
                while (resultSet.next()){
                    // 实际上就是来创建一个对象
                    T t = type.newInstance();
                    for (int i = 0; i < columnCount;i++){
                        String columnLabel = metaData.getColumnLabel(i + 1);
                        Object columnValue = resultSet.getObject(columnLabel);
                        // 通过反射，直接操作对象的属性值,爆破
                        try {
                            Field field = type.getDeclaredField(columnLabel);
                            field.setAccessible(true);
                            field.set(t,columnValue);
                        }catch (Exception e) {
                            // 跳过这个异常，直接执行下一个赋值
                            e.printStackTrace();
                        }
                    }

                    // 把对象放到集合中来
                    resList.add(t);
                }
                // 关闭
                close(resultSet,preparedStatement,null);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }


        return resList;
    }

    /**
     *
     * @param connection
     * @param sql 要求SQL 中的返回值列名 与属性一致 or 与对应的 注解一致
     *            先匹配属性，再匹配注解
     * @param params
     * @param type
     * @return
     * @param <T>
     */
    public static <T> List<T> queryClassListWithAnnotation(Connection connection,String sql,Object[] params,Class<T> type){
        List<T> resList = new ArrayList<>();
        boolean b = testConnection(connection);
        if (b){
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                System.out.println(sql);
                System.out.println(Arrays.asList(params));
                putParams(preparedStatement,params);
                System.out.println(preparedStatement);
                ResultSet resultSet = preparedStatement.executeQuery();
                // 拿到返回结果集的元数据
                ResultSetMetaData metaData = resultSet.getMetaData();
                // 遍历元数据，通过反射的方式进行对象属性的附值
                int columnCount = metaData.getColumnCount();
                while (resultSet.next()){
                    T t = type.newInstance();
                    // 1.第一个遍历，先把 能对上的属性的值进行对应上
                    // 保存匹配成功的属性
                    ArrayList<Field> fieldMapSucessList = new ArrayList<>();
                    // 保存 返回的列 没有匹配成功的
                    HashSet<String> columnLabelFailedSet = new HashSet<>();
                    for (int i = 0; i < columnCount;i++){
                        String columnLabel = metaData.getColumnLabel(i + 1);
                        Object columnValue = resultSet.getObject(columnLabel);
                        // 通过反射，直接操作对象的属性值,爆破
                        try {
                            // 先匹配是否存在属性可以对应上
                            Field field = type.getDeclaredField(columnLabel);
                            field.setAccessible(true);
                            field.set(t,columnValue);
                            // 还可以再进行优化一下，对属性的优化，保存一下已经被匹配上了的对象
                            fieldMapSucessList.add(field);

                        }catch (Exception e) {
                            // 如果没有属性，再进行注解的匹配，一个字段一个字段的进行注解匹配
                            columnLabelFailedSet.add(columnLabel);
                        }
                    }
                    // 2.再执行一次循环，进行注解的匹配
                    Field[] allFields = type.getDeclaredFields();
                    if (allFields != null && allFields.length > 0){
                        for (String columnLabel : columnLabelFailedSet) {
                            for (Field field : allFields) {
                                // 当这个值不包含在已经被匹配的列表中的时候，再进行注解的匹配
                                if (!fieldMapSucessList.contains(field)){
                                    JDBCColumnToAttribute annotation = field.getAnnotation(JDBCColumnToAttribute.class);
                                    if (annotation != null){
                                        String value = annotation.value();
                                        // 匹配上了注解的值 ： 如果有多个重复的会把所有匹配上的都赋值
                                        if (value.equals(columnLabel)){
                                            try {
                                                field.setAccessible(true);
                                                Object columnValue = resultSet.getObject(columnLabel);
                                                field.set(t,columnValue);
                                            }catch (Exception e1){
                                                e1.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    resList.add(t);
                }
                close(resultSet,preparedStatement,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resList;
    }



    /**
     * 查询返回一个值
     * @param connection
     * @param sql
     * @param params
     * @return
     */
    public static Object queryObject(Connection connection,String sql,Object[] params){
        Object resObj = null;
        boolean b = testConnection(connection);
        if (b){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                // 设置参数
                putParams(preparedStatement,params);
                // 执行查询，获取结果集
                ResultSet resultSet = preparedStatement.executeQuery();
                // 遍历结果集，进行数据封装
                if (resultSet.next()){
                     resObj = resultSet.getObject(1);
                }

                close(resultSet,preparedStatement,null);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return resObj;
    }

    /**
     * 抽取出来的方法 ： 给SQL 设置参数的
     * @param preparedStatement
     * @param params
     */
    private static void putParams(PreparedStatement preparedStatement,Object[] params){
        if (params != null && params.length > 0){
            for (int i = 0 ;i < params.length;i++){
                try {
                    preparedStatement.setObject(i+1,params[i]);
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
