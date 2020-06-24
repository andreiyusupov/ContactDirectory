package com.nevermind.service;

import javax.json.*;
import javax.json.stream.JsonParser;
import java.io.StringReader;

public class CommonService implements Service<String> {

    private final Service<JsonStructure> contactService;
    private final Service<JsonStructure> addressService;
    private final Service<JsonStructure> attachmentService;
    private final Service<JsonStructure> phoneNumberService;

    public CommonService(ContactService contactService, AddressService addressService, AttachmentService attachmentService, PhoneNumberService phoneNumberService) {
        this.contactService = contactService;
        this.addressService = addressService;
        this.attachmentService = attachmentService;
        this.phoneNumberService = phoneNumberService;
    }

    @Override
    public long create(String commonInput) {

        JsonParser jsonParser = Json.createParser(new StringReader(commonInput));
        long id = -1;
        while (jsonParser.hasNext()) {
            JsonParser.Event event = jsonParser.next();
            if (event == JsonParser.Event.KEY_NAME) {
                String keyName = jsonParser.getString();
                jsonParser.next();
                switch (keyName) {
                    case "contact" -> {
                        JsonObject contactJson = jsonParser.getObject();
                        id = contactJson.getJsonNumber("id").longValueExact();
                        contactService.create(contactJson);
                    }
                    case "address" -> addressService.create(Json.createObjectBuilder(jsonParser.getObject())
                            .add("contact_id", id)
                            .build());
                    case "phoneNumbers" -> {
                        for (JsonValue jsonValue : jsonParser.getArray()) {
                            phoneNumberService.create(Json.createObjectBuilder(jsonValue.asJsonObject())
                                    .add("contact_id", id)
                                    .build());
                        }
                    }
                    case "attachments" -> {
                        for (JsonValue jsonValue : jsonParser.getArray()) {
                            attachmentService.create(Json.createObjectBuilder(jsonValue.asJsonObject())
                                    .add("contact_id", id)
                                    .build());
                        }
                    }
                }
            }
        }
        return id;
    }

    @Override
    public String get(long id) {
        return Json.createObjectBuilder()
                .add("contact", contactService.get(id))
                .add("address", addressService.get(id))
                .add("phoneNumbers", phoneNumberService.getAllById(id))
                .add("attachments", attachmentService.getAllById(id))
                .build()
                .toString();
    }

    @Override
    public String getAllById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAll() {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (JsonValue contact : contactService.getAll()
                .asJsonArray()) {
            long id = contact.asJsonObject()
                    .getJsonNumber("id")
                    .longValueExact();
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                    .add("contact", contact)
                    .add("address", addressService.get(id))
                    .add("phoneNumbers", phoneNumberService.getAllById(id))
                    .add("attachments", attachmentService.getAllById(id));
            jsonArrayBuilder.add(jsonObjectBuilder);
        }
        return jsonArrayBuilder.build().toString();
    }

    @Override
    public boolean update(String commonInput) {

        JsonParser jsonParser = Json.createParser(new StringReader(commonInput));
        while (jsonParser.hasNext()) {
            JsonParser.Event event = jsonParser.next();
            if (event == JsonParser.Event.KEY_NAME) {
                String keyName = jsonParser.getString();
                jsonParser.next();
                switch (keyName) {
                    case "contact" -> {
                        JsonObject contactObject = jsonParser.getObject();
                        long id = contactObject.getJsonNumber("id").longValueExact();
                        if (!contactService.get(id).asJsonObject().equals(contactObject)) {
                            contactService.update(contactObject);
                        }
                    }
                    case "address" -> {
                        JsonObject addressObject = jsonParser.getObject();
                        long id = addressObject.getJsonNumber("id").longValueExact();
                        JsonStructure address = addressService.get(id);
                        if (address == null) {
                            addressService.create(addressObject);
                        } else if (!address.asJsonObject().equals(addressObject)) {
                            addressService.update(addressObject);
                        }
                    }
                    case "phoneNumbers" -> {
                        for (JsonValue jsonValue : jsonParser.getArray()) {
                            JsonObject phoneNumberObject = jsonValue.asJsonObject();
                            long id = phoneNumberObject.getJsonNumber("id").longValueExact();
                            JsonStructure phoneNumber = phoneNumberService.get(id);
                            if (phoneNumber == null) {
                                phoneNumberService.create(phoneNumberObject);
                            } else if (!phoneNumber.asJsonObject().equals(phoneNumberObject)) {
                                phoneNumberService.update(phoneNumberObject);
                            }
                        }
                    }
                    case "attachments" -> {
                        for (JsonValue jsonValue : jsonParser.getArray()) {
                            JsonObject attachmentObject = jsonValue.asJsonObject();
                            long id = attachmentObject.getJsonNumber("id").longValueExact();
                            JsonStructure attachment = attachmentService.get(id);
                            if (attachment == null) {
                                attachmentService.create(attachmentObject);
                            } else if (!attachment.asJsonObject().equals(attachmentObject)) {
                                attachmentService.update(attachmentObject);
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean delete(String commonInput) {

        JsonParser jsonParser = Json.createParser(new StringReader(commonInput));
        while (jsonParser.hasNext()) {
            JsonParser.Event event = jsonParser.next();
            if (event == JsonParser.Event.KEY_NAME) {
                String keyName = jsonParser.getString();
                jsonParser.next();
                switch (keyName) {
                    case "contact" -> contactService.delete(jsonParser.getObject());
                    case "address" -> addressService.delete(jsonParser.getObject());
                    case "phoneNumbers" -> {
                        for (JsonValue jsonValue : jsonParser.getArray()) {
                            phoneNumberService.delete(jsonValue.asJsonObject());
                        }
                    }
                    case "attachments" -> {
                        for (JsonValue jsonValue : jsonParser.getArray()) {
                            attachmentService.delete(jsonValue.asJsonObject());
                        }
                    }
                }
            }
        }

        return true;
    }
}
