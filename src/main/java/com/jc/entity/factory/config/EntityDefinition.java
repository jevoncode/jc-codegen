package com.jc.entity.factory.config;

/**
 * @author Jevoncode
 * @since 2017-12-24
 * <p>
 * 对应配置jc-codegen-{verison}.xml的group标签
 */
public class EntityDefinition {

    private GroupDefinition groupDefinition;

    private ArtifactDefinition artifactDefinition ;


    /**
     * 表名
     */
    private String tableName;

    /**
     * 表描述
     */
    private String description;

    /**
     * 表的主键名
     */
    private String primaryKey;

    /**
     * 表名的前缀
     */
    private String prefix;

    /**
     * 是否覆盖代码
     */
    private boolean overwrite;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public GroupDefinition getGroupDefinition() {
        return groupDefinition;
    }

    public void setGroupDefinition(GroupDefinition groupDefinition) {
        this.groupDefinition = groupDefinition;
    }

    public ArtifactDefinition getArtifactDefinition() {
        return artifactDefinition;
    }

    public void setArtifactDefinition(ArtifactDefinition artifactDefinition) {
        this.artifactDefinition = artifactDefinition;
    }
}
