package Controllers;

import Database.AppointmentHelper;
import Database.ContactHelper;
import Models.Appointment;
import Models.Contact;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;

public class ReportHomepage {

    @FXML private Button backButton;
    @FXML private TableView typeTableView;
    @FXML private TableColumn typeColumn;
    @FXML private TableColumn typeTotalColumn;
    @FXML private TableView monthTableView;
    @FXML private TableColumn monthColumn;
    @FXML private TableColumn monthTotalColumn;
    @FXML private TableView contactTableView;
    @FXML private TableColumn contactAppointmentIDColumn;
    @FXML private TableColumn contactTitleColumn;
    @FXML private TableColumn contactTypeColumn;
    @FXML private TableColumn contactDescriptionColumn;
    @FXML private TableColumn contactStartColumn;
    @FXML private TableColumn contactEndColumn;
    @FXML private TableColumn contactCustomerID;
    @FXML private ComboBox contactComboBox;
    @FXML private TableView divisionTableView;

    public void initialize() throws SQLException {
        setTypeTable();
        setMonthTable();
        setContactComboBox();

        contactComboBox.setOnAction(e -> {
            try {
                setContactTable();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void setTypeTable() throws SQLException {
        ObservableList<Appointment> appointments = AppointmentHelper.fetchAppointments();
        HashMap<String, Integer> typeMap = new HashMap<>();

        for (Appointment appointment : appointments) {
            String type = appointment.getAppointmentType();
            if (typeMap.containsKey(type)) {
                typeMap.put(type, typeMap.get(type) + 1);
            } else {
                typeMap.put(type, 1);
            }
        }

        ObservableList<HashMap.Entry<String, Integer>> typeDataList = FXCollections.observableArrayList(typeMap.entrySet());

        typeColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<HashMap.Entry<String, Integer>, String>, ObservableValue<String>>)
                cellData -> new SimpleStringProperty(cellData.getValue().getKey()));

        typeTotalColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<HashMap.Entry<String, Integer>, Integer>, ObservableValue<Integer>>)
                cellData -> new SimpleIntegerProperty(cellData.getValue().getValue()).asObject());

        typeTableView.setItems(typeDataList);
    }


    public void setMonthTable() throws SQLException {
        ObservableList<Appointment> appointments = AppointmentHelper.fetchAppointments();
        HashMap<String, Integer> monthMap = new HashMap<>();

        for (Appointment appointment : appointments) {
            LocalDateTime startDateTime = appointment.getStartDateTime();
            String month = startDateTime.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
            if (monthMap.containsKey(month)) {
                monthMap.put(month, monthMap.get(month) + 1);
            } else {
                monthMap.put(month, 1);
            }
        }

        ObservableList<HashMap.Entry<String, Integer>> monthDataList = FXCollections.observableArrayList(monthMap.entrySet());

        monthColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<HashMap.Entry<String, Integer>, String>, ObservableValue<String>>)
                cellData -> new SimpleStringProperty(cellData.getValue().getKey()));

        monthTotalColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<HashMap.Entry<String, Integer>, Integer>, ObservableValue<Integer>>)
                cellData -> new SimpleIntegerProperty(cellData.getValue().getValue()).asObject());

        monthTableView.setItems(monthDataList);
    }

    public void setContactTable() throws SQLException {
        String selectedContact = (String) contactComboBox.getValue();
        ObservableList<Appointment> appointments = AppointmentHelper.fetchAppointmentsByContact(selectedContact);

        contactAppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        contactTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        contactTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        contactDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        contactStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        contactEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        contactCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        contactTableView.setItems(appointments);
    }


    public void setContactComboBox() throws SQLException {
        ObservableList<Contact> contactsList = ContactHelper.fetchContacts();
        for (Contact contact : contactsList) {
            contactComboBox.getItems().add(contact.getContactName());
        }
    }

    public void setDivisionTable() {

    }

    public void goToHomepage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/Homepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();
    }
}
