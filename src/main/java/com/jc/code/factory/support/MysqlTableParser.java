package com.jc.code.factory.support;

import com.jc.code.factory.config.EntityFile;
import com.jc.code.factory.config.EntityFileHolder;
import com.jc.entity.factory.config.EntityDefinition;
import com.jc.entity.factory.context.CodegenContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class MysqlTableParser extends AbstractTableParser {


    private static Logger logger = LoggerFactory.getLogger(MysqlTableParser.class);

    public MysqlTableParser(Connection connection, CodegenContext codegenContext) {
        super(connection, codegenContext);
    }

    @Override
    public EntityFileHolder parse() throws Exception {


        // 获取数据库的元信息
        DatabaseMetaData dbMetaData = connection.getMetaData();


        for (Map.Entry<String, EntityDefinition> entry : codegenContext.getEntityDefinitionMap().entrySet()) {

            String tableName = entry.getKey();
            EntityDefinition entityDefinition = entry.getValue();

            ResultSet rs = connection.prepareStatement(
                    "SELECT * FROM " + tableName + " WHERE 1=2")
                    .executeQuery();

            ResultSetMetaData rsMetaData = rs.getMetaData();

            EntityFile entityFile = new EntityFile();

            for (int i = 0; i < rsMetaData.getColumnCount(); i++) {
                String column = rsMetaData.getColumnName(i + 1); // 列名、字段名
                int sqlType = rsMetaData.getColumnType(i + 1);  //@see java.sql.Types
                int columnSize = rsMetaData.getColumnDisplaySize(i + 1); //字段长度, 如varchar(50) 的50
                boolean isNull = rsMetaData.isNullable(i + 1) == 0 ? false : true;
                String comment = extractComment(tableName, column);

                List<EntityFile.EntityField> es =  entityFile.getEntityFieldList();
                EntityFile.EntityField entityField = new EntityFile.EntityField();
                String classCanonicalName = JavaTypeResolver.getColumnClassName(sqlType);
                entityField.setClassCanonicalName(classCanonicalName);



            }


        }


        return null;
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
        if(StringUtils.isNotEmpty(comment)) {
            comment = comment.replace("\n", "").replace("\r", "").replace("\n\r", "");
        }

        return comment;
    }
}
