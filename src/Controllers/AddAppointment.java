package Controllers;

import Database.CustomerHelper;
import Models.Customer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AddAppointment {

    @FXML private TextField titleTextField;
    @FXML private ComboBox customerComboBox;
    @FXML private Button submitButton;

    public void initialize() throws SQLException {
        submitButton.setFocusTraversable(true);
        Platform.runLater(() -> submitButton.requestFocus());

        setCustomerComboBox();
    }

    public void goToAppointmentHomepage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/AppointmentHomepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Appointments Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) titleTextField.getScene().getWindow();
        currentStage.close();
    }

    public void setCustomerComboBox() throws SQLException {
        ObservableList<Customer> customerList = CustomerHelper.fetchCustomers();
        for (Customer customer : customerList) {
            customerComboBox.getItems().add(customer.getCustomerName());
        }

    }
}
