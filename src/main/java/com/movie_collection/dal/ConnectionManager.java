package com.movie_collection.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;


/**
 * Class responsible for managing the connection to the database.
 *
 * @author Patrik Valentiny
 */
public class ConnectionManager {
    private static final String CONFIG_FILE_NAME = "config.cfg";
    private static final SQLServerDataSource ds = new SQLServerDataSource();

    public ConnectionManager() {
        Properties props = new Properties();
        try {
            props.load(new FileReader(CONFIG_FILE_NAME));
        } catch (IOException e) {
            System.out.println("Config file could not be loaded");
        }

        ds.setServerName(props.getProperty("SERVER"));
        ds.setDatabaseName(props.getProperty("DATABASE"));
        ds.setPortNumber(Integer.parseInt(props.getProperty("PORT")));
        ds.setUser(props.getProperty("USER"));
        ds.setPassword(props.getProperty("PASSWORD"));
        ds.setTrustServerCertificate(true);
    }

    public Connection getConnection() throws SQLServerException {
        return ds.getConnection();
    }
}
