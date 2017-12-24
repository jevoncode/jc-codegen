package com.jc.entity.factory.xml;


import com.jc.entity.factory.config.EntityDefinition;
import com.jc.entity.factory.config.EntityDefinitionHolder;
import com.jc.entity.factory.utils.EntityDefinitionReaderUtils;
import com.jc.exception.EntityDefinitionStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DefaultEntityDefinitionDocumentReader implements  EntityDefinitionDocumentReader {

    private static Logger logger = LoggerFactory.getLogger(DefaultEntityDefinitionDocumentReader.class);

    private XmlReaderContext readerContext;
    private EntityDefinitionParserDelegate delegate;

    @Override
    public void registerBeanDefinitions(Document doc, XmlReaderContext XmlReaderContext) throws EntityDefinitionStoreException {

        this.readerContext = XmlReaderContext;
        logger.debug("Loading bean definitions");
        Element root = doc.getDocumentElement();
        doRegisterBeanDefinitions(root);
    }

    private void doRegisterBeanDefinitions(Element root) {
        this.delegate = createDelegate(getReaderContext(), root);


        parseEntityDefinitions(root, this.delegate);

    }

    private void parseEntityDefinitions(Element root, EntityDefinitionParserDelegate delegate) {
        if (delegate.isDefaultNamespace(root)) {
            NodeList nl = root.getChildNodes();
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                if (node instanceof Element) {
                    Element ele = (Element) node;
                    if (delegate.isDefaultNamespace(ele)) {
                        parseDefaultElement(ele, delegate);
                    }
                }
            }
        }
    }

    private void parseDefaultElement(Element ele, EntityDefinitionParserDelegate delegate) {
        EntityDefinitionHolder holder = delegate.parseBeanDefinitionElement(ele);
        EntityDefinitionReaderUtils.registerEntityDefinition(holder,getReaderContext().getCodegenContext());
    }

    private EntityDefinitionParserDelegate createDelegate(XmlReaderContext readerContext, Element root) {
        EntityDefinitionParserDelegate delegate = new EntityDefinitionParserDelegate(readerContext);
        delegate.initGroup(root);
        return delegate;
    }

    public XmlReaderContext getReaderContext() {
        return readerContext;
    }
}
