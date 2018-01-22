package ${serviceFile.groupId}.${serviceFile.artifactId}.service;

import java.util.Map;
import java.util.List;

import ${serviceFile.groupId}.${serviceFile.artifactId}.entity.${serviceFile.classSimpleName};
import ${defualtPageClass};  //分页dto

<#assign pageIndex=defualtPageClass?last_index_of(".")+1/>

/**
<#if entityFile.description??>
* @description ${serviceFile.description}
</#if>
* @author ${serviceFile.author}
* @since ${serviceFile.date}
*/
public interface ${serviceFile.classSimpleName}Service {

    int insert(${serviceFile.classSimpleName} model);
    
    int insertBatch(List<${serviceFile.classSimpleName}> modelList);

    int update(${serviceFile.classSimpleName} model);
    
    int updateSelective(${serviceFile.classSimpleName} model);

    ${serviceFile.classSimpleName} getBy${serviceFile.primarykey.fieldName?cap_first}(${serviceFile.primarykey.classSimpleName} ${serviceFile.primarykey.fieldName});

    ${defualtPageClass?substring(pageIndex)?cap_first} queryList(${defualtPageClass?substring(pageIndex)?cap_first} page);
    
    int count(Map<String,Object> params);

    //不分页使用的list(注意：存风险查全库)
    List<${serviceFile.classSimpleName}> findList(Map<String,Object> params);

}
