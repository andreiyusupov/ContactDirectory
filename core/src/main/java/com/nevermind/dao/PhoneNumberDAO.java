package com.nevermind.dao;

import com.nevermind.model.PhoneNumber;
import com.nevermind.model.PhoneType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhoneNumberDAO implements DAO<PhoneNumber> {

    private final DataSource dataSource;
    private final String tableName = "phone_numbers";

    public PhoneNumberDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public long create(PhoneNumber phoneNumber) {
        String sql = "INSERT INTO " + tableName + "(contact_id, country_code, operator_code, phone_number, phone_type, comment)" +
                " VALUES(?,?,?,?,?,?)" +
                " RETURNING id;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, phoneNumber.getContactId());
            preparedStatement.setInt(2, phoneNumber.getCountryCode());
            preparedStatement.setInt(3, phoneNumber.getOperatorCode());
            preparedStatement.setLong(4, phoneNumber.getPhoneNumber());
            preparedStatement.setObject(5, phoneNumber.getPhoneType().toString(), Types.OTHER);
            preparedStatement.setString(6, phoneNumber.getComment());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return -1;
    }

    @Override
    public PhoneNumber get(long id) {
        String sql = "SELECT id, contact_id, country_code, operator_code, phone_number, phone_type, comment" +
                " FROM " + tableName +
                " WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? parsePhoneNumber(resultSet) : null;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PhoneNumber> getAllById(long id) {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        String sql = "SELECT id, contact_id, country_code, operator_code, phone_number, phone_type, comment" +
                " FROM " + tableName +
                " WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    phoneNumbers.add(
                            parsePhoneNumber(resultSet));
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return phoneNumbers;
    }

    @Override
    public List<PhoneNumber> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(PhoneNumber phoneNumber) {
        String sql = "UPDATE " + tableName +
                " SET(contact_id, country_code, operator_code, phone_number, phone_type, comment) =(?,?,?,?,?,?)" +
                " WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, phoneNumber.getContactId());
            preparedStatement.setInt(2, phoneNumber.getCountryCode());
            preparedStatement.setInt(3, phoneNumber.getOperatorCode());
            preparedStatement.setLong(4, phoneNumber.getPhoneNumber());
            preparedStatement.setObject(5, phoneNumber.getPhoneType().toString(), Types.OTHER);
            preparedStatement.setString(6, phoneNumber.getComment());
            preparedStatement.setLong(7, phoneNumber.getId());

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(PhoneNumber phoneNumber) {
        String sql = "DELETE FROM " + tableName +
                " WHERE id=?;";
        try (Connection connection = dataSource.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, phoneNumber.getId());

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    private PhoneNumber parsePhoneNumber(ResultSet resultSet) throws SQLException {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(
                resultSet.getLong(1));
        phoneNumber.setContactId(
                resultSet.getLong(2));
        phoneNumber.setCountryCode(
                resultSet.getInt(3));
        phoneNumber.setOperatorCode(
                resultSet.getInt(4));
        phoneNumber.setPhoneNumber(
                resultSet.getInt(5));
        phoneNumber.setPhoneType(
                PhoneType.valueOf(resultSet.getString(6)));
        phoneNumber.setComment(
                resultSet.getString(7));
        return phoneNumber;
    }
}
