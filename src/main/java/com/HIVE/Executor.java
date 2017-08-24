package com.HIVE;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2016-06-01.
 */
public class Executor {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        Hive hive = (Hive) applicationContext.getBean("hive");
        hive.run();
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);
    }
}

