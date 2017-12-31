<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<#assign _left='#{'>
<#assign _right='}'>
<mapper namespace="${mapperFile.groupId}.${mapperFile.artifactId}.mapper.${mapperFile.classSimpleName}Mapper">
     
        
    <resultMap id="rowMapper" type="${mapperFile.groupId}.${mapperFile.artifactId}.entity.${mapperFile.classSimpleName}">
    <#list mapperFile.mapperColumns as column>
        <result property="${column.property}" jdbcType="${column.jdbcType}" column="${column.column}"/>
    </#list>
    </resultMap> 
     
    <sql id="BASE_COLUMN_LIST">
        <#list mapperFile.mapperColumns as column>
        ${column.column}<#sep>,</#sep>
        </#list>
    </sql>

    <insert id="insert" parameterType="${mapperFile.groupId}.${mapperFile.artifactId}.entity.${mapperFile.classSimpleName}" >
        INSERT INTO ${mapperFile.tableName}
        <trim prefix="(" suffix=")" suffixOverrides="," >
            <#list mapperFile.mapperColumns as column>
            <if test="${column.property} != null" >
                ${column.column}<#sep>,</#sep>
            </if>
            </#list>
        </trim>
        <trim prefix="VALUES (" suffix=")" suffixOverrides="," >
            <#list mapperFile.mapperColumns as column>
            <if test="${column.property} != null" >
                ${_left}${column.property}${_right}<#sep>,</#sep>
            </if>
            </#list>
        </trim>
    </insert>

    <insert id="insertBatch" parameterType="java.util.List" >
        INSERT INTO ${mapperFile.tableName} (
            <#list mapperFile.mapperColumns as column>
              ${column.column}<#sep>,</#sep>
            </#list>
        )VALUES
        <foreach collection="list" item="item" index="index" separator="," >  
            (
                <#list mapperFile.mapperColumns as column>
                ${_left}item.${column.property}${_right}<#sep>,</#sep>
                </#list>
            )
        </foreach>
    </insert>
    

    

    <!-- 危险操作，不提供自动代码生成
    <delete id="delete">
        DELETE FROM ${mapperFile.tableName} WHERE ${mapperFile.primaryColumn.column} = ${_left}${mapperFile.primaryColumn.property}${_right}
    </delete>
    -->
    
    <update id="update" parameterType="${mapperFile.groupId}.${mapperFile.artifactId}.entity.${mapperFile.classSimpleName}" >
        <#assign primaryColumn = '${mapperFile.primaryColumn.column}' >
        UPDATE ${mapperFile.tableName} SET
            <#list mapperFile.mapperColumns as column>
            <#if primaryColumn != '${column.column}' >
                ${column.column}=${_left}${column.property}${_right}<#sep>,</#sep>
            </#if>
            </#list>
        WHERE
            ${mapperFile.primaryColumn.column} = ${_left}${mapperFile.primaryColumn.property}${_right}
    </update>
    
 
    
    <update id="updateSelective" parameterType="${mapperFile.groupId}.${mapperFile.artifactId}.entity.${mapperFile.classSimpleName}" >
        <#assign primaryColumn = '${mapperFile.primaryColumn.column}' >
        UPDATE ${mapperFile.tableName}
            <set>
                <#list mapperFile.mapperColumns as column>
                <#if primaryColumn != '${column.column}' >
                <if test="${column.property} != null" >
                    ${column.column}=${_left}${column.property}${_right}<#sep>,</#sep>
                </if>
                </#if>
                </#list>
            </set>
        WHERE
            ${mapperFile.primaryColumn.column} = ${_left}${mapperFile.primaryColumn.property}${_right}
    </update>

    <select id="getBy${mapperFile.primaryColumn.property?cap_first}" resultMap="rowMapper">
        SELECT 
            <include refid="BASE_COLUMN_LIST" />
        FROM ${mapperFile.tableName}
        WHERE
          ${mapperFile.primaryColumn.column} = ${_left}${mapperFile.primaryColumn.property}${_right}
    </select>

    <select id="queryList" resultMap="rowMapper">
        SELECT 
            <include refid="BASE_COLUMN_LIST" /> 
        FROM ${mapperFile.tableName}
        <trim prefix="WHERE" prefixOverrides="AND |OR ">
	        <#list mapperFile.mapperColumns as column>
            <#if column.numerical>
            <if test="params.${column.property} != null">
                and ${column.column} = ${_left}params.${column.property}${_right}
            </if>
            <#else>
            <if test="params.${column.property} != null and params.${column.property} != ''">
                and ${column.column} = ${_left}params.${column.property}${_right}
            </if>
            </#if>
	        </#list>
	     </trim>
    </select>

    <select id="count" resultType="int">
        SELECT COUNT(0)
        FROM ${mapperFile.tableName}
        WHERE
            1 = 1
            <#list mapperFile.mapperColumns as column>
            <#if column.numerical>
            <if test="${column.property} != null">
                and ${column.column} = ${_left}${column.property}${_right}
            </if>
            <#else>
            <if test="${column.property} != null and ${column.property} != ''">
                and ${column.column} = ${_left}${column.property}${_right}
            </if>
            </#if>
	        </#list>
    </select>



    <select id="findList" resultMap="rowMapper">
        SELECT
        <include refid="BASE_COLUMN_LIST" />
        FROM ${mapperFile.tableName}
        <trim prefix="WHERE" prefixOverrides="AND |OR ">
            <#list mapperFile.mapperColumns as column>
            <#if column.numerical>
            <if test="${column.property} != null">
                and ${column.column} = ${_left}${column.property}${_right}
            </if>
            <#else>
            <if test="${column.property} != null and ${column.property} != ''">
                and ${column.column} = ${_left}${column.property}${_right}
            </if>
            </#if>
            </#list>
        </trim>
    </select>
	 
</mapper>

