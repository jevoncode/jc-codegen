package com.jc.code.factory.support;

import com.jc.code.factory.config.EntityFileHolder;

import java.sql.Connection;

public interface ParserTable {


    EntityFileHolder parse() throws Exception;
}
