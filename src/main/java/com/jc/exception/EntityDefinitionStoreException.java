package com.jc.exception;

public class EntityDefinitionStoreException extends  FatalEntityException{
    private String resourceDescription;

    private String beanName;


    /**
     * 创建EntityDefinitionStoreException
     * @param msg 详细信息（用于指明错误是什么）
     */
    public EntityDefinitionStoreException(String msg) {
        super(msg);
    }

    /**
     * 创建EntityDefinitionStoreException
     * @param msg 详细信息（用于指明错误是什么）
     * @param cause 根原因（可能为 {@code null}）
     */
    public EntityDefinitionStoreException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * 创建EntityDefinitionStoreException
     * @param resourceDescription 描述entity定义所在的资源
     * @param msg 详细信息（用于指明错误是什么）
     */
    public EntityDefinitionStoreException(String resourceDescription, String msg) {
        super(msg);
        this.resourceDescription = resourceDescription;
    }

    public EntityDefinitionStoreException(String resourceDescription, String msg, Throwable cause) {
        super(msg, cause);
        this.resourceDescription = resourceDescription;
    }

    public EntityDefinitionStoreException(String resourceDescription, String entityName, String msg) {
        this(resourceDescription, entityName, msg, null);
    }

    public EntityDefinitionStoreException(String resourceDescription, String entityName, String msg, Throwable cause) {
        super("Invalid entity definition with name '" + entityName + "' defined in " + resourceDescription + ": " + msg, cause);
        this.resourceDescription = resourceDescription;
        this.beanName = entityName;
    }


    public String getResourceDescription() {
        return this.resourceDescription;
    }

    public String getBeanName() {
        return this.beanName;
    }
}
