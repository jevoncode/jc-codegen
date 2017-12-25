package com.jc.code.factory.config;

import java.util.List;

public class MapperFile extends CodeFile{


    List<MapperColumn> mapperColumns;


    static class MapperColumn{


        /**
         * mapper文件resultMap配置
         * 参考mapper.xml文件
         */
        private String property;
        /**
         * mapper文件resultMap配置
         * 参考mapper.xml文件
         * 如 VARCHAR
         */
        private String jdbcType;

        /**
         * mapper文件resultMap配置
         * 表结构的字段
         * 参考mapper.xml文件
         * 如 user_address
         */
        private String column;


    }
}
