package Database;

import Models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

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

    public static ObservableList<Appointment> fetchAppointmentsByMonth() throws SQLException {
        ObservableList<Appointment> monthAppointmentList = FXCollections.observableArrayList();

        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.lengthOfMonth());
        LocalDate startDate = currentDate.withDayOfMonth(1);

        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT * FROM appointments WHERE Start >= ? AND Start <= ?");
        statement.setTimestamp(1, Timestamp.valueOf(startDate.atStartOfDay()));
        statement.setTimestamp(2, Timestamp.valueOf(endDate.plusDays(1).atStartOfDay()));

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
            monthAppointmentList.add(appointment);
        }

        return monthAppointmentList;
    }

    public static ObservableList<Appointment> fetchAppointmentsByWeek() throws SQLException {
        ObservableList<Appointment> weekAppointmentList = FXCollections.observableArrayList();

        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).minusDays(1);
        LocalDate startDate = currentDate;

        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT * FROM appointments WHERE Start >= ? AND Start <= ?");
        statement.setTimestamp(1, Timestamp.valueOf(startDate.atStartOfDay()));
        statement.setTimestamp(2, Timestamp.valueOf(endDate.plusDays(1).atStartOfDay()));

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
            weekAppointmentList.add(appointment);
        }

        return weekAppointmentList;
    }

    public static ObservableList<Appointment> fetchAppointmentsByTime(LocalDateTime start, LocalDateTime end) throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT * FROM appointments WHERE Start >= ? AND Start < ?");
        statement.setTimestamp(1, Timestamp.valueOf(start));
        statement.setTimestamp(2, Timestamp.valueOf(end));

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

    public static void editAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType,
                                         LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) throws SQLException {

        PreparedStatement statement = JDBC.getConnection().prepareStatement("UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = CURRENT_TIMESTAMP, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?");
        statement.setString(1, appointmentTitle);
        statement.setString(2, appointmentDescription);
        statement.setString(3, appointmentLocation);
        statement.setString(4, appointmentType);
        statement.setTimestamp(5, Timestamp.valueOf(startDateTime));
        statement.setTimestamp(6, Timestamp.valueOf(endDateTime));
        statement.setInt(7, customerID);
        statement.setInt(8, userID);
        statement.setInt(9, contactID);
        statement.setInt(10, appointmentID);


        statement.execute();

    }

    public static void deleteAppointment(int appointmentID) throws SQLException {
        String sqlDC = "DELETE from appointments WHERE Appointment_ID = ?";
        try (PreparedStatement psDC = JDBC.getConnection().prepareStatement(sqlDC)) {
            psDC.setInt(1, appointmentID);
            psDC.execute();
        }
    }

    public static boolean isAppointmentOverlap(LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int appointmentID) throws SQLException {
        ObservableList<Appointment> appointments = fetchAppointments();

        for (Appointment appointment : appointments) {
            if (appointment.getCustomerID() == customerID && appointment.getAppointmentID() != appointmentID && appointment.getStartDateTime().isBefore(endDateTime) && startDateTime.isBefore(appointment.getEndDateTime())) {
                return true;
            }
        }

        return false;
    }

    public static boolean customerHasAppointments(int customerID) throws SQLException {
        ObservableList<Appointment> appointments = fetchAppointments();

        for (Appointment appointment : appointments) {
            if (appointment.getCustomerID() == customerID) {
                return true;
            }
        }

        return false;
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




}
