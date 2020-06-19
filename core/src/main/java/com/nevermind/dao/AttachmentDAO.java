package com.nevermind.dao;

import com.nevermind.model.Attachment;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttachmentDAO implements DAO<Attachment> {

    private final DataSource dataSource;
    private final String tableName = "attachments";

    public AttachmentDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean create(Attachment attachment) {
        String sql = "INSERT INTO " + tableName + "(contact_id, filename, date, comment) " +
                "VALUES(?,?,?,?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, attachment.getContactId());
            preparedStatement.setString(2, attachment.getFileName());
            preparedStatement.setDate(3, Date.valueOf(attachment.getDate()));
            preparedStatement.setString(4, attachment.getComment());
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return false;
    }

    @Override
    public Attachment get(int id) {
        String sql = "SELECT contact_id, filename, date, comment" +
                " FROM " + tableName +
                " WHERE contact_id=" + id + ";";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            return resultSet.next() ? parseAttachment(resultSet) : null;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Attachment> getAllById(int id) {
        List<Attachment> attachments = new ArrayList<>();
        String sql = "SELECT contact_id, filename, date, comment" +
                " FROM " + tableName +
                " WHERE contact_id=" + id + ";";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                attachments.add(
                        parseAttachment(resultSet));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return attachments;
    }

    @Override
    public List<Attachment> getAll() {
        throw new UnsupportedOperationException();
    }

    private Attachment parseAttachment(ResultSet resultSet) throws SQLException {
        Attachment attachment = new Attachment();
        attachment.setContactId(
                resultSet.getInt(1));
        attachment.setFileName(
                resultSet.getString(2));
        attachment.setDate(
                resultSet.getDate(3).toLocalDate());
        attachment.setComment(
                resultSet.getString(4));
        return attachment;
    }

    @Override
    public boolean update(Attachment attachment) {

        String sql = "UPDATE " + tableName + " SET(filename, date, comment) = (?,?,?)" +
                " WHERE contact_id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, attachment.getFileName());
            preparedStatement.setDate(2, Date.valueOf(attachment.getDate()));
            preparedStatement.setString(3, attachment.getComment());
            preparedStatement.setLong(4, attachment.getContactId());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Attachment attachment) {
        String sql = "DELETE FROM " + tableName +
                " WHERE (contact_id,filename)=(?,?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, attachment.getContactId());
            preparedStatement.setString(2, attachment.getFileName());
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return false;
    }
}