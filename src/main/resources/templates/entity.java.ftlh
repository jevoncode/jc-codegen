package ${entityFile.groupId}.${entityFile.artifactId}.entity;

<#if entityFile.entityFieldList??>
    <#list entityFile.entityFieldList as entity>
    <#if !entity.classCanonicalName?contains('java.lang')>
import ${entity.classCanonicalName};
    </#if>
    </#list>
</#if>

/**
<#if entityFile.description??>
 * @description ${entityFile.description}
</#if>
 * @author ${entityFile.author}
 * @since ${entityFile.date}
 */
public class ${entityFile.classSimpleName} {
    
    <#list entityFile.entityFieldList as entity>
    // ${entity.comment}
    private ${entity.classSimpleName} ${entity.fieldName};
    </#list>

    <#list entityFile.entityFieldList as entity>
    public ${entity.classSimpleName} get${entity.fieldName?cap_first}() {
        return ${entity.fieldName};
    }
    
    public void set${entity.fieldName?cap_first}(${entity.classSimpleName} ${entity.fieldName}) {
        this.${entity.fieldName} = ${entity.fieldName};
    }

    </#list>
}