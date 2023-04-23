package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Optional;

public class Homepage {

    @FXML private Text locationText;
    @FXML private Button goToCustomersButton;

    public void initialize() {
        checkLocation();
    }

    public void checkLocation() {
        ZoneId zone = ZoneId.systemDefault();
        locationText.setText("Location: " + zone);
    }

    public void goToCustomers() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/CustomerHomepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Customers Homepage");
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

    public void goToLogin() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("Click OK to confirm, or Cancel to stay logged in.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/LoginForm.fxml"));
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Login");
            newStage.setScene(scene);
            newStage.show();
            Stage currentStage = (Stage) goToCustomersButton.getScene().getWindow();
            currentStage.close();
        }
    }



}
