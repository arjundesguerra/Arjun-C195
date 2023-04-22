package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

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
}
