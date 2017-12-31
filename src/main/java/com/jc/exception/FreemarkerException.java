package com.jc.exception;


import org.springframework.util.ObjectUtils;

public class FreemarkerException extends NestedRuntimeException {

    public FreemarkerException(String msg) {
        super(msg);
    }

    public FreemarkerException(String msg, Throwable cause) {
        super(msg, cause);
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FreemarkerException)) {
            return false;
        }
        FreemarkerException otherBe = (FreemarkerException) other;
        return (getMessage().equals(otherBe.getMessage()) &&
                ObjectUtils.nullSafeEquals(getCause(), otherBe.getCause()));
    }

    @Override
    public int hashCode() {
        return getMessage().hashCode();
    }
}