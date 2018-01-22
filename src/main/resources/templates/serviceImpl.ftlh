package ${serviceFile.groupId}.${serviceFile.artifactId}.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import ${serviceFile.groupId}.${serviceFile.artifactId}.mapper.${serviceFile.classSimpleName}Mapper;
import ${serviceFile.groupId}.${serviceFile.artifactId}.entity.${serviceFile.classSimpleName};
import ${serviceFile.groupId}.${serviceFile.artifactId}.service.${serviceFile.classSimpleName}Service;


import ${defualtPageClass};  //分页dto

<#assign pageIndex=defualtPageClass?last_index_of(".")+1/>


/**
<#if entityFile.description??>
* @description ${serviceFile.description}
</#if>
* @author ${serviceFile.author}
* @since ${serviceFile.date}
*/
@Service
public class ${serviceFile.classSimpleName}ServiceImpl implements ${serviceFile.classSimpleName}Service{

    @Autowired
    private ${serviceFile.classSimpleName}Mapper ${serviceFile.classSimpleName?uncap_first}Mapper;
    
    @Override
    public int insert(${serviceFile.classSimpleName} model) {
        return this.${serviceFile.classSimpleName?uncap_first}Mapper.insert(model);
    }

    @Override
    public int insertBatch(List<${serviceFile.classSimpleName}> modelList) {
        return this.${serviceFile.classSimpleName?uncap_first}Mapper.insertBatch(modelList);
    }

	@Override
    public int update(${serviceFile.classSimpleName} model) {
        ${serviceFile.classSimpleName} old = this.${serviceFile.classSimpleName?uncap_first}Mapper.getBy${serviceFile.primarykey.fieldName?cap_first}(model.get${serviceFile.primarykey.fieldName?cap_first}());
		if(old == null){
			return 0;
		}
        //理应将old输出日志，保证当时操作时数据是这样
        BeanUtils.copyProperties(model,old);
		return this.${serviceFile.classSimpleName?uncap_first}Mapper.update(old);
    }

    @Override
    public int updateSelective(${serviceFile.classSimpleName} model) {
        ${serviceFile.classSimpleName} old = this.${serviceFile.classSimpleName?uncap_first}Mapper.getBy${serviceFile.primarykey.fieldName?cap_first}(model.get${serviceFile.primarykey.fieldName?cap_first}());
		if(old != null)
        	return this.${serviceFile.classSimpleName?uncap_first}Mapper.updateSelective(model);
		return 0;
    }

    @Override
    public ${serviceFile.classSimpleName} getBy${serviceFile.primarykey.fieldName?cap_first}(${serviceFile.primarykey.classSimpleName} ${serviceFile.primarykey.fieldName}){
        ${serviceFile.classSimpleName} model = this.${serviceFile.classSimpleName?uncap_first}Mapper.getBy${serviceFile.primarykey.fieldName?cap_first}(${serviceFile.primarykey.fieldName});
		return model;
    }

    @Override
    public ${defualtPageClass?substring(pageIndex)?cap_first} queryList(${defualtPageClass?substring(pageIndex)?cap_first} page) {
        List<${serviceFile.classSimpleName}> list = this.${serviceFile.classSimpleName?uncap_first}Mapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.${serviceFile.classSimpleName?uncap_first}Mapper.count(params);
    }

    @Override
    public List<${serviceFile.classSimpleName}> findList(Map<String,Object> params){
        return this.${serviceFile.classSimpleName?uncap_first}Mapper.findList(params);
    }
}
