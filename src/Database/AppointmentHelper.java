package Database;

import Models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AppointmentHelper {

    public static ObservableList<Appointment> fetchAppointments() throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT * FROM appointments ");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int appointmentID = resultSet.getInt("Appointment_ID");
            String appointmentTitle = resultSet.getString("Title");
            String appointmentDescription = resultSet.getString("Description");
            String appointmentLocation = resultSet.getString("Location");
            String appointmentType = resultSet.getString("Type");
            LocalDateTime startDateTime = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDateTime = resultSet.getTimestamp("End").toLocalDateTime();
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");


            Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, startDateTime, endDateTime, customerID, userID, contactID);
            appointmentList.add(appointment);
        }

        return appointmentList;
    }

}
