package ${entityFile.groupId}.${entityFile.artifactId}.exception;


/**
<#if entityFile.description??>
* @description ${entityFile.description}
</#if>
* @author ${entityFile.author}
* @date ${entityFile.date}
*/
public class ${entityFile.classSimpleName}Exception extends  RuntimeException{


    public ${entityFile.classSimpleName}Exception(String message) {
        super(message);
    }
    
}
