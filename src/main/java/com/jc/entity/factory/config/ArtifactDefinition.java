package com.jc.entity.factory.config;

/**
 * @author Jevoncode
 * @since 2017-12-16
 * <p>
 * 对应配置jc-codegen-{verison}.xml的artifact标签
 */
public class ArtifactDefinition {
    /**
     * 模块名
     */
    private String id;


    /**
     * 描述
     */
    private String description;

    /**
     * 模块名前缀
     * <p>
     * 一般是多模块时使用，如springframework项目下的多模块，spring-core，spring-context，其中spring就是前缀
     */
    private String prefix;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
