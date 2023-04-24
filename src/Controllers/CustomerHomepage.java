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

public class CustomerHomepage {

    @FXML private Button backButton;
    @FXML private Button addCustomerButton;
    @FXML private TableView customerTable;
    @FXML private TableColumn customerIdColumn;
    @FXML private TableColumn customerNameColumn;
    @FXML private TableColumn customerNumberColumn;
    @FXML private TableColumn customerAddressColumn;
    @FXML private TableColumn customerDivisionColumn;
    @FXML private TableColumn customerCountryColumn;
    @FXML private TableColumn customerPostalCodeColumn;


    public void initialize() throws SQLException {
        customerTable.setItems(CustomerHelper.fetchCustomers());

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerNumberColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
        customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));

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

    public void goToHomepage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/Homepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) addCustomerButton.getScene().getWindow();
        currentStage.close();
    }




}
