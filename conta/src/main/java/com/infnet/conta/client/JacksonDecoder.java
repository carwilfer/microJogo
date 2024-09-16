package com.infnet.conta.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.Decoder;

import java.io.IOException;
import java.lang.reflect.Type;

public class JacksonDecoder implements Decoder {

    private final ObjectMapper objectMapper;

    public JacksonDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException {
        if (response.body() == null) {
            return null;
        }
        return objectMapper.readValue(response.body().asInputStream(), objectMapper.constructType(type));
    }
}
