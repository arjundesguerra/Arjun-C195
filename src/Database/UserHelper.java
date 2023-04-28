package Database;

import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHelper {

    public static ObservableList<User> fetchUsers() throws SQLException {
        ObservableList<User> usersList = FXCollections.observableArrayList();

        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT * FROM users ");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int userID = resultSet.getInt("User_ID");
            String username = resultSet.getString("User_Name");
            String password = resultSet.getString("Password");

            User user = new User(userID, username, password);
            usersList.add(user);
        }

        return usersList;
    }

    public static boolean validateUser(String username, String password) throws SQLException {
        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT User_ID FROM users WHERE User_Name = ? AND Password = ?");
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }


    public static int getUserIDByName(String userName) throws SQLException {
        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT User_ID FROM users WHERE User_Name = ?");
        statement.setString(1, userName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("User_ID");
        } else {
            throw new SQLException(userName + " not found");
        }
    }

    public static String getUserNameByID(int userID) throws SQLException {
        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT User_Name FROM users WHERE User_ID = ?");
        statement.setInt(1, userID);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("User_Name");
        } else {
            throw new SQLException(userID + " not found");
        }
    }

}
