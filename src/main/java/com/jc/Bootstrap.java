package com.jc;

import com.jc.db.DataSource;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Bootstrap {

    private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        logger.info("启动代码生成工具");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

        DataSource dataSource = applicationContext.getBean(DataSource.class);
//        System.out.println(dataSource.getConnection());
        logger.debug(dataSource.getConnection().toString());
        logger.info("结束");
    }
}
