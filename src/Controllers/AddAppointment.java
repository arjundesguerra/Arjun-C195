package Controllers;

import Database.ContactHelper;
import Database.CustomerHelper;
import Database.UserHelper;
import Models.Contact;
import Models.Customer;
import Models.User;
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
    @FXML private ComboBox userComboBox;
    @FXML private ComboBox contactComboBox;
    @FXML private Button submitButton;

    public void initialize() throws SQLException {
        submitButton.setFocusTraversable(true);
        Platform.runLater(() -> submitButton.requestFocus());

        setComboBoxes();
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

    public void setComboBoxes() throws SQLException {
        ObservableList<Customer> customerList = CustomerHelper.fetchCustomers();
        for (Customer customer : customerList) {
            customerComboBox.getItems().add(customer.getCustomerName());
        }

        ObservableList<User> usersList = UserHelper.fetchUsers();
        for (User user : usersList) {
            userComboBox.getItems().add(user.getUsername());
        }

        ObservableList<Contact> contactsList = ContactHelper.fetchContacts();
        for (Contact contact : contactsList) {
            contactComboBox.getItems().add(contact.getContactName());
        }
    }




}
