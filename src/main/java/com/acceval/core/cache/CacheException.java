package com.acceval.core.cache;

public class CacheException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CacheException(String msg) {
        super(msg);
    }

    public CacheException(Class<?> clz, String msg) {
        super((clz != null ? clz.getName() + ". " : "") + msg);
    }
}
