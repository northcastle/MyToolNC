package com.northcastle.test;

import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author: northcastle
 * @CreateTime: 2023-02-13  09:42
 * @Description: Logback 的学习使用类-可删除
 */
public class LogbackTest {
    public static void main(String[] args) throws InterruptedException {
        // 获取日志记录器
        ch.qos.logback.classic.Logger logger =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(LogbackTest.class);



        for (int i = 0; i < 100; i++) {
            logger.trace("trace Java从入门到精通");
            logger.debug("debug Java从入门到精通");
            logger.info("info Java从入门到精通");
            logger.warn("warn Java从入门到精通");
            logger.error("error Java从入门到精通");
        }




    }
}
