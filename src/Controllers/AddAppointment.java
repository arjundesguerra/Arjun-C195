package Controllers;

import Database.ContactHelper;
import Database.CustomerHelper;
import Database.UserHelper;
import Models.Contact;
import Models.Customer;
import Models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AddAppointment {

    @FXML private TextField titleTextField;
    @FXML private ComboBox customerComboBox;
    @FXML private ComboBox userComboBox;
    @FXML private ComboBox contactComboBox;
    @FXML private ComboBox startTimeComboBox;
    @FXML private ComboBox endTimeComboBox;
    @FXML private Button submitButton;

    public void initialize() throws SQLException {
        submitButton.setFocusTraversable(true);
        Platform.runLater(() -> submitButton.requestFocus());

        setComboBoxes();
        setTimeComboBoxes();
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

    public void setTimeComboBoxes() {
        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(23, 30);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        List<String> timeList = new ArrayList<>();
        LocalTime currentTime = startTime;
        while (currentTime.isBefore(endTime)) {
            String timeStr = currentTime.format(formatter);
            timeList.add(timeStr);
            currentTime = currentTime.plusMinutes(30);
        }
        // separate 23:30 avoids heap failure
        timeList.add("23:30");

        ObservableList<String> timeObservableList = FXCollections.observableArrayList(timeList);
        startTimeComboBox.setItems(timeObservableList);
        endTimeComboBox.setItems(timeObservableList);
    }



}
