package com.jc.exception;


import org.springframework.util.ObjectUtils;

public class EntityException extends NestedRuntimeException {

    public EntityException(String msg) {
        super(msg);
    }

    public EntityException(String msg, Throwable cause) {
        super(msg, cause);
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EntityException)) {
            return false;
        }
        EntityException otherBe = (EntityException) other;
        return (getMessage().equals(otherBe.getMessage()) &&
                ObjectUtils.nullSafeEquals(getCause(), otherBe.getCause()));
    }

    @Override
    public int hashCode() {
        return getMessage().hashCode();
    }
}