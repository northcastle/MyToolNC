package com.northcastle.database;

import com.alibaba.fastjson.JSON;

import java.time.LocalDateTime;

/**
 * 案例对应的数据表结构
 * mysql> desc t100w;
 * +-------+-------------+------+-----+-------------------+----------------+
 * | Field | Type        | Null | Key | Default           | Extra          |
 * +-------+-------------+------+-----+-------------------+----------------+
 * | id    | int(11)     | NO   | PRI | NULL              | auto_increment |
 * | num   | int(11)     | NO   |     | NULL              |                |
 * | k1    | varchar(20) | NO   |     | NULL              |                |
 * | k2    | varchar(20) | NO   |     | NULL              |                |
 * | dt    | datetime    | NO   |     | CURRENT_TIMESTAMP |                |
 * +-------+-------------+------+-----+-------------------+----------------+
 * 5 rows in set (0.00 sec)
 *
 *
 * 数据案例
 * mysql> select * from t100w where id < 10;
 * +----+------+------+----------+---------------------+
 * | id | num  | k1   | k2       | dt                  |
 * +----+------+------+----------+---------------------+
 * |  1 |    0 | 000  | 000      | 2023-03-24 11:08:47 |
 * |  2 |    1 | 111  | 111      | 2023-03-24 11:28:28 |
 * |  3 |    2 | 222  | 222      | 2023-03-24 11:35:22 |
 * |  4 |    3 | 333  | 333      | 2023-03-24 11:42:52 |
 * |  5 | 3848 | ilD7 | XcvrpTKf | 2023-03-24 11:50:27 |
 * |  6 | 5432 | pmQK | JUehfGWR | 2023-03-24 11:50:28 |
 * |  7 | 1937 | gGep | 9oh3I1gU | 2023-03-24 11:50:28 |
 * |  8 | 6075 | K7vc | qgWpMHaP | 2023-03-24 11:50:28 |
 * |  9 | 9520 | 5H7S | d0nfM0cU | 2023-03-24 11:50:28 |
 * +----+------+------+----------+---------------------+
 *
 *
 */


/**
 * @Author: northcastle
 * @CreateTime: 2023-03-27  13:57
 * @Description: 对应 t100w 对象的表
 */
public class T100wVO {

    // 这个注解是无效的，因为先匹配了属性的名称
    @JDBCColumnToAttribute("k1_bak")
    private Integer id;
    private Integer num;
    private String k1;
    private String k2;


    /**
     * 当前使用的mysql驱动版本是 8.0.32
     * 对应的 mysql 中的 datetime 类型的数据
     * 对应的 Java 中的 就是 Java8 中的 LocalDateTime
     *
     *
     * 如果使用的mysql 驱动版本是 5.x 的话，对应的是 java.util.Date
     */
    private LocalDateTime dt;

    /**
     * 测试使用注解进行属性的对应
     */
    @JDBCColumnToAttribute("k1_bak")
    private String k1Bak;



    public T100wVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getK1() {
        return k1;
    }

    public void setK1(String k1) {
        this.k1 = k1;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getK2() {
        return k2;
    }

    public void setK2(String k2) {
        this.k2 = k2;
    }

    public LocalDateTime getDt() {
        return dt;
    }

    public void setDt(LocalDateTime dt) {
        this.dt = dt;
    }

    public String getK1Bak() {
        return k1Bak;
    }

    public void setK1Bak(String k1Bak) {
        this.k1Bak = k1Bak;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
