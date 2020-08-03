package com.nevermind.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nevermind.dto.ContactDTO;

import java.util.Arrays;


public class JsonParser {

    public static ContactDTO parseJsonToDTO(String contactJson) throws JsonProcessingException {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .readValue(contactJson, ContactDTO.class);
    }

    public static long[] getIdArray(String contactIdListJson) throws JsonProcessingException {
        Long[] sourceIdArray = new ObjectMapper()
                .readValue(contactIdListJson, Long[].class);
        long[] idArray = new long[sourceIdArray.length];
        Arrays.setAll(idArray, i -> sourceIdArray[i]);
        return idArray;
    }
}
