package com.northcastle.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author northcastle
 * @desc  查询结果的列与 Java 对象属性的对应关系
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JDBCColumnToAttribute {
    String value();
}
