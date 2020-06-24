package com.nevermind.service;

import com.nevermind.dao.AttachmentDAO;
import com.nevermind.model.Attachment;

import javax.json.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AttachmentService implements Service<JsonStructure> {

    private final AttachmentDAO attachmentDAO;

    public AttachmentService(AttachmentDAO attachmentDAO) {
        this.attachmentDAO = attachmentDAO;
    }

    public long create(JsonStructure attachmentInput) {
        JsonObject jsonObject = attachmentInput.asJsonObject();
        Attachment attachment = initAttachment(jsonObject);
        return attachmentDAO.create(attachment);
    }

    @Override
    public JsonStructure get(long id) {
        Attachment attachment = attachmentDAO.get(id);
        return initAttachment(attachment).build();
    }

    @Override
    public JsonStructure getAllById(long id) {
        ArrayList<Attachment> attachmentList = (ArrayList<Attachment>) attachmentDAO.getAllById(id);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (Attachment attachment : attachmentList) {
            jsonArrayBuilder.add(initAttachment(attachment));
        }
        return jsonArrayBuilder.build();
    }

    @Override
    public JsonStructure getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(JsonStructure attachmentInput) {
        JsonObject jsonObject = attachmentInput.asJsonObject();
        Attachment attachment = initAttachment(jsonObject);
        return attachmentDAO.update(attachment);
    }

    @Override
    public boolean delete(JsonStructure attachmentInput) {
        JsonObject jsonObject = attachmentInput.asJsonObject();
        Attachment attachment = new Attachment();
        attachment.setId(jsonObject.getJsonNumber("id").longValueExact());
        return attachmentDAO.delete(attachment);
    }

    private JsonObjectBuilder initAttachment(Attachment attachment) {
        return Json.createObjectBuilder()
                .add("id", attachment.getId())
                .add("contactId", attachment.getContactId())
                .add("fileName", attachment.getFileName())
                .add("date", attachment.getDate().toString())
                .add("comment", attachment.getComment());
    }

    private Attachment initAttachment(JsonObject jsonObject) {
        Attachment attachment = new Attachment();
        attachment.setId(jsonObject.getJsonNumber("id").longValueExact());
        attachment.setContactId(jsonObject.getJsonNumber("contactId").longValueExact());
        attachment.setFileName(jsonObject.getString("fileName"));
        attachment.setDate(LocalDate.parse(jsonObject.getString("date")));
        attachment.setComment(jsonObject.getString("comment"));
        return attachment;
    }
}
