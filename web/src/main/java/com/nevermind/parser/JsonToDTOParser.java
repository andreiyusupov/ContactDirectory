package com.nevermind.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nevermind.dto.ContactDTO;

import java.util.List;

public class JsonToDTOParser {

    public static ContactDTO parseJsonToDTO(String contactJson) throws JsonProcessingException {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .readValue(contactJson, ContactDTO.class);
    }

    public static List<ContactDTO> parseJsonToDTOList(String contactListJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        TypeFactory typeFactory = objectMapper.getTypeFactory();
        return objectMapper.registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .readValue(contactListJson, typeFactory.constructCollectionType(List.class, ContactDTO.class));
    }
}
