package com.nevermind.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseUtil {

    public static DataSource getDataSource() {
        DataSource ds=null;
        try {
            InitialContext cxt = new InitialContext();
            ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/postgres" );
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return ds;
    }

}
