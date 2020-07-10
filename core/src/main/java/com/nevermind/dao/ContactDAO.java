package com.nevermind.dao;

import com.nevermind.criteria.Criterion;
import com.nevermind.criteria.CriterionParser;
import com.nevermind.model.Contact;
import com.nevermind.model.Gender;
import com.nevermind.model.MaritalStatus;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO implements PaginationDAO<Contact>, SearchDAO<Contact, Criterion> {

    private final DataSource dataSource;
    private final String tableName = "contacts";

    public ContactDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public long create(Contact contact) {
        String sql = "INSERT INTO " + tableName + "(first_name,middle_name,last_name,date_of_birth,gender," +
                "marital_status,citizenship,website,email,current_place_of_work,photo)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?)" +
                " RETURNING id;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, contact.getFirstName());
            preparedStatement.setString(2, contact.getMiddleName());
            preparedStatement.setString(3, contact.getLastName());
            preparedStatement.setDate(4, Date.valueOf(contact.getDateOfBirth()));
            preparedStatement.setObject(5, contact.getGender().toString(), Types.OTHER);
            preparedStatement.setObject(6, contact.getMaritalStatus().toString(), Types.OTHER);
            preparedStatement.setString(7, contact.getCitizenship());
            preparedStatement.setString(8, contact.getWebsite());
            preparedStatement.setString(9, contact.getEmail());
            preparedStatement.setString(10, contact.getCurrentPlaceOfWork());
            preparedStatement.setString(11, contact.getPhoto());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? resultSet.getLong(1) : -1;
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    @Override
    public Contact get(long id) {
        String sql = "SELECT id,first_name,middle_name,last_name,date_of_birth,gender," +
                "marital_status,citizenship,website,email,current_place_of_work,photo" +
                " FROM " + tableName +
                " WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? parseContact(resultSet) : null;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Contact contact) {

        String sql = "UPDATE " + tableName +
                " SET(first_name,middle_name,last_name,date_of_birth,gender,marital_status,citizenship," +
                "website,email,current_place_of_work,photo) =(?,?,?,?,?,?,?,?,?,?,?)" +
                " WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, contact.getFirstName());
            preparedStatement.setString(2, contact.getMiddleName());
            preparedStatement.setString(3, contact.getLastName());
            preparedStatement.setDate(4, Date.valueOf(contact.getDateOfBirth()));
            preparedStatement.setObject(5, contact.getGender().toString(), Types.OTHER);
            preparedStatement.setObject(6, contact.getMaritalStatus().toString(), Types.OTHER);
            preparedStatement.setString(7, contact.getCitizenship());
            preparedStatement.setString(8, contact.getWebsite());
            preparedStatement.setString(9, contact.getEmail());
            preparedStatement.setString(10, contact.getCurrentPlaceOfWork());
            preparedStatement.setString(11, contact.getPhoto());
            preparedStatement.setLong(12, contact.getId());

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


    private Contact parseContact(ResultSet resultSet) throws SQLException {
        Contact contact = new Contact();
        contact.setId(
                resultSet.getLong(1));
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
                resultSet.getString(10));
        contact.setCurrentPlaceOfWork(
                resultSet.getString(11));
        contact.setPhoto(
                resultSet.getString(12));
        return contact;
    }

    @Override
    public int getTotalElements() {
        String sql = "SELECT COUNT(id)" +
                " FROM " + tableName + ";";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : -1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<Contact> getPage(int currentPage, int pageLimit) {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT id,first_name,middle_name,last_name,date_of_birth,gender," +
                "marital_status,citizenship,website,email,current_place_of_work,photo" +
                " FROM " + tableName +
                " ORDER BY id LIMIT ? OFFSET ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, pageLimit);
            preparedStatement.setInt(2, (currentPage - 1) * pageLimit);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    contacts.add(
                            parseContact(resultSet));
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return contacts;
    }

    @Override
    public int findTotalElements(List<Criterion> criteria) {
        List<String[]> criterionList = CriterionParser.parseCriterion(criteria);
        boolean addressCriterion = false;
        for (String[] crit : criterionList) {
            if (crit[0].equals("addresses")) {
                addressCriterion = true;
                break;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT COUNT(contacts.id)" +
                " FROM " + tableName);
        if (addressCriterion) {
            stringBuilder.append(" INNER JOIN addresses");
            stringBuilder.append(" ON contacts.id=addresses.contact_id");
        }
        boolean isFirst = true;
        for (String[] crit : criterionList) {
            if (isFirst) {
                stringBuilder.append(" WHERE ");
                isFirst = false;
            } else {
                stringBuilder.append(" AND ");
            }
            stringBuilder.append(crit[0])
                    .append(".")
                    .append(crit[1])
                    .append(crit[2])
                    .append(crit[3]);
        }
        stringBuilder.append(";");
        String sql = stringBuilder.toString();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : -1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<Contact> findPage(int currentPage, int pageLimit, List<Criterion> criteria) {
        List<Contact> contacts = new ArrayList<>();
        List<String[]> criterionList = CriterionParser.parseCriterion(criteria);
        boolean addressCriterion = false;
        for (String[] crit : criterionList) {
            if (crit[0].equals("addresses")) {
                addressCriterion = true;
                break;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT contacts.id,contacts.first_name,contacts.middle_name,contacts.last_name,contacts.date_of_birth,contacts.gender," +
                "contacts.marital_status,contacts.citizenship,contacts.website,contacts.email,contacts.current_place_of_work,contacts.photo" +
                " FROM " + tableName);

        if (addressCriterion) {
            stringBuilder.append(" INNER JOIN addresses");
            stringBuilder.append(" ON contacts.id=addresses.contact_id");
        }
        boolean isFirst = true;
        for (String[] crit : criterionList) {
            if (isFirst) {
                stringBuilder.append(" WHERE ");
                isFirst = false;
            } else {
                stringBuilder.append(" AND ");
            }
            stringBuilder.append(crit[0])
                    .append(".")
                    .append(crit[1])
                    .append(crit[2])
                    .append(crit[3]);
        }
        stringBuilder.append(" ORDER BY contacts.id LIMIT ? OFFSET ?;");
        String sql = stringBuilder.toString();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, pageLimit);
            preparedStatement.setInt(2, (currentPage - 1) * pageLimit);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    contacts.add(
                            parseContact(resultSet));
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return contacts;
    }
}
