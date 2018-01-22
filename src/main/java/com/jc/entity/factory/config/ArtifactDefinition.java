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
}
