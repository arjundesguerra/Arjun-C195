package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Homepage {

    @FXML private Button addCustomerButton;

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

}
