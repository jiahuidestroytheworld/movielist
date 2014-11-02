package com.jiahui.movielists.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class ObjectMapperFactory {
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                //.registerModule(new JodaModule())
                //.registerModule(new Hibernate4Module());
    }

    public static ObjectMapper create() {
        return objectMapper;
    }
}
