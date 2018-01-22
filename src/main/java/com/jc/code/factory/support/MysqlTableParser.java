package com.jc.code.factory.support;

import com.jc.code.factory.config.*;
import com.jc.code.factory.utils.CamelCaseUtils;
import com.jc.entity.factory.config.ArtifactDefinition;
import com.jc.entity.factory.config.EntityDefinition;
import com.jc.entity.factory.config.GroupDefinition;
import com.jc.entity.factory.context.CodegenContext;
import com.jc.exception.PrimaryKeyException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MysqlTableParser extends AbstractTableParser {


    private static Logger logger = LoggerFactory.getLogger(MysqlTableParser.class);

    private static final String DOT = ".";

    public MysqlTableParser(Connection connection, CodegenContext codegenContext) {
        super(connection, codegenContext);
    }

    @Override
    public List<FileHolder> parse() {
        Date executeDate = new Date();
        List<FileHolder> fileHolders = new ArrayList<>();

        // 获取数据库的元信息
        try {

            for (Map.Entry<String, EntityDefinition> entry : codegenContext.getEntityDefinitionMap().entrySet()) {

                String tableName = entry.getKey();
                EntityDefinition entityDefinition = entry.getValue();
                ArtifactDefinition artifactDefinition = entityDefinition.getArtifactDefinition();
                GroupDefinition groupDefinition = entityDefinition.getGroupDefinition();
                String defaultPageClass = groupDefinition.getDefualtPageClass();
                boolean multiModule = groupDefinition.isMultiModule();
                String artifactPrefix = artifactDefinition.getPrefix();
                String tablePrefix = entityDefinition.getPrefix();


                ResultSet rs = connection.prepareStatement(
                        "SELECT * FROM " + tableName + " WHERE 1=2")
                        .executeQuery();

                ResultSetMetaData rsMetaData = rs.getMetaData();

                //entity文件
                EntityFile entityFile = new EntityFile();
                entityFile.setProjectDir(groupDefinition.getProjectDir());
                entityFile.setClassSimpleName(CamelCaseUtils.parseSnakeCase(tableName.replaceFirst(tablePrefix, "")));
                String classCanonicalName4Entity = groupDefinition.getId() + DOT + artifactDefinition.getId() + DOT + entityFile.getClassSimpleName();
                entityFile.setClassCanonicalName(classCanonicalName4Entity);
                entityFile.setArtifactId(artifactDefinition.getId());
                entityFile.setAuthor(groupDefinition.getAuthor());
                String date = null;
                if (groupDefinition.getDate() != null) {
                    try {
                        date = DateFormatUtils.format(groupDefinition.getDate(), "yyyy-MM-dd");
                    } catch (Exception e) {
                        logger.error("group date is invalid, groupDate={}", groupDefinition.getDate(), e);
                    }
                }
                if (date == null) {
                    date = DateFormatUtils.format(executeDate, "yyyy-MM-dd");
                }
                entityFile.setDate(date);
                entityFile.setDescription(entityDefinition.getDescription());
                entityFile.setGroupId(groupDefinition.getId());

                //mapper文件
                MapperFile mapperFile = new MapperFile();
                BeanUtils.copyProperties(entityFile, mapperFile);
                mapperFile.setTableName(tableName);

                //server文件
                ServiceFile serviceFile = new ServiceFile();
                BeanUtils.copyProperties(entityFile, serviceFile);
                
                String pkName = extractPKName(tableName);


                for (int i = 0; i < rsMetaData.getColumnCount(); i++) {
                    String column = rsMetaData.getColumnName(i + 1); // 列名、字段名
                    int sqlType = rsMetaData.getColumnType(i + 1);  //@see java.sql.Types
                    int columnSize = rsMetaData.getColumnDisplaySize(i + 1); //字段长度, 如varchar(50) 的50
                    boolean nullable = rsMetaData.isNullable(i + 1) == 0 ? false : true;
                    String comment = extractComment(tableName, column);

                    //entity字段
                    EntityField entityField = new EntityField();
                    String classCanonicalName = JavaTypeResolver.parseClassCanonicalName(sqlType);
                    entityField.setClassCanonicalName(classCanonicalName);
                    entityField.setClassSimpleName(classCanonicalName.substring(classCanonicalName.lastIndexOf(".")+1));
                    entityField.setComment(comment);
                    entityField.setFieldName(CamelCaseUtils.parseSnakeCase2LowerCase(column));
                    entityField.setColumnSize(columnSize);
                    entityField.setNullable(nullable);
                    entityFile.addField(entityField);
                    if(pkName.equals(column)){
                        entityFile.setPrimarykey(entityField);
                    }

                    //mapper字段
                    MapperFile.MapperColumn mapperColumn = new MapperFile.MapperColumn();
                    mapperColumn.setColumn(column);
                    String jdbcType = JavaTypeResolver.parseJdbcTypeName(sqlType);
                    mapperColumn.setJdbcType(jdbcType);
                    mapperColumn.setProperty(entityField.getFieldName());
                    mapperColumn.setNumerical(JavaTypeResolver.isNumeriacal(jdbcType));
                    mapperFile.addColumn(mapperColumn);
                    if(pkName.equals(column)){
                        mapperFile.setPrimaryColumn(mapperColumn);
                    }
                }
                mapperFile.setPrimarykey(entityFile.getPrimarykey());
                serviceFile.setPrimarykey(entityFile.getPrimarykey());

                FileHolder fileHolder = new FileHolder(entityFile, mapperFile, serviceFile,multiModule,artifactPrefix,defaultPageClass);
                fileHolders.add(fileHolder);

            }

        } catch (SQLException e) {
            logger.error("parse table error, ",e);
        }

        return fileHolders;
    }

    private String extractPKName(String tableName) {
        String pkName = null;


        try {
            DatabaseMetaData dbMetaData = connection.getMetaData();
            ResultSet pkRSet  = dbMetaData.getPrimaryKeys(null, null, tableName);
            while (pkRSet.next()) {  //如果存在两个主键时，ResultSet时反过来读的，所以会取组合主键中的第一个
                System.err.println("****** Comment ******");
                System.err.println("TABLE_CAT : " + pkRSet.getObject(1));
                System.err.println("TABLE_SCHEM: " + pkRSet.getObject(2));
                System.err.println("TABLE_NAME : " + pkRSet.getObject(3));
                pkName=pkRSet.getObject(4).toString();
                System.err.println("COLUMN_NAME: " + pkName);
                System.err.println("KEY_SEQ : " + pkRSet.getObject(5));
                System.err.println("PK_NAME : " + pkRSet.getObject(6));
                System.err.println("****** ******* ******");
            }
        } catch (SQLException e) {
            logger.error("extract primary key fail!",e);
            throw new PrimaryKeyException("extract primary key fail!");
        }
        return  pkName;
    }

    private String extractComment(String tableName, String column) {

        String comment = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT COLUMN_COMMENT FROM information_schema.COLUMNS WHERE TABLE_NAME='" + tableName + "' AND COLUMN_NAME='" + column + "'";
        try {
            pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                comment = rs.getString(1);
            }
        } catch (Exception e) {
            logger.error("extractComment fail.", e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();

            } catch (Exception e) {
                logger.error("", e);
            }
        }
        if (StringUtils.isNotEmpty(comment)) {
            comment = comment.replace("\n", "").replace("\r", "").replace("\n\r", "");
        }

        return comment;
    }
}
