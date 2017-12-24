package com.jc.entity.factory.config;

import org.springframework.util.Assert;

import java.util.List;

public class EntityDefinitionHolder {

    private final ArtifactDefinition artifactDefinition;

    private final List<String> tableNames;

    private final List<EntityDefinition> entityDefinitions;


    public EntityDefinitionHolder(ArtifactDefinition artifactDefinition, List<String> tableNames, List<EntityDefinition> entityDefinitions) {
        this.artifactDefinition = artifactDefinition;
        this.tableNames = tableNames;
        this.entityDefinitions = entityDefinitions;
    }

    public ArtifactDefinition getArtifactDefinition() {
        return artifactDefinition;
    }

    public List<String> getTableNames() {
        return tableNames;
    }

    public List<EntityDefinition> getEntityDefinitions() {
        return entityDefinitions;
    }

    public void addEntityDefinition(EntityDefinition entityDefinition,String tableName) {
        Assert.notNull(entityDefinition,"entityDefinition should not be null");
        Assert.notNull(tableName,"tableName should not be null");
        entityDefinitions.add(entityDefinition);
        tableNames.add(tableName);
    }

}
