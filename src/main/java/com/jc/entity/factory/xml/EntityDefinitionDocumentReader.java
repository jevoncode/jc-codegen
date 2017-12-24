package com.jc.entity.factory.xml;

import com.jc.exception.EntityDefinitionStoreException;
import org.w3c.dom.Document;

public interface EntityDefinitionDocumentReader {


    void registerBeanDefinitions(Document doc, XmlReaderContext xmlReaderContext)
            throws EntityDefinitionStoreException;
}
