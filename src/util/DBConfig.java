package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConfig {

    private static final String PROPERTIES_FILE = "src/env.properties";

    private static Connection con;

    private DBConfig() {
    }

    private static Connection getConnection() {
        if (con == null) {

            try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
                Properties prop = new Properties();
                prop.load(input);

                String url = prop.getProperty("db.url");
                String username = prop.getProperty("db.username");
                String password = prop.getProperty("db.password");

                con = DriverManager.getConnection(url, username, password);
                System.out.println("Database Connection Established Successfully!");

            } catch (IOException e) {
                System.err.println("Failed to load env properties file.");
                e.printStackTrace();

            } catch (SQLException e) {
                System.err.println("Connection Failed! Check database URL, username, or password.");
                e.printStackTrace();
            }
        }
        return con;
    }

    public static Connection getInstance() {
        return getConnection();
    }

}
