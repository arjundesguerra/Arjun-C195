package Controllers;

import Database.CustomerHelper;
import Database.DivisionHelper;
import Models.Division;
import javafx.application.Platform;
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

public class AddCustomer {

    private int iterateID = 4;
    @FXML private TextField nameTextField;
    @FXML private TextField numberTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField postalCodeTextField;
    @FXML private ComboBox countryComboBox;
    @FXML private ComboBox<Division> divisionComboBox;
    @FXML private Button cancelButton;
    @FXML private Button submitButton;

    public void initialize() {
        submitButton.setFocusTraversable(true);
        Platform.runLater(() -> submitButton.requestFocus());

        countryComboBox.setOnAction(event -> {
            try {
                divisionSelector();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

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
        int customerID = iterateID;
        String customerName = nameTextField.getText();
        String customerNumber = numberTextField.getText();
        String customerAddress = addressTextField.getText();
        String customerPostalCode = postalCodeTextField.getText();
        int customerDivision = divisionComboBox.getValue().getDivisionID();

        CustomerHelper.createCustomer(customerID, customerName, customerAddress, customerPostalCode, customerNumber, customerDivision);
        goToHomepage();
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
