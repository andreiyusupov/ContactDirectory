package com.nevermind.service;

import com.nevermind.dao.PhoneNumberDAO;
import com.nevermind.model.PhoneNumber;
import com.nevermind.model.PhoneType;

import javax.json.*;
import java.util.ArrayList;

public class PhoneNumberService implements Service<JsonStructure> {

    private final PhoneNumberDAO phoneNumberDAO;

    public PhoneNumberService(PhoneNumberDAO phoneNumberDAO) {
        this.phoneNumberDAO = phoneNumberDAO;
    }

    public long create(JsonStructure phoneNumberInput) {
        JsonObject jsonObject = phoneNumberInput.asJsonObject();
        PhoneNumber phoneNumber = initPhoneNumber(jsonObject);
        return phoneNumberDAO.create(phoneNumber);
    }

    @Override
    public JsonStructure get(long id) {
        PhoneNumber phoneNumber = phoneNumberDAO.get(id);
        return initPhoneNumber(phoneNumber).build();
    }

    @Override
    public JsonStructure getAllById(long id) {
        ArrayList<PhoneNumber> phoneNumberList = (ArrayList<PhoneNumber>) phoneNumberDAO.getAllById(id);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (PhoneNumber phoneNumber : phoneNumberList) {
            jsonArrayBuilder.add(initPhoneNumber(phoneNumber));
        }
        return jsonArrayBuilder.build();
    }

    @Override
    public JsonStructure getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(JsonStructure phoneNumberInput) {
        JsonObject jsonObject = phoneNumberInput.asJsonObject();
        PhoneNumber phoneNumber = initPhoneNumber(jsonObject);
        return phoneNumberDAO.update(phoneNumber);
    }

    @Override
    public boolean delete(JsonStructure phoneNumberInput) {
        JsonObject jsonObject = phoneNumberInput.asJsonObject();
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(jsonObject.getJsonNumber("id").longValueExact());
        return phoneNumberDAO.delete(phoneNumber);
    }

    private JsonObjectBuilder initPhoneNumber(PhoneNumber phoneNumber) {
        return Json.createObjectBuilder()
                .add("id", phoneNumber.getId())
                .add("contactId", phoneNumber.getContactId())
                .add("countryCode", phoneNumber.getCountryCode())
                .add("operatorCode", phoneNumber.getOperatorCode())
                .add("phoneNumber", phoneNumber.getPhoneNumber())
                .add("phoneType", phoneNumber.getPhoneType().toString())
                .add("comment", phoneNumber.getComment());
    }

    private PhoneNumber initPhoneNumber(JsonObject jsonObject) {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(jsonObject.getJsonNumber("id").longValueExact());
        phoneNumber.setContactId(jsonObject.getJsonNumber("contactId").longValueExact());
        phoneNumber.setCountryCode(jsonObject.getInt("countryCode"));
        phoneNumber.setOperatorCode(jsonObject.getInt("operatorCode"));
        phoneNumber.setPhoneNumber(jsonObject.getJsonNumber("phoneNumber").longValueExact());
        phoneNumber.setPhoneType(PhoneType.valueOf(jsonObject.getString("phoneType")));
        phoneNumber.setComment(jsonObject.getString("comment"));
        return phoneNumber;
    }
}