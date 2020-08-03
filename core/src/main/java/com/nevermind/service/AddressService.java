package com.nevermind.service;

import com.nevermind.dao.CoreDAO;
import com.nevermind.dao.OneSlaveDAO;
import com.nevermind.model.Address;

public class AddressService implements OneSlaveService<Address> {

    private final CoreDAO<Address> addressDAO;

    public AddressService(CoreDAO<Address> addressDAO) {
        this.addressDAO = addressDAO;
    }

    @Override
    public long create(Address address) {
        return addressDAO.create(address);
    }

    @Override
    public Address get(long id) {
        return addressDAO.get(id);
    }

    @Override
    public Address getByMasterId(long id) {

        return ((OneSlaveDAO<Address>) addressDAO).getByMasterId(id);
    }

    @Override
    public boolean update(Address address) {
        return addressDAO.update(address);
    }

    @Override
    public boolean delete(long id) {
        return addressDAO.delete(id);
    }
}
