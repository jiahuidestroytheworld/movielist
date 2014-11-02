package com.jiahui.movielists.config;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class ObjectMapperResolver implements ContextResolver<ObjectMapper> {
    //Don't try to use @Inject, it doesn't work.
    @Autowired
    private ObjectMapper mapper;

    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}