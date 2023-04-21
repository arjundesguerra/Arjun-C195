package Controllers;

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
    @FXML private Button editCustomerButton;
    @FXML private Button deleteCustomerButton;
    @FXML private TableView customerTable;
    @FXML private TableColumn idColumn;
    @FXML private TableColumn nameColumn;
    @FXML private TableColumn numberColumn;
    @FXML private TableColumn addressColumn;
    @FXML private TableColumn divisionColumn;
    @FXML private TableColumn countryColumn;
    @FXML private TableColumn postalCodeColumn;

    public void initialize() throws SQLException {
        customerTable.setItems(CustomerHelper.fetchCustomers());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));

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
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/EditCustomer.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Edit Customer");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) addCustomerButton.getScene().getWindow();
        currentStage.close();
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
            }
        }
    }

}
