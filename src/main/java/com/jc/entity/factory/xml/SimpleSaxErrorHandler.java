package com.jc.entity.factory.xml;


import org.slf4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SimpleSaxErrorHandler implements ErrorHandler {
    private final Logger logger;


    /**
     * Create a new SimpleSaxErrorHandler for the given
     * Commons Logging logger instance.
     * @param logger 指定输出器
     */
    public SimpleSaxErrorHandler(Logger logger) {
        this.logger = logger;
    }


    @Override
    public void warning(SAXParseException ex) throws SAXException {
        logger.warn("Ignored XML validation warning", ex);
    }

    @Override
    public void error(SAXParseException ex) throws SAXException {
        throw ex;
    }

    @Override
    public void fatalError(SAXParseException ex) throws SAXException {
        throw ex;
    }
}
