package com.northcastle.database;

import com.northcastle.utils.StringUtile;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

/**
 * @Author: northcastle
 * @CreateTime: 2023-03-24  10:11
 * @Description: TODO
 */
public class DatabaseTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        new DatabaseTest().addData();


    }


    // 0.读取配置文件
    public void readJdbcConfig(){
        JDBCProperties jdbcProperties = JDBCUtile.obtainJDBCProperties();
        System.out.println("jdbcProperties = " + jdbcProperties);
    }

    // 1.添加数据
    public void addData(){
        Connection connection = JDBCUtile.obtainConnection();
        System.out.println("connection = " + connection);
        //3、执行SQL
        boolean connectionOK = JDBCUtile.testConnection(connection);
        System.out.println("connectionOK = " + connectionOK);
        if (connectionOK){
            String insertOneSql = "insert into t100w(num,k1,k2) values (?,?,?)";
            Random random = new Random();
            long beginTime = System.currentTimeMillis();
            System.out.println("开始插入 100w 数据 : "+beginTime);
            int dataNum = 1000000;
            Object[][] paramsArray = new Object[dataNum][];
            for(int i = 0 ;i < dataNum;i++){
                Object[] params = {random.nextInt(10000), StringUtile.obtainRandomStr(4),StringUtile.obtainRandomStr(8)};
                paramsArray[i] = params;
            }

            int i = JDBCUtile.insertBatch(connection, insertOneSql, paramsArray, 80);
            System.out.println("插入成功多少条 ："+i);

            long endTime = System.currentTimeMillis();
            System.out.println("结束插入 100w 数据 : "+endTime);
            System.out.println("保存用时 ："+(endTime - beginTime));


        }else{
            System.out.println("连接失败！");
        }
        //4、关闭连接
        JDBCUtile.close(null,null,connection);
    }

    // 2.删除数据
    public void deleteData(){
        // 获取一个连接
        Connection connection = JDBCUtile.obtainConnection();
        System.out.println("connection = " + connection);
        //3、执行SQL
        boolean connectionOK = JDBCUtile.testConnection(connection);
        System.out.println("connectionOK = " + connectionOK);
        if (connectionOK){
            String deleteOneSql = "delete from t100w where id = ?";
            Object[] params = {11};
            int i = JDBCUtile.deleteOne(connection, deleteOneSql, params);
            System.out.println("i = " + i);

        }else{
            System.out.println("连接失败！");
        }
        //4、关闭连接
        JDBCUtile.close(null,null,connection);
    }

    // 3.更新数据
    public void updateData(){
        // 获取一个连接
        Connection connection = JDBCUtile.obtainConnection();
        System.out.println("connection = " + connection);
        //3、执行SQL
        boolean connectionOK = JDBCUtile.testConnection(connection);
        System.out.println("connectionOK = " + connectionOK);
        if (connectionOK){
            String updateSql = "update t100w set k1 = ?,k2 = ?,dt = now() where id = ?";
            Object[] params = {"aaa","bbb",12};
            int i = JDBCUtile.deleteOne(connection, updateSql, params);
            System.out.println("i = " + i);

        }else{
            System.out.println("连接失败！");
        }
        //4、关闭连接
        JDBCUtile.close(null,null,connection);
    }

    // 查询一个返回值对象
    public void queryOneObject(){
        // 获取一个连接
        Connection connection = JDBCUtile.obtainConnection();
        System.out.println("connection = " + connection);
        //3、执行SQL
        boolean connectionOK = JDBCUtile.testConnection(connection);
        System.out.println("connectionOK = " + connectionOK);
        if (connectionOK){
            String querySql = "select k1  from t100w where id < ?";
            Object[] params = {1002};
            Object resObj = JDBCUtile.queryObject(connection, querySql, params);
            System.out.println("resObj = " + resObj);

        }else{
            System.out.println("连接失败！");
        }
        //4、关闭连接
        JDBCUtile.close(null,null,connection);
    }

    /**
     * 查询某个类型的某一条数据
     */
    public void queryOneObjectClass(){
        // 获取一个连接
        Connection connection = JDBCUtile.obtainConnection();
        System.out.println("connection = " + connection);
        //3、执行SQL
        boolean connectionOK = JDBCUtile.testConnection(connection);
        System.out.println("connectionOK = " + connectionOK);
        if (connectionOK){
            //String querySql = "select id,num,k1,k2, DATE_FORMAT(dt,'%Y-%m-%d %H:%i:%s') dt  from t100w where id < ?";
            String querySql = "select id,num,k1,k2, dt  from t100w where id < ?";
            Object[] params = {10};
            List<T100wVO> resList = JDBCUtile.queryClassList(connection, querySql, params, T100wVO.class);
            for (T100wVO t100wVO : resList) {
                System.out.println("t100wVO = " + t100wVO);
                System.out.println("t100wVO.getDt() = " + t100wVO.getDt());
                LocalDateTime dt = t100wVO.getDt();
                LocalDate localDate = dt.toLocalDate();
                LocalTime localTime = dt.toLocalTime();
                System.out.println("localDate = " + localDate);
                System.out.println("localTime = " + localTime);
                System.out.println();
            }

        }else{
            System.out.println("连接失败！");
        }
        //4、关闭连接
        JDBCUtile.close(null,null,connection);
    }

    // 查询一个对象，通过注解进行匹配
    public void queryObjectWithAnnotation(){
        // 获取一个连接
        Connection connection = JDBCUtile.obtainConnection();
        System.out.println("connection = " + connection);
        //3、执行SQL
        boolean connectionOK = JDBCUtile.testConnection(connection);
        System.out.println("connectionOK = " + connectionOK);
        if (connectionOK){
            //String querySql = "select id,num,k1,k2, DATE_FORMAT(dt,'%Y-%m-%d %H:%i:%s') dt  from t100w where id < ?";
            String querySql = "select id,num,k1,k2, dt,k1 k1_bak  from t100w where id = ?";
            Object[] params = {100};
            T100wVO t100wVO = JDBCUtile.queryClassObjectWithAnnotation(connection, querySql, params, T100wVO.class);
            System.out.println("t100wVO = " + t100wVO);
        }else{
            System.out.println("连接失败！");
        }
        //4、关闭连接
        JDBCUtile.close(null,null,connection);

    }


}
