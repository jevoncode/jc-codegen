package com.jc.domain;

import java.util.List;

/**
 * @author Jevoncode
 * @date 2017-12-16
 * <p>
 * Java中entity的定义, 对应着数据库的一张表
 * <p>
 * 此类的字段命名参考class类 @see java.lang.Class
 */
public class TableDefinition {
    /**
     * 文件名, 也是类的名字
     */
    private String classSimpleName;

    /**
     * 全限定名 com.jc.domain.TableDefinition
     */
    private String classCanonicalName;


    List<EntityField> entityFields;


    /**
     * 参考 @see java.lang.reflect.Field, 不是用Field的原因是Field是final类
     */
    static class EntityField {
        private String name;
        private Class<?> type;

    }
}
