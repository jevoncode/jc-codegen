package com.jc.entity.factory.support;

import com.jc.exception.EntityDefinitionStoreException;
import org.springframework.core.io.Resource;

public interface EntityDefinitionReader {


    int loadEntityDefinitions(Resource resource) throws EntityDefinitionStoreException;  //jc-note: 差点被骗了，其实这是public，接口是默认public 而不是protected

    int loadEntityDefinitions(String locationConfig);
}
