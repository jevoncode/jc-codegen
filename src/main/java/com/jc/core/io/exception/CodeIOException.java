package com.jc.core.io.exception;


import com.jc.exception.NestedRuntimeException;
import org.springframework.util.ObjectUtils;

public class CodeIOException extends NestedRuntimeException {

    public CodeIOException(String msg) {
        super(msg);
    }

    public CodeIOException(String msg, Throwable cause) {
        super(msg, cause);
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CodeIOException)) {
            return false;
        }
        CodeIOException otherBe = (CodeIOException) other;
        return (getMessage().equals(otherBe.getMessage()) &&
                ObjectUtils.nullSafeEquals(getCause(), otherBe.getCause()));
    }

    @Override
    public int hashCode() {
        return getMessage().hashCode();
    }
}