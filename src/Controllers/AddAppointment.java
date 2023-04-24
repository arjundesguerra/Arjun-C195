package Controllers;

import Database.AppointmentHelper;
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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AddAppointment {


    @FXML private ComboBox customerComboBox;
    @FXML private ComboBox userComboBox;
    @FXML private ComboBox contactComboBox;
    @FXML private TextField titleTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField locationTextField;
    @FXML private TextField typeTextField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox startTimeComboBox;
    @FXML private ComboBox endTimeComboBox;
    @FXML private Button submitButton;

    public void initialize() throws SQLException {
        submitButton.setFocusTraversable(true);
        Platform.runLater(() -> submitButton.requestFocus());

        setComboBoxes();
        setTimeComboBoxes();
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

    public void submit() throws IOException, SQLException {
        // gets Strings for getIDbyName methods in helper classes
        String customerName = (String) customerComboBox.getValue();
        String userName = (String) userComboBox.getValue();
        String contactName = (String) contactComboBox.getValue();

        if (customerComboBox.getValue() == null || userComboBox.getValue() == null || contactComboBox.getValue() == null ||
                titleTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty() || locationTextField.getText().isEmpty() ||
                typeTextField.getText().isEmpty() || datePicker.getValue() == null || startTimeComboBox.getValue() == null || endTimeComboBox.getValue() == null) {

            // display error message if any fields are empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all fields.");
            alert.showAndWait();
            return;
        }

        int appointmentID = AppointmentHelper.maxID();
        int customerID = CustomerHelper.getCustomerIDByName(customerName);
        int userID = UserHelper.getUserIDByName(userName);
        int contactID = ContactHelper.getContactIDByName(contactName);
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        String type = typeTextField.getText();
        LocalDate date = datePicker.getValue();
        String startTime = (String) startTimeComboBox.getValue();
        String endTime = (String) endTimeComboBox.getValue();

        // turns time strings to date time objects
        LocalDateTime startDateTime = LocalDateTime.parse(date.toString() + " " + startTime + ":00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDateTime = LocalDateTime.parse(date.toString() + " " + endTime + ":00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // checks if end time is after start time
        if (endDateTime.isBefore(startDateTime)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("End time cannot be before start time.");
            alert.showAndWait();
            return;
        }

        // checks if start and end times are equal
        if (endDateTime.isEqual(startDateTime)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An appointment cannot share start and end times.");
            alert.showAndWait();
            return;
        }

        // checks if the new appointment overlaps with existing appointments with the customer
        if (AppointmentHelper.isAppointmentOverlap(startDateTime, endDateTime, customerID, appointmentID)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("This appointment overlaps with an existing appointment with this customer.");
            alert.showAndWait();
            return;
        }

        // checks if appointment starts before 8AM or ends after 10PM in EST timezone
        ZonedDateTime startInEST = startDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime endInEST = endDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
        if (startInEST.toLocalTime().isBefore(LocalTime.of(8, 0)) ||
                endInEST.toLocalTime().isAfter(LocalTime.of(22, 0)) ||
                endInEST.toLocalDate().isAfter(startInEST.toLocalDate()) && endInEST.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Appointments can only be scheduled between 8AM and 10PM EST.");
            alert.showAndWait();
            return;
        }

        AppointmentHelper.createAppointment(appointmentID, title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID);
        goToAppointmentHomepage();

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



}
