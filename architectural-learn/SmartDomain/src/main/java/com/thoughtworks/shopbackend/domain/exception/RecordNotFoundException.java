package com.thoughtworks.shopbackend.domain.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(Integer id, Class clazz) {
        super("record not found: " + clazz.getName() + "id: " + id);
    }
}
