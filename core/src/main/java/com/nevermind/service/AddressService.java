package com.nevermind.service;

import com.nevermind.dao.AddressDAO;
import com.nevermind.model.Address;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;

public class AddressService implements Service<JsonStructure> {

    private final AddressDAO addressDAO;

    public AddressService(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    public long create(JsonStructure addressInput) {
        JsonObject jsonObject = addressInput.asJsonObject();
        Address address = initAddress(jsonObject);
        return addressDAO.create(address);
    }

    @Override
    public JsonStructure get(long id) {
        Address address = addressDAO.get(id);
        return initAddress(address).build();
    }

    @Override
    public JsonStructure getAllById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonStructure getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(JsonStructure addressInput) {
        JsonObject jsonObject = addressInput.asJsonObject();
        Address address = initAddress(jsonObject);
        return addressDAO.update(address);
    }

    @Override
    public boolean delete(JsonStructure addressInput) {
        JsonObject jsonObject = addressInput.asJsonObject();
        Address address = new Address();
        address.setId(jsonObject.getJsonNumber("id").longValueExact());
        return addressDAO.delete(address);
    }

    private JsonObjectBuilder initAddress(Address address) {
        return Json.createObjectBuilder()
                .add("id", address.getId())
                .add("contactId", address.getContactId())
                .add("country", address.getCountry())
                .add("city", address.getCity())
                .add("street", address.getStreet())
                .add("houseNumber", address.getHouseNumber())
                .add("apartmentNumber", address.getApartmentNumber())
                .add("postcode", address.getPostcode());
    }

    private Address initAddress(JsonObject jsonObject) {
        Address address = new Address();
        address.setId(jsonObject.getJsonNumber("id").longValueExact());
        address.setContactId(jsonObject.getJsonNumber("contactId").longValueExact());
        address.setCountry(jsonObject.getString("country"));
        address.setCity(jsonObject.getString("city"));
        address.setStreet(jsonObject.getString("street"));
        address.setHouseNumber(jsonObject.getInt("houseNumber"));
        address.setApartmentNumber(jsonObject.getInt("apartmentNumber"));
        address.setPostcode(jsonObject.getInt("postcode"));
        return address;
    }
}
