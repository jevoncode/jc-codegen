package com.jc.entity.factory.xml;

import com.jc.Bootstrap;
import com.jc.entity.factory.context.CodegenContext;
import com.jc.entity.factory.support.EntityDefinitionReader;
import com.jc.exception.EntityDefinitionStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.Assert;
import org.springframework.util.xml.XmlValidationModeDetector;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.InputStream;

public class XmlEntityDefinitionReader implements EntityDefinitionReader {

    private final Logger logger = LoggerFactory.getLogger(XmlEntityDefinitionReader.class);

    //jc-note: 使用标准的JAXP-configured, 用于加载Xml的Document实例
    private DocumentLoader documentLoader = new DefaultDocumentLoader();


    private EntityResolver entityResolver;


    private ErrorHandler errorHandler = new SimpleSaxErrorHandler(logger);


    /**
     * 表明使用XSD校验
     */
    public static final int VALIDATION_XSD = XmlValidationModeDetector.VALIDATION_XSD;


    private boolean namespaceAware = false;

    private CodegenContext codegenContext;

    public XmlEntityDefinitionReader(CodegenContext codegenContext) {
        this.codegenContext = codegenContext;
    }

    @Override
    public int loadEntityDefinitions(String locationConfig) {
        Resource resource = codegenContext.getResource(locationConfig);
        return loadEntityDefinitions(resource);
    }

    @Override
    public int loadEntityDefinitions(Resource resource) throws EntityDefinitionStoreException {
        return loadEntityDefinitions(new EncodedResource(resource));
    }

    public int loadEntityDefinitions(EncodedResource encodedResource) throws EntityDefinitionStoreException {
        Assert.notNull(encodedResource, "EncodedResource must not be null");
        try {
            InputStream inputStream = encodedResource.getResource().getInputStream();
            try {
                InputSource inputSource = new InputSource(inputStream);
                if (encodedResource.getEncoding() != null) {
                    inputSource.setEncoding(encodedResource.getEncoding());
                }
                return doLoadEntityDefinitions(inputSource, encodedResource.getResource());
            } finally {
                inputStream.close();
            }
        } catch (IOException ex) {
            throw new EntityDefinitionStoreException(
                    "IOException parsing XML document from " + encodedResource.getResource(), ex);
        }

    }

    private int doLoadEntityDefinitions(InputSource inputSource, Resource resource) throws EntityDefinitionStoreException {
        try {
            Document doc = doLoadDocument(inputSource, resource);
            return registerBeanDefinitions(doc, resource);
        } catch (Exception e) {
            throw new EntityDefinitionStoreException(resource.getDescription(), "Parser configuration exception parsing XML from " + resource, e);
        }
    }

    protected Document doLoadDocument(InputSource inputSource, Resource resource) throws Exception {
        return this.documentLoader.loadDocument(inputSource, getEntityResolver(), this.errorHandler,
                getValidationModeForResource(resource), isNamespaceAware());
    }

    private int getValidationModeForResource(Resource resource) {
        return VALIDATION_XSD;
    }
    /**
     * 返回是否开启XML命名空间
     */
    public boolean isNamespaceAware() {
        return this.namespaceAware;
    }

    protected EntityResolver getEntityResolver() {
        if (this.entityResolver == null) {
            // Determine default EntityResolver to use.
            this.entityResolver = new PluggableSchemaResolver(Bootstrap.class.getClassLoader());

        }
        return this.entityResolver;
    }

    public int registerBeanDefinitions(Document doc, Resource resource) throws BeanDefinitionStoreException {
        EntityDefinitionDocumentReader documentReader = new DefaultEntityDefinitionDocumentReader();
        int countBefore =  getCodegenContext().getEntityDefinitionCount();
        documentReader.registerBeanDefinitions(doc,new XmlReaderContext(getCodegenContext(),resource));
        return getCodegenContext().getEntityDefinitionCount() - countBefore;
    }

    public CodegenContext getCodegenContext() {
        return codegenContext;
    }

}
