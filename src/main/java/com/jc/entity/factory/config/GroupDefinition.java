package com.jc.entity.factory.config;

import java.util.Date;

/**
 * @author Jevoncode
 * @since 2017-12-16
 * <p>
 * 对应配置jc-codegen-{verison}.xml的group标签
 */
public class GroupDefinition {


    /**
     * 根包的名称, 以"."间隔
     */
    private String id;

    /**
     * 作者
     */
    private String author;

    /**
     * 日期
     */
    private Date date;

    /**
     * 公司
     */
    private String company;

    /**
     * 项目路径
     */
    private String projectDir;

    /**
     * 是否是多模块项目
     */
    private boolean multiModule;

    /**
     * 分页类的class
     */
    private String defaultPageClass;

    /**
     * 文件名前缀（为了兼容老项目，新生成的类名（文件名）可能需要不同的前缀）
     */
    private String fileNamePrefix;

    public boolean isMultiModule() {
        return multiModule;
    }

    public void setMultiModule(boolean multiModule) {
        this.multiModule = multiModule;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProjectDir() {
        return projectDir;
    }

    public void setProjectDir(String projectDir) {
        this.projectDir = projectDir;
    }

    public String getDefaultPageClass() {
        return defaultPageClass;
    }

    public void setDefaultPageClass(String defaultPageClass) {
        this.defaultPageClass = defaultPageClass;
    }

    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

    public void setFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
    }
}
