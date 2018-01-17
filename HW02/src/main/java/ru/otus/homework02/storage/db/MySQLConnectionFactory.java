package ru.otus.homework02.storage.db;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnectionFactory {
    private static final String BASE_CONNECTION_STRING = "jdbc:mysql://%s:%d/%s?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String CONNECTION_STRING = BASE_CONNECTION_STRING + "&useSSL=false&user=%s&password=%s";
    public static final String MYSQL_SYSTEM_DB = "information_schema";

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    public void loadConfigurationProperties(String settingsFile) {
        Properties prop = new Properties();

        try (InputStream is = Files.newInputStream(new File(settingsFile).toPath())) {
            prop.load(is);
            host = prop.getProperty("host");
            port = Integer.valueOf(prop.getProperty("port"));
            database = prop.getProperty("database");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
        } catch (IOException e) {
        }

    }

    private static String buildURL(String host, int port, String database, String username, String password) {
        return String.format(CONNECTION_STRING, host, port, database, username, password);
    }

    private static Connection getNewConnection(String host, int port, String database, String username, String password) {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());


            return DriverManager.getConnection(buildURL(host, port, database, username, password));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Connection getDBConnection() {
        return getNewConnection(host, port, database, username, password);
    }

    public Connection getSystemDBConnection() {
        return getNewConnection(host, port, MYSQL_SYSTEM_DB, username, password);
    }

    public String getDatabaseName() {
        return database;
    }

    @Override
    public String toString() {
        return "MySQLConnectionFactory{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", database='" + database + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
