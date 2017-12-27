package com.jc.code.factory.config;

public class FileHolder {


    private final EntityFile entityFile;
    private final MapperFile mapperFile;
    private final ServiceFile serviceFile;


    public FileHolder(EntityFile entityFile, MapperFile mapperFile, ServiceFile serviceFile) {
        this.entityFile = entityFile;
        this.mapperFile = mapperFile;
        this.serviceFile = serviceFile;
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
}