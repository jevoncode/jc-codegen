package com.jc.exception;


import org.springframework.util.ObjectUtils;

public class PrimaryKeyException extends NestedRuntimeException {

    public PrimaryKeyException(String msg) {
        super(msg);
    }

    public PrimaryKeyException(String msg, Throwable cause) {
        super(msg, cause);
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PrimaryKeyException)) {
            return false;
        }
        PrimaryKeyException otherBe = (PrimaryKeyException) other;
        return (getMessage().equals(otherBe.getMessage()) &&
                ObjectUtils.nullSafeEquals(getCause(), otherBe.getCause()));
    }

    @Override
    public int hashCode() {
        return getMessage().hashCode();
    }
}