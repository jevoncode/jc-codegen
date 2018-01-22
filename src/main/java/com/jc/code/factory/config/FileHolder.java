package com.jc.code.factory.config;

public class FileHolder {


    private final boolean multiModule;
    private final String artifactPrefix;

    private final EntityFile entityFile;
    private final MapperFile mapperFile;
    private final ServiceFile serviceFile;


    public FileHolder(EntityFile entityFile, MapperFile mapperFile, ServiceFile serviceFile, boolean multiModule,String artifactPrefix) {
        this.entityFile = entityFile;
        this.mapperFile = mapperFile;
        this.serviceFile = serviceFile;
        this.multiModule = multiModule;
        this.artifactPrefix = artifactPrefix;
    }

    public EntityFile getEntityFile() {
        return entityFile;
    }

    public MapperFile getMapperFile() {
        return mapperFile;
    }

    public ServiceFile getServiceFile() {
        return serviceFile;
    }


    public boolean isMultiModule() {
        return multiModule;
    }

    public String getArtifactPrefix() {
        return artifactPrefix;
    }
}
