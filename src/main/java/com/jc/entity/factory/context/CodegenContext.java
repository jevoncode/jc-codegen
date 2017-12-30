package com.jc.entity.factory.context;

import com.jc.code.factory.config.FileHolder;
import com.jc.code.factory.support.MysqlTableParser;
import com.jc.code.factory.support.ParserTable;
import com.jc.database.support.DataSource;
import com.jc.entity.factory.config.EntityDefinition;
import com.jc.entity.factory.config.EntityDefinitionHolder;
import com.jc.entity.factory.support.EntityDefinitionReader;
import com.jc.entity.factory.xml.XmlEntityDefinitionReader;
import com.jc.utils.JsonUtils;
import com.jc.utils.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CodegenContext extends DefaultResourceLoader {

    private Logger logger = LoggerFactory.getLogger(CodegenContext.class);

    private static final String DEFAULT_CONFIG = "jc-codegen-1.0.xml";


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

        for(FileHolder fileHolder:fileHolders){
            logger.debug(JsonUtils.toJSONString(fileHolder));


        }


    }

    private ParserTable createParserTable() {

        DataSource dataSource = SpringContextUtils.getBean(DataSource.class);
        Connection connection = dataSource.getConnection();
        return new MysqlTableParser(connection, this);
    }
}
