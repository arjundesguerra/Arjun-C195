package Controllers;

import Database.AppointmentHelper;
import Models.Appointment;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

public class Homepage {

    @FXML private Text locationText;
    @FXML private Button goToCustomersButton;

    public void initialize() {
        checkLocation();
    }

    public static void appointmentNotification() throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fifteenMinutesLater = now.plusMinutes(15);

        ObservableList<Appointment> appointments = AppointmentHelper.fetchAppointmentsByTime(now, fifteenMinutesLater);

        if (appointments.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("There are no appointments scheduled within the next 15 minutes.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Upcoming appointment(s) in the next 15 minutes: \n");
            String content = "";
            for (Appointment appointment : appointments) {
                content += "Appointment ID: " + appointment.getAppointmentID() + "\n";
                content += "Date: " + appointment.getStartDateTime().toLocalDate().toString() + "\n";
                content += "Time: " + appointment.getStartDateTime().toLocalTime().toString() + " - " + appointment.getEndDateTime().toLocalTime().toString() + "\n\n";
            }
            alert.setContentText(content);
            alert.showAndWait();
        }
    }

    public void checkLocation() {
        ZoneId zone = ZoneId.systemDefault();
        locationText.setText("Location: " + zone);
    }

    public void goToCustomers() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/CustomerHomepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Customers Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) goToCustomersButton.getScene().getWindow();
        currentStage.close();
    }

    public void goToAppointments() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/AppointmentHomepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Appointments Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) goToCustomersButton.getScene().getWindow();
        currentStage.close();
    }

    public void goToReports() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/ReportHomepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Reports Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) goToCustomersButton.getScene().getWindow();
        currentStage.close();
    }

    public void goToLogin() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("Click OK to confirm, or Cancel to stay logged in.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/LoginForm.fxml"));
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Login");
            newStage.setScene(scene);
            newStage.show();
            Stage currentStage = (Stage) goToCustomersButton.getScene().getWindow();
            currentStage.close();
        }
    }



}
