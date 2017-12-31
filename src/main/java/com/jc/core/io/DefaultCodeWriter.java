package com.jc.core.io;

import com.jc.core.io.config.CodeText;
import com.jc.core.io.exception.CodeIOException;
import com.jc.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.*;

public class DefaultCodeWriter implements  CodeWriter {

    private static Logger logger = LoggerFactory.getLogger(DefaultCodeWriter.class);

    private Writer writer;


    @Override
    public void write(CodeText codeText) {
        Assert.notNull(codeText,"CodeText must not be null");
        String path = codeText.getPath();
        Assert.notNull(path,"file path must not be null");
        String fileName = codeText.getFileName();
        Assert.notNull(fileName,"fileName must not be null");
        StringBuilder filePath = new StringBuilder();
        if(path.lastIndexOf("/")==path.length()-1){
            filePath.append(path).append(fileName);
        }else{
            filePath.append(path).append("/").append(fileName);
        }
        File file = new File(filePath.toString());
        File directory = file.getParentFile();
        if(directory.exists()){
            if(!directory.isDirectory()){
                logger.error("writing error! {} not directory codeText={}",directory.getAbsolutePath(), JsonUtils.toJSONString(codeText));
                throw new CodeIOException("writing error! "+directory.getAbsolutePath()+" not directory codeText="+JsonUtils.toJSONString(codeText));
            }
        }else{
            directory.mkdirs();
        }


        if(!file.exists()) {
            if(file.isDirectory()){
                throw new CodeIOException("filePath"+filePath.toString()+" is direcotry!!");
            }
            try {
                logger.info("生成文件{}",file.getAbsolutePath());
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(codeText.getContent());

            } catch (IOException e) {
                logger.error("writing error! codeText={}", JsonUtils.toJSONString(codeText),e);
                throw new CodeIOException("writing error! codeText="+JsonUtils.toJSONString(codeText),e);
            }finally {
                if(writer!=null)
                    try {
                        writer.close();
                    } catch (IOException e) {
                        throw new CodeIOException("writing error! codeText="+JsonUtils.toJSONString(codeText),e);
                    }
            }
        }else {
            logger.info("filePath={} exists!",filePath.toString());
        }

    }

    @Override
    public void write(String path, String fileName, String content) {
        CodeText codeText = new CodeText();
        codeText.setContent(content);
        codeText.setFileName(fileName);
        codeText.setPath(path);
        write(codeText);
    }


    public static void main(String[] args) {
        CodeWriter writer = new DefaultCodeWriter();
        writer.write("/mydata/com/jc/code","Test.java","中文\n");
    }
}
