package com.nevermind.service;

import com.nevermind.criteria.Criterion;
import com.nevermind.dao.CoreDAO;
import com.nevermind.dao.PaginationDAO;
import com.nevermind.dao.SearchDAO;
import com.nevermind.model.Contact;

import java.util.List;

public class ContactService implements PaginationService<Contact>, SearchService<Contact, Criterion> {

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
    public List<Contact> getPage(int currentPage, int pageLimit) {
        return ((PaginationDAO<Contact>) contactDAO).getPage(currentPage, pageLimit);
    }

    @Override
    public int findTotalElements(List<Criterion> criteria) {
        return ((SearchDAO<Contact, Criterion>) contactDAO).findTotalElements(criteria);
    }

    @Override
    public List<Contact> findPage(int pageNum, int recordsPerPage, List<Criterion> criteria) {
        return ((SearchDAO<Contact, Criterion>) contactDAO).findPage(pageNum, recordsPerPage, criteria);
    }
}
