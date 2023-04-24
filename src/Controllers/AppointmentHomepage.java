package Controllers;

import Database.AppointmentHelper;
import Models.Appointment;
import Models.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AppointmentHomepage {
    @FXML
    private TableView appointmentTable;
    @FXML private TableColumn appointmentIDColumn;
    @FXML private TableColumn appointmentTitleColumn;
    @FXML private TableColumn appointmentDescriptionColumn;
    @FXML private TableColumn appointmentLocationColumn;
    @FXML private TableColumn appointmentTypeColumn;
    @FXML private TableColumn appointmentStartColumn;
    @FXML private TableColumn appointmentEndColumn;
    @FXML private TableColumn appointmentCustomerID;
    @FXML private TableColumn appointmentUserID;
    @FXML private TableColumn appointmentContactID;
    @FXML private Button addAppointmentButton;

    public void initialize() throws SQLException {
        appointmentTable.setItems(AppointmentHelper.fetchAppointments());
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        appointmentCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));

    }

    public void goToAddAppointment() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/AddAppointment.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Add Appointment");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) addAppointmentButton.getScene().getWindow();
        currentStage.close();
    }

    public void goToEditAppointment() throws IOException, SQLException {
        Appointment selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer to edit.", ButtonType.OK);
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLViews/EditAppointment.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Edit Appointment");
            newStage.setScene(scene);

            EditAppointment editAppointment = loader.getController();
            editAppointment.setAppointmentData(selectedAppointment.getAppointmentID(), selectedAppointment.getAppointmentTitle(), selectedAppointment.getAppointmentDescription(),
                    selectedAppointment.getAppointmentLocation(),selectedAppointment.getAppointmentType(), selectedAppointment.getStartDateTime(), selectedAppointment.getEndDateTime(), selectedAppointment.getCustomerID(),
                    selectedAppointment.getUserID(), selectedAppointment.getContactID());

            newStage.show();
            Stage currentStage = (Stage) addAppointmentButton.getScene().getWindow();
            currentStage.close();
        }

    }

    public void deleteAppointment() throws SQLException {
        Appointment selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment to delete.", ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this appointment?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                AppointmentHelper.deleteAppointment(selectedAppointment.getAppointmentID());
                appointmentTable.setItems(AppointmentHelper.fetchAppointments());
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Appointment deleted successfully.", ButtonType.OK);
                successAlert.showAndWait();
            }
        }
    }

    public void goToHomepage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/Homepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) addAppointmentButton.getScene().getWindow();
        currentStage.close();
    }
}
