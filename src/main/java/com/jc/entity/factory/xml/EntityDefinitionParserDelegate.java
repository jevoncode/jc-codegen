package com.jc.entity.factory.xml;


import com.jc.entity.factory.config.ArtifactDefinition;
import com.jc.entity.factory.config.EntityDefinition;
import com.jc.entity.factory.config.EntityDefinitionHolder;
import com.jc.entity.factory.config.GroupDefinition;
import com.jc.exception.GroupDefinitionException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntityDefinitionParserDelegate {
    public static final String CODEGEN_NAMESPACE_URI = "http://www.jevoncode.com/schema/codegen";

    public static final String TRUE_VALUE = "true";

    public static final String FALSE_VALUE = "false";



    public static final String ID_ATTRIBUTE = "id";

    public static final String AUTHOR_ATTRIBUTE = "author";


    public static final String DATE_ATTRIBUTE = "date";


    public static final String COMPANY_ATTRIBUTE = "company";


    public static final String PROJECT_DIR_ATTRIBUTE = "projectDir";

    public static final String MULTI_MODULE_ATTRIBUTE = "multiModule";

    public static final String DESC_DIR_ATTRIBUTE = "description";

    public static final String TABLE_NAME_ATTRIBUTE = "tableName";

    public static final String PRIMARY_KEY_ATTRIBUTE = "primaryKey";

    public static final String PREFIX_ATTRIBUTE = "prefix";

    public static final String OVERWRITE_ATTRIBUTE = "overwrite";



    private final XmlReaderContext readerContext;

    private final GroupDefinition groupDefinition = new GroupDefinition();

    public EntityDefinitionParserDelegate(XmlReaderContext readerContext) {
        Assert.notNull(readerContext, "XmlReaderContext must not be null");
        this.readerContext = readerContext;
    }

    public void initGroup(Element root) {
        populateGroup(root);
    }

    private void populateGroup(Element root) {
        String idStr = root.getAttribute(ID_ATTRIBUTE);
        String authorStr = root.getAttribute(AUTHOR_ATTRIBUTE);
        String dateStr = root.getAttribute(DATE_ATTRIBUTE);
        String companyStr = root.getAttribute(COMPANY_ATTRIBUTE);
        String projectDirStr = root.getAttribute(PROJECT_DIR_ATTRIBUTE);
        boolean multiModule = root.getAttribute(MULTI_MODULE_ATTRIBUTE)==null?false:TRUE_VALUE.equalsIgnoreCase(root.getAttribute(MULTI_MODULE_ATTRIBUTE));
        groupDefinition.setId(idStr);
        groupDefinition.setAuthor(authorStr);
        try {
            Date date = DateUtils.parseDate(dateStr,"yyyy-MM-dd");
            groupDefinition.setDate(date);
        } catch (ParseException e) {
            throw  new GroupDefinitionException(readerContext.getResource().getDescription(),"parse date error", e);
        }
        groupDefinition.setCompany(companyStr);
        groupDefinition.setProjectDir(projectDirStr);
        groupDefinition.setMultiModule(multiModule);
    }




    public boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || CODEGEN_NAMESPACE_URI.equals(namespaceUri));
    }

    public boolean isDefaultNamespace(Node node) {
        return isDefaultNamespace(getNamespaceURI(node));
    }

    public String getNamespaceURI(Node node) {
        return node.getNamespaceURI();
    }

    public EntityDefinitionHolder parseBeanDefinitionElement(Element ele) {

        ArtifactDefinition artifactDefinition = new ArtifactDefinition();
        String idStr = ele.getAttribute(ID_ATTRIBUTE);
        String desc = ele.getAttribute(DESC_DIR_ATTRIBUTE);
        String prefix = ele.getAttribute(PREFIX_ATTRIBUTE);
        artifactDefinition.setId(idStr);
        artifactDefinition.setDescription(desc);
        artifactDefinition.setPrefix(prefix);
        List<String> tableNames = new ArrayList<>();
        List<EntityDefinition> entityDefinitions = new ArrayList<>();
        EntityDefinitionHolder holder = new EntityDefinitionHolder(artifactDefinition,tableNames,entityDefinitions);


        NodeList nl = ele.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element entityELe = (Element) node;
                if (isDefaultNamespace(entityELe)) {
                    parseBeanDefinitionElement(entityELe,holder);
                }
            }
        }

        return holder;
    }

    public void parseBeanDefinitionElement(Element ele, EntityDefinitionHolder holder){
        String tableName = ele.getAttribute(TABLE_NAME_ATTRIBUTE);
        String primaryKey = ele.getAttribute(PRIMARY_KEY_ATTRIBUTE); //暂时仅支持单主键
        String prefix = ele.getAttribute(PREFIX_ATTRIBUTE);
        String overwrite = ele.getAttribute(OVERWRITE_ATTRIBUTE);
        EntityDefinition entityDefinition = new EntityDefinition();
        entityDefinition.setTableName(tableName);
        entityDefinition.setPrimaryKey(primaryKey);
        entityDefinition.setPrefix(prefix);
        entityDefinition.setOverwrite(TRUE_VALUE.equalsIgnoreCase(overwrite));
        entityDefinition.setArtifactDefinition(holder.getArtifactDefinition());
        entityDefinition.setGroupDefinition(groupDefinition);
        holder.addEntityDefinition(entityDefinition,tableName);
    }
}
