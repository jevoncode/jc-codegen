package com.jc;

import com.alibaba.fastjson.JSON;
import com.jc.db.DataSource;
import com.jc.entity.factory.context.CodegenContext;
import com.jc.entity.factory.support.EntityDefinitionReader;
import com.jc.entity.factory.xml.XmlEntityDefinitionReader;
import com.jc.utils.JsonUtils;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class Bootstrap {

    private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        logger.info("启动代码生成工具");
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

//        DataSource dataSource = applicationContext.getBean(DataSource.class);
//        System.out.println(dataSource.getConnection());
//        logger.debug(dataSource.getConnection().toString());

//        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
//
//        Resource resource = defaultResourceLoader.getResource("jc-codegen-1.0.xml");
//        try {
//            System.out.println(resource.getURL().toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        CodegenContext codegenContext = new CodegenContext();
        codegenContext.refresh();

        logger.info(JsonUtils.toJSONString(codegenContext.getEntityDefinitionMap()));


        logger.info("结束");
    }
}
