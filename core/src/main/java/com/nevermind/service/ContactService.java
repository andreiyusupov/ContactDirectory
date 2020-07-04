package com.nevermind.service;

import com.nevermind.dao.CoreDAO;
import com.nevermind.dao.PaginationDAO;
import com.nevermind.model.Contact;

import java.util.List;

public class ContactService implements PaginationService<Contact> {

    private final CoreDAO<Contact> contactDAO;

    public ContactService(CoreDAO<Contact> contactDAO) {
        this.contactDAO = contactDAO;
    }

    @Override
    public long create(Contact contact) {
        return contactDAO.create(contact);
    }

    @Override
    public Contact get(long id) {
        return contactDAO.get(id);
    }

    @Override
    public boolean update(Contact contact) {
        return contactDAO.update(contact);
    }

    @Override
    public boolean delete(long id) {
        return contactDAO.delete(id);
    }

    @Override
    public int getTotalElements() {
        return ((PaginationDAO<Contact>) contactDAO).getTotalElements();
    }

    @Override
    public List<Contact> getPage(int pageNum, int recordsPerPage) {
        return ((PaginationDAO<Contact>) contactDAO).getPage(pageNum, recordsPerPage);
    }
}
