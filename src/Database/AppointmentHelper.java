package Database;

import Models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    public static int maxID() throws SQLException {
        int appointmentID = 0;
        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT MAX(Appointment_ID) FROM appointments");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            appointmentID = resultSet.getInt(1) + 1;
        }
        return appointmentID;
    }

    public static void createAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType,
                                         LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) throws SQLException {

        PreparedStatement statement = JDBC.getConnection().prepareStatement("INSERT INTO appointments VALUES(?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP , 'user', CURRENT_TIMESTAMP, 'user', ?, ?, ?);");
        statement.setInt(1, appointmentID);
        statement.setString(2, appointmentTitle);
        statement.setString(3, appointmentDescription);
        statement.setString(4, appointmentLocation);
        statement.setString(5, appointmentType);
        statement.setTimestamp(6, Timestamp.valueOf(startDateTime));
        statement.setTimestamp(7, Timestamp.valueOf(endDateTime));
        statement.setInt(8, customerID);
        statement.setInt(9, userID);
        statement.setInt(10, contactID);

        statement.execute();

    }

    public static void deleteAppointment(int appointmentID) throws SQLException {
        String sqlDC = "DELETE from appointments WHERE Appointment_ID = ?";
        try (PreparedStatement psDC = JDBC.getConnection().prepareStatement(sqlDC)) {
            psDC.setInt(1, appointmentID);
            psDC.execute();
        }
    }

    public static boolean isAppointmentOverlap(LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID) throws SQLException {
        ObservableList<Appointment> appointments = fetchAppointments();

        for (Appointment appointment : appointments) {
            if (appointment.getCustomerID() == customerID && appointment.getStartDateTime().isBefore(endDateTime) && startDateTime.isBefore(appointment.getEndDateTime())) {
                return true;
            }
        }

        return false;
    }



}
