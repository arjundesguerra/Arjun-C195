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

}
