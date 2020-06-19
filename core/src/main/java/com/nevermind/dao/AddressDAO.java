package com.nevermind.dao;

import com.nevermind.model.Address;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class AddressDAO implements DAO<Address> {

    private final DataSource dataSource;
    private final String tableName = "addresses";

    public AddressDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean create(Address address) {
        String sql = "INSERT INTO " + tableName + "(contact_id, country, city, street, house_number, appartment_number, postcode) " +
                "VALUES(?,?,?,?,?,?,?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, address.getContactId());
            preparedStatement.setString(2, address.getCountry());
            preparedStatement.setString(3, address.getCity());
            preparedStatement.setString(4, address.getStreet());
            preparedStatement.setInt(5, address.getHouseNumber());
            preparedStatement.setInt(6, address.getApartmentNumber());
            preparedStatement.setInt(7, address.getPostcode());
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return false;
    }

    @Override
    public Address get(int id) {
        String sql = "SELECT contact_id, country, city, street, house_number, appartment_number, postcode" +
                " FROM " + tableName +
                " WHERE contact_id=" + id + ";";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            return resultSet.next() ? parseAddress(resultSet) : null;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Address> getAllById(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Address> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Address address) {

        String sql = "UPDATE " + tableName + " SET(country, city, street, house_number, appartment_number, postcode) =" +
                "(?,?,?,?,?,?)" +
                " WHERE contact_id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, address.getCountry());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setInt(4, address.getHouseNumber());
            preparedStatement.setInt(5, address.getApartmentNumber());
            preparedStatement.setInt(6, address.getPostcode());
            preparedStatement.setLong(7, address.getContactId());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Address address) {
        throw new UnsupportedOperationException();
    }


    private Address parseAddress(ResultSet resultSet) throws SQLException {
        Address address = new Address();
        address.setContactId(
                resultSet.getInt(1));
        address.setCountry(
                resultSet.getString(2));
        address.setCity(
                resultSet.getString(3));
        address.setStreet(
                resultSet.getString(4));
        address.setHouseNumber(
                resultSet.getInt(5));
        address.setApartmentNumber(
                resultSet.getInt(6));
        address.setPostcode(
                resultSet.getInt(7));
        return address;
    }
}
