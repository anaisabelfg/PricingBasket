package com.pricingbasket.coding.infrastructure;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ListReader<T> {
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<T> read(String fileName, Class<T> typeClass) {
        try {

            String items = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, typeClass);
            return objectMapper.readValue(items, type);
        } catch (IOException e) {
            throw new MalformedListException(e);
        }
    }

}
