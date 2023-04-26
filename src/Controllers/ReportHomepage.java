package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.io.IOException;

public class ReportHomepage {

    @FXML
    private Button backButton;
    @FXML private TableView typeTableView;
    @FXML private TableView monthTableView;
    @FXML private TableView contactTableView;
    @FXML private TableView divisionTableView;

    public void initialize() {

    }

    public void setTypeTable() {

    }

    public void setMonthTable() {

    }

    public void goToHomepage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/Homepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();
    }
}
