package ${entityFile.groupId}.${entityFile.artifactId}.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import ${entityFile.groupId}.${entityFile.artifactId}.entity.${entityFile.classSimpleName};
import com.jc.codegen.dto.Page;  //分页dto

/**
<#if entityFile.description??>
* @description ${entityFile.description}
</#if>
* @author ${entityFile.author}
* @date ${entityFile.date}
*/
@Repository
public interface ${entityFile.classSimpleName}Mapper{

	int insert(${entityFile.classSimpleName} model);
	
	int insertBatch(List<${entityFile.classSimpleName}> modelList);

	
	int update(${entityFile.classSimpleName} model);
	
	int updateSelective(${entityFile.classSimpleName} model);

	${entityFile.classSimpleName} getBy${entityFile.primarykey.fieldName?cap_first}(${entityFile.primarykey.classSimpleName} ${entityFile.primarykey.fieldName});

	//分页使用的list
	List<${entityFile.classSimpleName}> queryList(Page page);

	int count(Map<String,Object> params);

	//不分页使用的list(注意：存风险查全库)
	List<${entityFile.classSimpleName}> findList(Map<String,Object> params);
}