package com.jc.entity.factory.context;

import com.jc.code.factory.config.FileHolder;
import com.jc.code.factory.support.MysqlTableParser;
import com.jc.code.factory.support.ParserTable;
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

    private Logger logger = LoggerFactory.getLogger(CodegenContext.class);

    private static final String DEFAULT_CONFIG = "jc-codegen-1.0.xml";

    private static final String TEMPLATE_ENTITY_JAVA = "entity.java.ftlh";
    private static final String TEMPLATE_EXCEPTION_JAVA = "exception.java.ftlh";
    private static final String TEMPLATE_MAPPER_JAVA = "mapper.java.ftlh";
    private static final String TEMPLATE_MAPPER_XML = "mapper.xml.ftlh";
    private static final String TEMPLATE_SERVICE_JAVA = "service.ftlh";
    private static final String TEMPLATE_SERVICE_IMPL_JAVA = "serviceImpl.ftlh";


    EntityDefinitionReader entityDefinitionReader = new XmlEntityDefinitionReader(this);
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
            String entityJava = null;
            String exceptionJava = null;
            String mapperJava = null;
            String mapperXml = null;
            String serviceJava = null;
            String serviceImplJava = null;
            try {
                entityJava = FreeMarkerTemplateUtils.processTemplateIntoString( freemarkerConfiguration.getTemplate(TEMPLATE_ENTITY_JAVA),fileHolder);
            } catch (Exception e) {
                logger.error("process template fail! template={}",TEMPLATE_ENTITY_JAVA,e);
                throw new FreemarkerException("process template fail! template="+TEMPLATE_ENTITY_JAVA,e);
            }
            logger.debug(entityJava);

            try {
                exceptionJava = FreeMarkerTemplateUtils.processTemplateIntoString( freemarkerConfiguration.getTemplate(TEMPLATE_EXCEPTION_JAVA),fileHolder);
            } catch (Exception e) {
                logger.error("process template fail! template={}",TEMPLATE_EXCEPTION_JAVA,e);
                throw new FreemarkerException("process template fail! template="+TEMPLATE_EXCEPTION_JAVA,e);
            }
            try {
                mapperJava = FreeMarkerTemplateUtils.processTemplateIntoString( freemarkerConfiguration.getTemplate(TEMPLATE_MAPPER_JAVA),fileHolder);
            } catch (Exception e) {
                logger.error("process template fail! template={}",TEMPLATE_MAPPER_JAVA,e);
                throw new FreemarkerException("process template fail! template="+TEMPLATE_MAPPER_JAVA,e);
            }
            try {
                mapperXml = FreeMarkerTemplateUtils.processTemplateIntoString( freemarkerConfiguration.getTemplate(TEMPLATE_MAPPER_XML),fileHolder);
            } catch (Exception e) {
                logger.error("process template fail! template={}",TEMPLATE_MAPPER_XML,e);
                throw new FreemarkerException("process template fail! template="+TEMPLATE_MAPPER_XML,e);
            }
            try {
                serviceJava = FreeMarkerTemplateUtils.processTemplateIntoString( freemarkerConfiguration.getTemplate(TEMPLATE_SERVICE_JAVA),fileHolder);
            } catch (Exception e) {
                logger.error("process template fail! template={}",TEMPLATE_SERVICE_JAVA,e);
                throw new FreemarkerException("process template fail! template="+TEMPLATE_SERVICE_JAVA,e);
            }
            try {
                serviceImplJava = FreeMarkerTemplateUtils.processTemplateIntoString( freemarkerConfiguration.getTemplate(TEMPLATE_SERVICE_IMPL_JAVA),fileHolder);
            } catch (Exception e) {
                logger.error("process template fail! template={}",TEMPLATE_SERVICE_IMPL_JAVA,e);
                throw new FreemarkerException("process template fail! template="+TEMPLATE_SERVICE_IMPL_JAVA,e);
            }
        }


    }

    private ParserTable createParserTable() {

        DataSource dataSource = SpringContextUtils.getBean(DataSource.class);
        Connection connection = dataSource.getConnection();
        return new MysqlTableParser(connection, this);
    }
}
