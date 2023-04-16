package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.ZoneId;


public class LoginForm {

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Button signInButton;
    @FXML private Text locationText;





    public void initialize() {
        checkLocation();
    }

    public void checkLocation() {
        ZoneId zone = ZoneId.systemDefault();
        locationText.setText("Location: " + zone);
    }

    public void signIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.equals("test") && password.equals("test")) {

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sign-In Failed");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect username or password.");
            alert.showAndWait();
        }
    }
}
