package com.jc.entity.factory.xml;

import com.jc.entity.factory.context.CodegenContext;
import org.springframework.core.io.Resource;

public class XmlReaderContext {


    private CodegenContext codegenContext;

    private Resource resource;

    public XmlReaderContext(CodegenContext codegenContext, Resource resource) {
        this.codegenContext = codegenContext;
        this.resource = resource;
    }


    public CodegenContext getCodegenContext() {
        return codegenContext;
    }

    public void setCodegenContext(CodegenContext codegenContext) {
        this.codegenContext = codegenContext;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
