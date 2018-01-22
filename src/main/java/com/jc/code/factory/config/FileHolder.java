package com.jc.code.factory.config;

public class FileHolder {


    /**
     * 是否支持多模块
     */
    private final boolean multiModule;


    /**
     * 模块名前缀
     * <p>
     * 一般是多模块时使用，如springframework项目下的多模块，spring-core，spring-context，其中spring就是前缀
     */
    private final String artifactPrefix;

    /**
     * 分页类的class
     */
    private final String defaultPageClass;

    private final EntityFile entityFile;
    private final MapperFile mapperFile;
    private final ServiceFile serviceFile;


    public FileHolder(EntityFile entityFile, MapperFile mapperFile, ServiceFile serviceFile, boolean multiModule,String artifactPrefix,String defaultPageClass) {
        this.entityFile = entityFile;
        this.mapperFile = mapperFile;
        this.serviceFile = serviceFile;
        this.multiModule = multiModule;
        this.artifactPrefix = artifactPrefix;
        this.defaultPageClass = defaultPageClass;
    }


    public FileHolder(EntityFile entityFile, MapperFile mapperFile, ServiceFile serviceFile, boolean multiModule, String artifactPrefix, String defaultPageClass, String fileNamePrefix) {
        this.entityFile = entityFile;
        this.mapperFile = mapperFile;
        this.serviceFile = serviceFile;
        this.multiModule = multiModule;
        this.artifactPrefix = artifactPrefix;
        this.defaultPageClass = defaultPageClass;
        entityFile.setFileNamePrefix(fileNamePrefix);
        mapperFile.setFileNamePrefix(fileNamePrefix);
        serviceFile.setFileNamePrefix(fileNamePrefix);
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

    public String getDefaultPageClass() {
        return defaultPageClass;
    }
}
