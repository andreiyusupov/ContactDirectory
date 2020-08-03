package com.nevermind.service;

import com.nevermind.dao.CoreDAO;
import com.nevermind.dao.ManySlavesDAO;
import com.nevermind.model.Attachment;

import java.util.List;

public class AttachmentService implements ManySlavesService<Attachment> {

    private final CoreDAO<Attachment> attachmentDAO;

    public AttachmentService(CoreDAO<Attachment> attachmentDAO) {
        this.attachmentDAO = attachmentDAO;
    }

    @Override
    public long create(Attachment attachment) {
        return attachmentDAO.create(attachment);
    }

    @Override
    public Attachment get(long id) {
        return attachmentDAO.get(id);
    }

    @Override
    public List<Attachment> getAllByMasterId(long id) {
        return ((ManySlavesDAO<Attachment>) attachmentDAO).getAllByMasterId(id);
    }

    @Override
    public boolean update(Attachment attachment) {
        return attachmentDAO.update(attachment);
    }

    @Override
    public boolean delete(long id) {
        return attachmentDAO.delete(id);
    }

}
