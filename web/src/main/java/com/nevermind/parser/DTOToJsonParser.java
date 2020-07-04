package com.nevermind.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nevermind.dto.ContactDTO;
import com.nevermind.dto.ShortContactDTO;

import java.util.List;

public class DTOToJsonParser {

    public static String parseDTOtoJson(ContactDTO contactDTO) throws JsonProcessingException {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .writeValueAsString(contactDTO);
    }

    public static String parseDTOtoJson(List<ShortContactDTO> contactDTOs) throws JsonProcessingException {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .writeValueAsString(contactDTOs);
    }
}
