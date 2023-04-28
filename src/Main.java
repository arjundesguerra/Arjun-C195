import Database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLViews/LoginForm.fxml"));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        if (Locale.getDefault().getLanguage().equals("fr")) {
            primaryStage.setTitle("Connexion");
        } else {
            primaryStage.setTitle("Login");
        }
    }


    public static void main(String[] args) {
        JDBC.makeConnection();
        launch(args);
    }
}
