package com.jc.code.factory.config;


/**
 * 此类的字段命名参考class类 {@link java.lang.Class}
 * @author Jevoncode
 * @date 2017-12-25
 */
public class CodeFile {


    /**
     * 文件名, 也是类的名字, 如: CodeFile
     */
    private String classSimpleName;

    /**
     * 全限定名 如: com.jc.code.factory.config.CodeFile
     */
    private String classCanonicalName;

    /**
     * 类的注释
     */
    private String description;

    /**
     * 类的注释里的作者
     */
    private String author;


    /**
     * 类的注释里的日期
     */
    private String date;


    /**
     * 类的groupId
     */
    private String groupId;


    /**
     * 类的ArtifactId (模块名)
     */
    private String artifactId;






}