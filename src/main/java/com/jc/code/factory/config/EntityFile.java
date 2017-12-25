package com.jc.code.factory.config;

import java.util.List;

public class EntityFile extends CodeFile{

    private List<EntityField> entityFieldList;



    public static class EntityField {


        /**
         * 全限定名 如: com.jc.code.factory.config.CodeFile
         */
        private String classCanonicalName;

        /**
         * 文件名, 也是类的名字, 如: CodeFile
         */
        private String classSimpleName;

        /**
         * 字段名, 对应数据库的字段, 如t_jc_user表中的user_password, 则字段名为对应的驼峰名 userPassword
         */
        private String fieldName;

        /**
         * 字段的注释, 读取数据库表结构字段的注释
         */
        private String comment;


        public String getClassCanonicalName() {
            return classCanonicalName;
        }

        public void setClassCanonicalName(String classCanonicalName) {
            this.classCanonicalName = classCanonicalName;
        }

        public String getClassSimpleName() {
            return classSimpleName;
        }

        public void setClassSimpleName(String classSimpleName) {
            this.classSimpleName = classSimpleName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

    public List<EntityField> getEntityFieldList() {
        return entityFieldList;
    }

    public void setEntityFieldList(List<EntityField> entityFieldList) {
        this.entityFieldList = entityFieldList;
    }
}
