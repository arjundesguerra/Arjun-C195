package Database;

import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class CustomerHelper {

    public static ObservableList<Customer> fetchCustomers() throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT Customer_ID, Customer_Name, Phone, Address, Division, Country, Postal_Code " +
                "FROM customers " +
                "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                "JOIN countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID ");

        ResultSet resultSet = statement.executeQuery();

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

        return customerList;
    }

    public static void createCustomer(int customerID, String customerName, String customerAddress, String customerPostalCode,
                                      String customerPhoneNumber, int customerDivisionID) throws SQLException {

        PreparedStatement statement = JDBC.getConnection().prepareStatement("INSERT INTO customers VALUES(?, ?, ?, ?, ?, NULL, NULL, CURRENT_TIMESTAMP , NULL, ?);");

        statement.setInt(1, customerID);
        statement.setString(2, customerName);
        statement.setString(3, customerAddress);
        statement.setString(4, customerPostalCode);
        statement.setString(5, customerPhoneNumber);
        statement.setInt(6, customerDivisionID);

        statement.execute();

    }

    public static void deleteCustomer(int customerID) throws SQLException {
        String sqlDC = "DELETE from customers WHERE Customer_ID = ?";
        try (PreparedStatement psDC = JDBC.getConnection().prepareStatement(sqlDC)) {
            psDC.setInt(1, customerID);
            psDC.execute();
        }
    }


    public static int maxID() throws SQLException {
        int customerID = 0;
        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT MAX(Customer_ID) FROM customers");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            customerID = resultSet.getInt(1) + 1;
        }
        return customerID;
    }

}
