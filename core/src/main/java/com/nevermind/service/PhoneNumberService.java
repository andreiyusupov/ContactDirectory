package com.nevermind.service;

import com.nevermind.dao.CoreDAO;
import com.nevermind.dao.ManySlavesDAO;
import com.nevermind.model.PhoneNumber;

import java.util.List;

public class PhoneNumberService implements ManySlavesService<PhoneNumber> {

    private final CoreDAO<PhoneNumber> phoneNumberDAO;

    public PhoneNumberService(CoreDAO<PhoneNumber> phoneNumberDAO) {
        this.phoneNumberDAO = phoneNumberDAO;
    }

    @Override
    public long create(PhoneNumber phoneNumber) {
        return phoneNumberDAO.create(phoneNumber);
    }

    @Override
    public PhoneNumber get(long id) {
        return phoneNumberDAO.get(id);
    }

    @Override
    public List<PhoneNumber> getAllByMasterId(long id) {
        return ((ManySlavesDAO<PhoneNumber>) phoneNumberDAO).getAllByMasterId(id);
    }

    @Override
    public boolean update(PhoneNumber phoneNumber) {
        return phoneNumberDAO.update(phoneNumber);
    }

    @Override
    public boolean delete(long id) {
        return phoneNumberDAO.delete(id);
    }

}