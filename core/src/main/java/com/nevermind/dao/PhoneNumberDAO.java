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
    public boolean create(PhoneNumber phoneNumber) {
        String sql = "INSERT INTO " + tableName + "(contact_id, country_code, operator_code, phone_number, phone_type, comment) " +
                "VALUES(?,?,?,?,?,?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, phoneNumber.getContactId());
            preparedStatement.setInt(2, phoneNumber.getCountryCode());
            preparedStatement.setInt(3, phoneNumber.getOperatorCode());
            preparedStatement.setLong(4, phoneNumber.getPhoneNumber());
            preparedStatement.setObject(5, phoneNumber.getPhoneType().toString(), Types.OTHER);
            preparedStatement.setString(6, phoneNumber.getComment());

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return false;
    }

    @Override
    public PhoneNumber get(long id) {
        String sql = "SELECT contact_id, country_code, operator_code, phone_number, phone_type, comment" +
                " FROM " + tableName +
                " WHERE contact_id=?;";
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
        String sql = "SELECT contact_id, country_code, operator_code, phone_number, phone_type, comment" +
                " FROM " + tableName +
                " WHERE contact_id=?;";
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
                " SET(country_code, operator_code, phone_number, phone_type, comment) =(?,?,?,?,?)" +
                " WHERE contact_id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, phoneNumber.getCountryCode());
            preparedStatement.setInt(2, phoneNumber.getOperatorCode());
            preparedStatement.setLong(3, phoneNumber.getPhoneNumber());
            preparedStatement.setObject(4, phoneNumber.getPhoneType().toString(), Types.OTHER);
            preparedStatement.setString(5, phoneNumber.getComment());
            preparedStatement.setLong(6, phoneNumber.getContactId());

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(PhoneNumber phoneNumber) {
        String sql = "DELETE FROM " + tableName +
                " WHERE (contact_id,country_code,operator_code,phone_number,phone_type) = (?,?,?,?,?);";
        try (Connection connection = dataSource.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, phoneNumber.getContactId());
            preparedStatement.setInt(2, phoneNumber.getCountryCode());
            preparedStatement.setInt(3, phoneNumber.getOperatorCode());
            preparedStatement.setLong(4, phoneNumber.getPhoneNumber());
            preparedStatement.setObject(5, phoneNumber.getPhoneType().toString(), Types.OTHER);

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    private PhoneNumber parsePhoneNumber(ResultSet resultSet) throws SQLException {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setContactId(
                resultSet.getInt(1));
        phoneNumber.setCountryCode(
                resultSet.getInt(2));
        phoneNumber.setOperatorCode(
                resultSet.getInt(3));
        phoneNumber.setPhoneNumber(
                resultSet.getInt(4));
        phoneNumber.setPhoneType(
                PhoneType.valueOf(resultSet.getString(5)));
        phoneNumber.setComment(
                resultSet.getString(6));
        return phoneNumber;
    }
}
