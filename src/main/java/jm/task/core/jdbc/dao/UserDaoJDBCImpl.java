package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE user \n" +
                    " (id INT NOT NULL AUTO_INCREMENT,\n" +
                    " name VARCHAR(255),\n" +
                    " lastName VARCHAR(255),\n" +
                    " age TINYINT(3),\n" +
                    " PRIMARY KEY (id))\n" +
                    "ENGINE = InnoDB\n" +
                    "DEFAULT CHARACTER SET = utf8;\n");
            System.out.println("Таблица успешно создана.");
        } catch (SQLSyntaxErrorException ex) {
            System.out.println("Таблица уже существует!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection is closed");
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE user");
            System.out.println("Таблица успешно удалена.");
        } catch (SQLSyntaxErrorException ex) {
            System.out.println("Таблицы не существует!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection is closed");
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

            System.out.println("User с именем - " + name + " добавлен в базу данных." );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection is closed");
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?")) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection is closed");
        System.out.println(userList);
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE user");
            System.out.println("Таблица успешно очищена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection is closed");
    }
}
