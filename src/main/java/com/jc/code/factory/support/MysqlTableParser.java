package com.jc.code.factory.support;

import com.jc.code.factory.config.EntityFileHolder;
import com.jc.entity.factory.config.EntityDefinition;
import com.jc.entity.factory.context.CodegenContext;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Map;

public class MysqlTableParser extends AbstractTableParser {


    public MysqlTableParser(Connection connection, CodegenContext codegenContext) {
        super(connection, codegenContext);
    }

    @Override
    public EntityFileHolder parse() throws Exception {


        // 获取数据库的元信息
        DatabaseMetaData dbMetaData = connection.getMetaData();


        for(Map.Entry<String, EntityDefinition> entry:codegenContext.getEntityDefinitionMap().entrySet()){

            String tableName = entry.getKey();

            ResultSet rs = connection.prepareStatement(
                    "SELECT * FROM " + tableName + " WHERE 1=2")
                    .executeQuery();

            ResultSetMetaData rsMetaData = rs.getMetaData();

            for (int i = 0; i < rsMetaData.getColumnCount(); i++) {


            }


        }


        return null;
    }
}
