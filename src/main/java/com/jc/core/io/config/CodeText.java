package com.jc.core.io.config;

public class CodeText {

    /**
     * 文件路径（绝对路径）
     */
    private String path;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件内容
     */
    private String content;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
