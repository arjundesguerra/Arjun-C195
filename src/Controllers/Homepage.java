package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class Homepage {

    @FXML private Button goToCustomersButton;

    public void goToCustomers() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/CustomerHomepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Customer Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) goToCustomersButton.getScene().getWindow();
        currentStage.close();
    }

    public void goToAppointments() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/AppointmentHomepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Appointments Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) goToCustomersButton.getScene().getWindow();
        currentStage.close();
    }


}
