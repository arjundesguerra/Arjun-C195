package Database;

import Models.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactHelper {

    public static ObservableList<Contact> fetchContacts() throws SQLException {
        ObservableList<Contact> contactsList = FXCollections.observableArrayList();

        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT * FROM contacts ");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int contactID = resultSet.getInt("Contact_ID");
            String contactName = resultSet.getString("Contact_Name");
            String email = resultSet.getString("Email");

            Contact contact = new Contact(contactID, contactName, email);
            contactsList.add(contact);
        }

        return contactsList;
    }

    public static int getContactIDByName(String contactName) throws SQLException {
        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT Contact_ID FROM contacts WHERE Contact_Name = ?");
        statement.setString(1, contactName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("Contact_ID");
        } else {
            throw new SQLException(contactName + " not found");
        }
    }

}
