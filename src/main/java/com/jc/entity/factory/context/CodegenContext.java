package com.jc.entity.factory.context;

import com.jc.code.factory.config.EntityFile;
import com.jc.code.factory.config.FileHolder;
import com.jc.code.factory.support.MysqlTableParser;
import com.jc.code.factory.support.ParserTable;
import com.jc.core.io.CodeWriter;
import com.jc.core.io.DefaultCodeWriter;
import com.jc.database.support.DataSource;
import com.jc.entity.factory.config.EntityDefinition;
import com.jc.entity.factory.config.EntityDefinitionHolder;
import com.jc.entity.factory.support.EntityDefinitionReader;
import com.jc.entity.factory.xml.XmlEntityDefinitionReader;
import com.jc.exception.FreemarkerException;
import com.jc.utils.JsonUtils;
import com.jc.utils.SpringContextUtils;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CodegenContext extends DefaultResourceLoader {

    private static final  String NAME_DELIM = "-";
    private static final  String PATH_SEPARATOR = "/";


    private Logger logger = LoggerFactory.getLogger(CodegenContext.class);

    private static final String DEFAULT_CONFIG = "jc-codegen-1.0.xml";

    private static final String TEMPLATE_ENTITY_JAVA = "entity.java.ftlh";
    private static final String TEMPLATE_EXCEPTION_JAVA = "exception.java.ftlh";
    private static final String TEMPLATE_MAPPER_JAVA = "mapper.java.ftlh";
    private static final String TEMPLATE_MAPPER_XML = "mapper.xml.ftlh";
    private static final String TEMPLATE_SERVICE_JAVA = "service.ftlh";
    private static final String TEMPLATE_SERVICE_IMPL_JAVA = "serviceImpl.ftlh";


    EntityDefinitionReader entityDefinitionReader = new XmlEntityDefinitionReader(this);
    CodeWriter codeWriter = new DefaultCodeWriter();
    ParserTable parserTable = null;


    /**
     * entity definition对象的map, key是tableName
     */
    private final Map<String, EntityDefinition> entityDefinitionMap = new ConcurrentHashMap<String, EntityDefinition>(64);

    public int getEntityDefinitionCount() {
        return entityDefinitionMap.size();
    }

    public void registerEntityDefinition(EntityDefinitionHolder holder) {

        List<String> tableNames = holder.getTableNames();
        List<EntityDefinition> entityDefinitions = holder.getEntityDefinitions();

        for (int i = 0; i < tableNames.size(); i++) {
            entityDefinitionMap.put(tableNames.get(i), entityDefinitions.get(i));
        }
    }

    public void refresh() {
        int entityCounts = entityDefinitionReader.loadEntityDefinitions(DEFAULT_CONFIG);

        logger.debug("refresh finish, loads entity {}", entityCounts);

    }

    public Map<String, EntityDefinition> getEntityDefinitionMap() {
        return entityDefinitionMap;
    }

    public void gen() {
        parserTable = createParserTable();
        List<FileHolder> fileHolders = parserTable.parse();

        Configuration freemarkerConfiguration = (Configuration) SpringContextUtils.getBean("freeMarkerConfiguration");

        for(FileHolder fileHolder:fileHolders){
            logger.debug(JsonUtils.toJSONString(fileHolder));
            EntityFile entityFile = fileHolder.getEntityFile();
            String projectDir = fileHolder.getEntityFile().getProjectDir();
            String groupId = fileHolder.getEntityFile().getGroupId();
            String artifactId = fileHolder.getEntityFile().getArtifactId();
            String path = constructPath(projectDir,groupId,artifactId,fileHolder.isMultiModule(),fileHolder.getArtifactPrefix());
            String entityJava = null;
            String exceptionJava = null;
            String mapperJava = null;
            String mapperXml = null;
            String serviceJava = null;
            String serviceImplJava = null;

            //Entity.java文件
            try {
                entityJava = FreeMarkerTemplateUtils.processTemplateIntoString( freemarkerConfiguration.getTemplate(TEMPLATE_ENTITY_JAVA),fileHolder);
                codeWriter.write(path+"entity/",entityFile.getFileName(),entityJava);
            } catch (Exception e) {
                logger.error("process template fail! template={}",TEMPLATE_ENTITY_JAVA,e);
                throw new FreemarkerException("process template fail! template="+TEMPLATE_ENTITY_JAVA,e);
            }

            //Exception.java文件
            try {
                exceptionJava = FreeMarkerTemplateUtils.processTemplateIntoString( freemarkerConfiguration.getTemplate(TEMPLATE_EXCEPTION_JAVA),fileHolder);
                codeWriter.write(path+"exception/",entityFile.getClassSimpleName()+"Exception.java",exceptionJava);
            } catch (Exception e) {
                logger.error("process template fail! template={}",TEMPLATE_EXCEPTION_JAVA,e);
                throw new FreemarkerException("process template fail! template="+TEMPLATE_EXCEPTION_JAVA,e);
            }

            //Mapper.java文件
            try {
                mapperJava = FreeMarkerTemplateUtils.processTemplateIntoString( freemarkerConfiguration.getTemplate(TEMPLATE_MAPPER_JAVA),fileHolder);
                codeWriter.write(path+"mapper/",entityFile.getClassSimpleName()+"Mapper.java",mapperJava);
            } catch (Exception e) {
                logger.error("process template fail! template={}",TEMPLATE_MAPPER_JAVA,e);
                throw new FreemarkerException("process template fail! template="+TEMPLATE_MAPPER_JAVA,e);
            }

            //Mapper.xml文件
            try {
                mapperXml = FreeMarkerTemplateUtils.processTemplateIntoString( freemarkerConfiguration.getTemplate(TEMPLATE_MAPPER_XML),fileHolder);
                codeWriter.write(path+"mapper/",entityFile.getClassSimpleName()+"Mapper.xml",mapperXml);
            } catch (Exception e) {
                logger.error("process template fail! template={}",TEMPLATE_MAPPER_XML,e);
                throw new FreemarkerException("process template fail! template="+TEMPLATE_MAPPER_XML,e);
            }

            //Service.java文件
            try {
                serviceJava = FreeMarkerTemplateUtils.processTemplateIntoString( freemarkerConfiguration.getTemplate(TEMPLATE_SERVICE_JAVA),fileHolder);
                codeWriter.write(path+"service/",entityFile.getClassSimpleName()+"Service.java",serviceJava);
            } catch (Exception e) {
                logger.error("process template fail! template={}",TEMPLATE_SERVICE_JAVA,e);
                throw new FreemarkerException("process template fail! template="+TEMPLATE_SERVICE_JAVA,e);
            }


            //ServiceImpl.java文件
            try {
                serviceImplJava = FreeMarkerTemplateUtils.processTemplateIntoString( freemarkerConfiguration.getTemplate(TEMPLATE_SERVICE_IMPL_JAVA),fileHolder);
                codeWriter.write(path+"service/",entityFile.getClassSimpleName()+"ServiceImpl.java",serviceImplJava);
            } catch (Exception e) {
                logger.error("process template fail! template={}",TEMPLATE_SERVICE_IMPL_JAVA,e);
                throw new FreemarkerException("process template fail! template="+TEMPLATE_SERVICE_IMPL_JAVA,e);
            }
        }


    }


    @Deprecated
    private String constructPath(String projectDir, String groupId, String artifactId) {
        StringBuilder path = new StringBuilder();
        if(projectDir.lastIndexOf(PATH_SEPARATOR)==projectDir.length()-1){
            path.append(projectDir).append("/src/main/java/").append(groupId.replaceAll("\\.",PATH_SEPARATOR)).append(PATH_SEPARATOR).append(artifactId);
        }else{
            path.append(projectDir).append(PATH_SEPARATOR).append("/src/main/java/").append(groupId.replaceAll("\\.",PATH_SEPARATOR)).append(PATH_SEPARATOR).append(artifactId);
        }
        return path.append(PATH_SEPARATOR).toString();
    }

    @Deprecated
    private String constructPath(String projectDir, String groupId, String artifactId,boolean isMultiModule) {
        StringBuilder path = new StringBuilder();
        path.append(projectDir);
        if(projectDir.lastIndexOf(PATH_SEPARATOR)!=projectDir.length()-1)
            path.append(PATH_SEPARATOR);

        if(isMultiModule)
            path.append(artifactId).append(PATH_SEPARATOR);
        path.append("src/main/java/").append(groupId.replaceAll("\\.",PATH_SEPARATOR)).append(PATH_SEPARATOR).append(artifactId);

        return path.append(PATH_SEPARATOR).toString();
    }

    private String constructPath(String projectDir, String groupId, String artifactId, boolean isMultiModule, String artifactPrefix) {
        StringBuilder path = new StringBuilder();
        path.append(projectDir);
        if(projectDir.lastIndexOf(PATH_SEPARATOR)!=projectDir.length()-1)
            path.append(PATH_SEPARATOR);

        if(isMultiModule) {
            if(StringUtils.isNotBlank(artifactPrefix)) {
                path.append(artifactPrefix).append(NAME_DELIM);
            }
            path.append(artifactId).append(PATH_SEPARATOR);
        }
        path.append("src/main/java/").append(groupId.replaceAll("\\.",PATH_SEPARATOR)).append(PATH_SEPARATOR).append(artifactId);

        return path.append(PATH_SEPARATOR).toString();
    }


    private ParserTable createParserTable() {

        DataSource dataSource = SpringContextUtils.getBean(DataSource.class);
        Connection connection = dataSource.getConnection();
        return new MysqlTableParser(connection, this);
    }
}
