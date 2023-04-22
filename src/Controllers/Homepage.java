package Controllers;

import Database.AppointmentHelper;
import Database.CustomerHelper;
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

public class Homepage {

    @FXML private Button addCustomerButton;
    @FXML private TableView customerTable;
    @FXML private TableColumn customerIdColumn;
    @FXML private TableColumn customerNameColumn;
    @FXML private TableColumn customerNumberColumn;
    @FXML private TableColumn customerAddressColumn;
    @FXML private TableColumn customerDivisionColumn;
    @FXML private TableColumn customerCountryColumn;
    @FXML private TableColumn customerPostalCodeColumn;
    @FXML private TableView appointmentTable;
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



    public void initialize() throws SQLException {
        customerTable.setItems(CustomerHelper.fetchCustomers());

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerNumberColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
        customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));

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

    public void goToAddCustomer() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/AddCustomer.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Add Customer");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) addCustomerButton.getScene().getWindow();
        currentStage.close();
    }

    public void goToEditCustomer() throws IOException {
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer to edit.", ButtonType.OK);
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLViews/EditCustomer.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Edit Customer");
            newStage.setScene(scene);

            EditCustomer editCustomer = loader.getController();
            editCustomer.setCustomerData(selectedCustomer.getCustomerID(), selectedCustomer.getCustomerName(), selectedCustomer.getCustomerPhoneNumber(),
                    selectedCustomer.getCustomerAddress(), selectedCustomer.getCustomerPostalCode(), selectedCustomer.getCustomerCountry(), selectedCustomer.getCustomerDivision());


            newStage.show();
            Stage currentStage = (Stage) addCustomerButton.getScene().getWindow();
            currentStage.close();
        }
    }


    public void deleteCustomer() throws SQLException {
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer to delete.", ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                CustomerHelper.deleteCustomer(selectedCustomer.getCustomerID());
                customerTable.setItems(CustomerHelper.fetchCustomers());
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully.", ButtonType.OK);
                successAlert.showAndWait();
            }
        }
    }


}
