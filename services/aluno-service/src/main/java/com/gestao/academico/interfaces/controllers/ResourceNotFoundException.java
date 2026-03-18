package com.gestao.academico.interfaces.controllers;

public class ResourceNotFoundException extends RuntimeException {
    private final String instance;

    public ResourceNotFoundException(String message, String instance) {
        super(message);
        this.instance = instance;
    }

    public String getInstance() {
        return instance;
    }
}
