package com.jc.configuration.domain;

/**
 * @author Jevoncode
 * @date 2017-12-16
 * <p>
 * 对应配置jc-codegen-{verison}.xml的table标签
 */
public class Table {

    /**
     * 表名
     */
    private String id;

    /**
     * 表描述
     */
    private String description;

    /**
     * 表的主键名
     */
    private String dbKey;

    /**
     * 表名的前缀
     */
    private String prefix;

    /**
     * 是否覆盖代码
     */
    private boolean overwrite;
}
