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
                     "FROM customers, countries, first_level_divisions " +
                     "WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.COUNTRY_ID = countries.Country_ID");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int customerID = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String customerPhoneNumber = resultSet.getString("Phone");
                String customerAddress = resultSet.getString("Address");
                String customerDivision = resultSet.getString("Division");
                String customerCountry = resultSet.getString("Country");
                String customerPostalCode = resultSet.getString("Postal_Code");

                Customer customer = new Customer(customerID, customerName, customerPhoneNumber, customerAddress, customerDivision, customerCountry, customerPostalCode);
                customerList.add(customer);
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return customerList;
    }

}
