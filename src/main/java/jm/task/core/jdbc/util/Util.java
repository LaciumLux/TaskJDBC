package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/database";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final Logger LOGGER = Logger.getLogger(Util.class.getName());

    private Util() {
        //default implementation
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            LOGGER.info("Установка соединения: Complete!");
        } catch (SQLException e) {
            LOGGER.info("Не удалось установить соединение.");
        }
        return connection;
    }

}
