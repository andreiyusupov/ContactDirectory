package com.nevermind.dao;

import com.nevermind.model.Gender;
import com.nevermind.model.MaritalStatus;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.nevermind.model.Contact;

public class ContactDAO implements DAO<Contact> {

    private final DataSource dataSource;
    private final String tableName="contacts";

    public ContactDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean create(Contact contact) {
        String sql = "INSERT INTO "+tableName+"(first_name,middle_name,last_name,date_of_birth,gender," +
                                "marital_status,citizenship,website,email,current_place_of_work,photo) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?);";
        try(Connection connection= dataSource.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
            preparedStatement.setString(1,contact.getFirstName());
            preparedStatement.setString(2,contact.getMiddleName());
            preparedStatement.setString(3,contact.getLastName());
            preparedStatement.setDate(4,Date.valueOf(contact.getDateOfBirth()));
            preparedStatement.setObject(5,contact.getGender().toString(),Types.OTHER);
            preparedStatement.setObject(6,contact.getMaritalStatus().toString(),Types.OTHER);
            preparedStatement.setString(7,contact.getCitizenship());
            preparedStatement.setString(8,contact.getWebsite());
            preparedStatement.setString(9,contact.getEmail());
            preparedStatement.setString(10,contact.getCurrentPlaceOfWork());
            preparedStatement.setString(11,contact.getPhoto());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted==1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public Contact get(int id) {
        String sql = "SELECT id,first_name,middle_name,last_name,date_of_birth,gender," +
                "marital_status,citizenship,website,email,current_place_of_work,photo" +
                " FROM "+tableName+
                " WHERE id="+id+";";
        try(Connection connection= dataSource.getConnection();
            Statement statement=connection.createStatement();
            ResultSet resultSet= statement.executeQuery(sql)) {
            return resultSet.next() ? parseContact(resultSet) : null;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
      return null;
    }

    @Override
    public List<Contact> getAllById(int id) {
       throw new UnsupportedOperationException();
    }

    @Override
    public List<Contact> getAll() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT id,first_name,middle_name,last_name,date_of_birth,gender," +
                "marital_status,citizenship,website,email,current_place_of_work,photo" +
                " FROM "+tableName+";";
        try(Connection connection= dataSource.getConnection();
            Statement statement=connection.createStatement();
            ResultSet resultSet= statement.executeQuery(sql)) {
            while(resultSet.next()){
                contacts.add(
                        parseContact(resultSet));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return contacts;
    }

    @Override
    public boolean update(Contact contact) {

        String sql = "UPDATE "+tableName+" SET(first_name,middle_name,last_name,date_of_birth,gender," +
                "marital_status,citizenship,website,email,current_place_of_work,photo) =" +
                "(?,?,?,?,?,?,?,?,?,?,?)" +
                " WHERE id=?;";
        try(Connection connection= dataSource.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql)) {

            preparedStatement.setString(1,contact.getFirstName());
            preparedStatement.setString(2,contact.getMiddleName());
            preparedStatement.setString(3,contact.getLastName());
            preparedStatement.setDate(4,Date.valueOf(contact.getDateOfBirth()));
            preparedStatement.setObject(5,contact.getGender().toString(),Types.OTHER);
            preparedStatement.setObject(6,contact.getMaritalStatus().toString(),Types.OTHER);
            preparedStatement.setString(7,contact.getCitizenship());
            preparedStatement.setString(8,contact.getWebsite());
            preparedStatement.setString(9,contact.getEmail());
            preparedStatement.setString(10,contact.getCurrentPlaceOfWork());
            preparedStatement.setString(11,contact.getPhoto());
            preparedStatement.setLong(12,contact.getId());
            int rowsUpdated= preparedStatement.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Contact contact) {
        String sql = "DELETE FROM "+tableName+" WHERE id="+contact.getId()+";";
        try(Connection connection= dataSource.getConnection();
            Statement statement=connection.createStatement()) {
            int rowsDeleted= statement.executeUpdate(sql);
            return rowsDeleted == 1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return false;
    }


    private Contact parseContact(ResultSet resultSet) throws SQLException {
        Contact contact= new Contact();
        contact.setId(
                resultSet.getInt(1));
        contact.setFirstName(
                resultSet.getString(2));
        contact.setMiddleName(
                resultSet.getString(3));
        contact.setLastName(
                resultSet.getString(4));
        contact.setDateOfBirth(
                resultSet.getDate(5).toLocalDate());
        contact.setGender(
                Gender.valueOf(
                        resultSet.getString(6)));
        contact.setMaritalStatus(
                MaritalStatus.valueOf(
                        resultSet.getString(7)));
        contact.setCitizenship(
                resultSet.getString(8));
        contact.setWebsite(
                resultSet.getString(9));
        contact.setEmail(
                resultSet.getString(10)
        );
        contact.setCurrentPlaceOfWork(
                resultSet.getString(11));
        contact.setPhoto(
                resultSet.getString(12));
        return contact;
    }

}
