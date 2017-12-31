package com.jc.core.io;

import com.jc.core.io.config.CodeText;

public interface CodeWriter {

    void write(CodeText codeText);

    void write(String path,String fileName,String content);

}
