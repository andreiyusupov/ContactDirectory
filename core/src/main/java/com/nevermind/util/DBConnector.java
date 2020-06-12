package com.nevermind.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.postgresql.ds.PGConnectionPoolDataSource;

public class DBConnector {

    public static Connection getConnection() throws SQLException {
        PGConnectionPoolDataSource dataSource= new PGConnectionPoolDataSource();
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String pass = resource.getString("db.password");
        dataSource.setURL(url);
        dataSource.setUser(user);
        dataSource.setPassword(pass);
        return DriverManager.getConnection(url,user,pass);

    }
}
