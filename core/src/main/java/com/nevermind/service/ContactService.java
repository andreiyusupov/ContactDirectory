package com.nevermind.service;

import com.nevermind.dao.ContactDAO;
import com.nevermind.model.Contact;
import com.nevermind.model.Gender;
import com.nevermind.model.MaritalStatus;

import javax.json.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ContactService implements Service<JsonStructure> {

    private final ContactDAO contactDAO;

    public ContactService(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }


    public long create(JsonStructure contactInput) {
        JsonObject jsonObject = contactInput.asJsonObject();
        Contact contact = initContact(jsonObject);
        return contactDAO.create(contact);
    }

    @Override
    public JsonStructure get(long id) {
        Contact contact = contactDAO.get(id);
        return initContact(contact).build();
    }

    @Override
    public JsonStructure getAllById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonStructure getAll() {
        ArrayList<Contact> contactList = (ArrayList<Contact>) contactDAO.getAll();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (Contact contact : contactList) {
            jsonArrayBuilder.add(initContact(contact));
        }
        return jsonArrayBuilder.build();
    }

    @Override
    public boolean update(JsonStructure contactInput) {
        JsonObject jsonObject = contactInput.asJsonObject();
        Contact contact = initContact(jsonObject);
        contact.setId(jsonObject.getJsonNumber("id").longValueExact());
        return contactDAO.update(contact);
    }

    @Override
    public boolean delete(JsonStructure contactInput) {
        JsonObject jsonObject = contactInput.asJsonObject();
        Contact contact = new Contact();
        contact.setId(jsonObject.getJsonNumber("id").longValueExact());
        return contactDAO.delete(contact);

    }

    private JsonObjectBuilder initContact(Contact contact) {
        return Json.createObjectBuilder()
                .add("id", contact.getId())
                .add("firstName", contact.getFirstName())
                .add("middleName", contact.getMiddleName())
                .add("lastName", contact.getLastName())
                .add("dateOfBirth", contact.getDateOfBirth().toString())
                .add("gender", contact.getGender().toString())
                .add("citizenship", contact.getCitizenship())
                .add("maritalStatus", contact.getMaritalStatus().toString())
                .add("website", contact.getWebsite())
                .add("email", contact.getEmail())
                .add("currentPlaceOfWork", contact.getCurrentPlaceOfWork())
                .add("photo", contact.getPhoto());
    }

    private Contact initContact(JsonObject jsonObject) {
        Contact contact = new Contact();
        contact.setId(jsonObject.getJsonNumber("id").longValueExact());
        contact.setFirstName(jsonObject.getString("firstName"));
        contact.setMiddleName(jsonObject.getString("middleName"));
        contact.setLastName(jsonObject.getString("lastName"));
        contact.setDateOfBirth(LocalDate.parse(jsonObject.getString("dateOfBirth")));
        contact.setGender(Gender.valueOf(jsonObject.getString("gender")));
        contact.setCitizenship(jsonObject.getString("citizenship"));
        contact.setMaritalStatus(MaritalStatus.valueOf(jsonObject.getString("maritalStatus")));
        contact.setWebsite(jsonObject.getString("website"));
        contact.setEmail(jsonObject.getString("email"));
        contact.setCurrentPlaceOfWork(jsonObject.getString("currentPlaceOfWork"));
        contact.setPhoto(jsonObject.getString("photo"));
        return contact;
    }

}
