package Database;

import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class CustomerHelper {

    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try (Connection conn = JDBC.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT Customer_ID, Customer_Name, Phone, Address, Division, Country, Postal_Code " +
                     "FROM customers");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int customerID = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String phoneNumber = resultSet.getString("Phone");
                String address = resultSet.getString("Address");
                String division = resultSet.getString("Division");
                String country = resultSet.getString("Country");
                String postalCode = resultSet.getString("Postal_Code");

                Customer customer = new Customer(customerID, customerName, address, postalCode, phoneNumber, division, country);
                customerList.add(customer);
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return customerList;
    }

}
