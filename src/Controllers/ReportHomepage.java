package Controllers;

import Database.AppointmentHelper;
import Models.Appointment;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;

public class ReportHomepage {

    @FXML
    private Button backButton;
    @FXML
    private TableView typeTableView;
    @FXML
    private TableColumn typeColumn;
    @FXML
    private TableColumn typeTotalColumn;
    @FXML
    private TableView monthTableView;
    @FXML
    private TableColumn monthColumn;
    @FXML
    private TableColumn monthTotalColumn;
    @FXML
    private TableView contactTableView;
    @FXML
    private TableView divisionTableView;

    public void initialize() throws SQLException {
        setTypeTable();
        setMonthTable();
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


    public void setContactTable() {

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
