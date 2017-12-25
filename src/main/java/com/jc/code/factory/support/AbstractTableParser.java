package com.jc.code.factory.support;

import com.jc.code.factory.config.EntityFileHolder;
import com.jc.entity.factory.context.CodegenContext;

import java.sql.Connection;

public abstract class AbstractTableParser implements  ParserTable {


    protected Connection connection;

    protected CodegenContext codegenContext;

    public AbstractTableParser(Connection connection,CodegenContext codegenContext){
        this.connection = connection;
        this.codegenContext = codegenContext;
    }




}
