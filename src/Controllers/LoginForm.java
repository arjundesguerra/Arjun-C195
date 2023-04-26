package Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;


public class LoginForm {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Text locationText;
    @FXML private Button signInButton;


    public void initialize() {
        signInButton.setFocusTraversable(true);
        Platform.runLater(() -> signInButton.requestFocus());

        checkLocation();
    }

    public void checkLocation() {
        ZoneId zone = ZoneId.systemDefault();
        locationText.setText("Location: " + zone);
    }

    public void signIn() throws IOException, SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.equals("test") && password.equals("test")) {
            Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/Homepage.fxml"));
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Homepage");
            newStage.setScene(scene);
            newStage.show();
            Homepage.appointmentNotification();
            Stage currentStage = (Stage) locationText.getScene().getWindow();
            currentStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sign-In Failed");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect username or password.");
            alert.showAndWait();
        }
    }
}
