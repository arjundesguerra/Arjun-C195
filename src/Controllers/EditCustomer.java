package Controllers;

import Database.CustomerHelper;
import Database.DivisionHelper;
import Models.Customer;
import Models.Division;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EditCustomer {

    @FXML private TextField nameTextField;
    @FXML private TextField numberTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField postalCodeTextField;
    @FXML private ComboBox countryComboBox;
    @FXML private ComboBox divisionComboBox;
    @FXML private Button cancelButton;
    @FXML private Button submitButton;
    @FXML private TextField idField;
    private int customerID;
    private String customerName;
    private String customerNumber;
    private String customerAddress;
    private String customerPostalCode;
    private String customerCountry;
    private String customerDivision;


    public void initialize() throws SQLException {
        submitButton.setFocusTraversable(true);
        Platform.runLater(() -> submitButton.requestFocus());
    }

    public void setCustomerData(int customerID, String customerName, String customerNumber, String customerAddress, String customerPostalCode,
                                String customerCountry, String customerDivision) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerNumber = customerNumber;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerCountry = customerCountry;
        this.customerDivision = customerDivision;

        idField.setText(Integer.toString(customerID));
        nameTextField.setText(customerName);
        numberTextField.setText(customerNumber);
        addressTextField.setText(customerAddress);
        postalCodeTextField.setText(customerPostalCode);
        divisionComboBox.setValue(customerDivision);

        if (customerCountry.equals("U.S")) {
            countryComboBox.setValue("United States");
        } else if (customerCountry.equals("UK")) {
            countryComboBox.setValue("United Kingdom");
        } else if (customerCountry.equals("Canada")) {
            countryComboBox.setValue("Canada");
        }

        try {
            divisionSelector();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void divisionSelector() throws SQLException {
        int countryID;
        if (countryComboBox.getValue().equals("United States")) {
            countryID = 1;
            divisionComboBox.setItems(DivisionHelper.fetchDivisions(countryID));
        } else if (countryComboBox.getValue().equals("United Kingdom")) {
            countryID = 2;
            divisionComboBox.setItems(DivisionHelper.fetchDivisions(countryID));
        } else if (countryComboBox.getValue().equals("Canada")) {
            countryID = 3;
            divisionComboBox.setItems(DivisionHelper.fetchDivisions(countryID));
        }
    }

    public void submit() throws IOException, SQLException {
        int customerID = CustomerHelper.maxID();
        String customerName = nameTextField.getText();
        String customerNumber = numberTextField.getText();
        String customerAddress = addressTextField.getText();
        String customerPostalCode = postalCodeTextField.getText();

        if(customerName.isEmpty() || customerNumber.isEmpty() || customerAddress.isEmpty() || customerPostalCode.isEmpty()
                || divisionComboBox.getValue() == null || countryComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill out all fields.", ButtonType.OK);
            alert.showAndWait();
        } else {
            Division selectedDivision = (Division) divisionComboBox.getValue();
            int customerDivision = selectedDivision.getDivisionID();
            CustomerHelper.createCustomer(customerID, customerName, customerAddress, customerPostalCode, customerNumber, customerDivision);
            goToHomepage();
        }
    }

    public void goToHomepage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/Homepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) cancelButton.getScene().getWindow();
        currentStage.close();
    }

}
