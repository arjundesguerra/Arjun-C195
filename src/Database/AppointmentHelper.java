package Database;

import Models.Appointment;
import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentHelper {

    public static ObservableList<Customer> fetchAppointments() throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT * FROM customers JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID "
                + "JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID ORDER BY Customer_ID ");

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
}
