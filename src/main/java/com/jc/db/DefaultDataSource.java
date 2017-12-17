package com.jc.db;

import com.jc.configuration.domain.DataSourceConfiguration;
import com.jc.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DefaultDataSource implements DataSource {

    private static Logger logger = LoggerFactory.getLogger(DefaultDataSource.class);


    @Autowired
    private DataSourceConfiguration dataSourceConfiguration;

    private boolean init;

    @Override
    public Connection getConnection() {
        if (!init) {
            init();
        }


        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dataSourceConfiguration.getUrl(), dataSourceConfiguration.getUsername(), dataSourceConfiguration.getPassword());
        } catch (SQLException e) {
            logger.error("get database connection faile. dataSourceConfiguration={}", JsonUtils.toJSONNoFeatures(dataSourceConfiguration), e);
            throw new DataSourceException(e);
        }
        return conn;
    }

    private synchronized void init() {
        if (!init) {
            if (dataSourceConfiguration == null)
                throw new IllegalArgumentException("数据库配置异常, dataSourceConfiguration=" + JsonUtils.toJSONNoFeatures(dataSourceConfiguration));

            DriverManager.setLogWriter(new PrintWriter(System.out));

            try {
                Class.forName(dataSourceConfiguration.getDriver());
            } catch (ClassNotFoundException e) {
                logger.error("init datasource faile. dataSourceConfiguration={}", JsonUtils.toJSONNoFeatures(dataSourceConfiguration), e);
                throw new DataSourceException(e);
            }
            init = true;
        }

    }
}
