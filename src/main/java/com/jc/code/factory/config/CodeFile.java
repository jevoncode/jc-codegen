package com.jc.code.factory.config;


/**
 * 此类的字段命名参考class类 {@link java.lang.Class}
 * @author Jevoncode
 * @since 2017-12-25
 */
public class CodeFile {

    private EntityField primarykey;

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

    /**
     * 文件根路径(绝对路径)
     */
    private String projectDir;


    /**
     * 文件名前缀（为了兼容老项目，新生成的类名（文件名）可能需要不同的前缀）
     */
    private String fileNamePrefix;

    public String getClassSimpleName() {
        if(getFileNamePrefix()!=null && !"".equals(getFileNamePrefix().trim()) && classSimpleName!=null){
            return getFileNamePrefix()+classSimpleName;
        }
        return classSimpleName;
    }

    public void setClassSimpleName(String classSimpleName) {
        this.classSimpleName = classSimpleName;
    }

    public String getClassCanonicalName() {
        if(getFileNamePrefix()!=null && !"".equals(getFileNamePrefix().trim()) && classCanonicalName!=null){
            return classCanonicalName.substring(0,classCanonicalName.lastIndexOf("."))+getClassSimpleName();
        }
        return classCanonicalName;
    }

    public void setClassCanonicalName(String classCanonicalName) {
        this.classCanonicalName = classCanonicalName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public EntityField getPrimarykey() {
        return primarykey;
    }

    public void setPrimarykey(EntityField primarykey) {
        this.primarykey = primarykey;
    }

    public String getProjectDir() {
        return projectDir;
    }

    public void setProjectDir(String projectDir) {
        this.projectDir = projectDir;
    }

    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

    public void setFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
    }
}
