package com.jc.code.factory.config;

import java.util.ArrayList;
import java.util.List;

public class MapperFile extends CodeFile{


    List<MapperColumn> mapperColumns = new ArrayList<>();

    public void addColumn(MapperColumn mapperColumn) {
        mapperColumns.add(mapperColumn);
    }


    public static class MapperColumn{


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


       public String getProperty() {
           return property;
       }

       public void setProperty(String property) {
           this.property = property;
       }

       public String getJdbcType() {
           return jdbcType;
       }

       public void setJdbcType(String jdbcType) {
           this.jdbcType = jdbcType;
       }

       public String getColumn() {
           return column;
       }

       public void setColumn(String column) {
           this.column = column;
       }
   }
}
