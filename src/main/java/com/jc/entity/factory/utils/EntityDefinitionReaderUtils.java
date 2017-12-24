package com.jc.entity.factory.utils;

import com.jc.entity.factory.config.EntityDefinition;
import com.jc.entity.factory.config.EntityDefinitionHolder;
import com.jc.entity.factory.context.CodegenContext;

public class EntityDefinitionReaderUtils {
    public static void registerEntityDefinition(EntityDefinitionHolder holder, CodegenContext codegenContext) {
        codegenContext.registerEntityDefinition(holder);
    }
}
