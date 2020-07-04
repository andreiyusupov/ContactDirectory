package com.nevermind.dao;

import com.nevermind.model.Address;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDAO implements OneSlaveDAO<Address> {

    private final DataSource dataSource;
    private final String tableName = "addresses";

    public AddressDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public long create(Address address) {
        String sql = "INSERT INTO " + tableName + "(contact_id, country, city, street, house_number, appartment_number, postcode)" +
                " VALUES(?,?,?,?,?,?,?)" +
                " RETURNING id;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, address.getContactId());
            preparedStatement.setString(2, address.getCountry());
            preparedStatement.setString(3, address.getCity());
            preparedStatement.setString(4, address.getStreet());
            preparedStatement.setInt(5, address.getHouseNumber());
            preparedStatement.setInt(6, address.getApartmentNumber());
            preparedStatement.setInt(7, address.getPostcode());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? resultSet.getLong(1) : -1;
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return -1;
    }

    @Override
    public Address get(long id) {
        String sql = "SELECT id, contact_id, country, city, street, house_number, appartment_number, postcode" +
                " FROM " + tableName +
                " WHERE id=?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? parseAddress(resultSet) : null;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public Address getByMasterId(long masterId) {
        String sql = "SELECT id, contact_id, country, city, street, house_number, appartment_number, postcode" +
                " FROM " + tableName +
                " WHERE contact_id=?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, masterId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? parseAddress(resultSet) : null;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Address address) {

        String sql = "UPDATE " + tableName +
                " SET(contact_id, country, city, street, house_number, appartment_number, postcode) =(?,?,?,?,?,?,?)" +
                " WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, address.getContactId());
            preparedStatement.setString(2, address.getCountry());
            preparedStatement.setString(3, address.getCity());
            preparedStatement.setString(4, address.getStreet());
            preparedStatement.setInt(5, address.getHouseNumber());
            preparedStatement.setInt(6, address.getApartmentNumber());
            preparedStatement.setInt(7, address.getPostcode());
            preparedStatement.setLong(8, address.getId());

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM " + tableName +
                " WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    private Address parseAddress(ResultSet resultSet) throws SQLException {
        Address address = new Address();
        address.setId(
                resultSet.getLong(1));
        address.setContactId(
                resultSet.getLong(2));
        address.setCountry(
                resultSet.getString(3));
        address.setCity(
                resultSet.getString(4));
        address.setStreet(
                resultSet.getString(5));
        address.setHouseNumber(
                resultSet.getInt(6));
        address.setApartmentNumber(
                resultSet.getInt(7));
        address.setPostcode(
                resultSet.getInt(8));
        return address;
    }
}
