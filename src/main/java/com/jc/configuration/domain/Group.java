package com.jc.configuration.domain;

import java.util.Date;

/**
 * @author Jevoncode
 * @since 2017-12-16
 * <p>
 * 对应配置jc-codegen-{verison}.xml的group标签
 */
public class Group {

    /**
     * 根包的名称, 以"."间隔
     */
    private String id;

    /**
     * 作者
     */
    private String author;

    /**
     * 日期
     */
    private Date date;

    /**
     * 公司
     */
    private String company;

    /**
     * 项目路径
     */
    private String projectDir;


}
