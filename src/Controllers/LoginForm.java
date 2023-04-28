package Controllers;

import Database.UserHelper;
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

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;


public class LoginForm {

    @FXML
    private Text userLoginText;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text locationText;
    @FXML
    private Button signInButton;
    private ResourceBundle bundle;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void initialize() {
        if (Locale.getDefault().getLanguage().equals("fr")) {
            bundle = ResourceBundle.getBundle("ResourceBundles/fr_login");
            setFrench();
        }

        signInButton.setFocusTraversable(true);
        Platform.runLater(() -> signInButton.requestFocus());

        checkLocation();
    }

    public void checkLocation() {
        ZoneId zone = ZoneId.systemDefault();
        if (Locale.getDefault().getLanguage().equals("fr")) {
            locationText.setText(bundle.getString("Location") + ": " + zone);
        } else {
            locationText.setText("Location: " + zone);
        }
    }

    public void signIn() throws IOException, SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String loginStatus;

        boolean isValidUser = UserHelper.validateUser(username, password);
        if (isValidUser) {
            loginStatus = "Success";
        } else {
            loginStatus = "Failure";
        }

        String logMessage = String.format("[%s] Login attempt by user '%s': %s\n", LocalDateTime.now().format(formatter), username, loginStatus);
        try (FileWriter writer = new FileWriter("login_activity.txt", true)) {
            writer.write(logMessage);
        }

        if (isValidUser) {
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
            if (Locale.getDefault().getLanguage().equals("fr")) {
                alert.setTitle(bundle.getString("InvalidAlertTitle"));
                alert.setContentText(bundle.getString("InvalidAlertDescription"));
            } else {
                alert.setTitle("Sign-In Failed");
                alert.setContentText("Incorrect username or password.");
            }
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    public void setFrench() {
        userLoginText.setText(bundle.getString("UserLogin"));
        usernameField.setPromptText(bundle.getString("Username"));
        passwordField.setPromptText(bundle.getString("Password"));
        signInButton.setText(bundle.getString("SignIn"));
    }
}
