package p2;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainSceneController {

    @FXML
    private BorderPane main;
    @FXML
    private AnchorPane headerPane;
    @FXML
    private AnchorPane anchorpanevboxmainpane;
    @FXML
    private VBox vboxmainpane;
    @FXML
    private HBox hboxitemsmainpane;
    @FXML
    private Pane paneitemsmainpane;
    @FXML
    private Button StudentsBtn;
    @FXML
    private Button CoursesBtn;
    @FXML
    private ImageView Logout;
    @FXML
    private Button DepartmentBtn;
    @FXML
    private Button GradesBtn;
    @FXML
    private Button ReportsBtn;

    @FXML
    private void initialize() {
        // Set event handlers for button scaling
        setButtonScaling(StudentsBtn);
        setButtonScaling(CoursesBtn);
        setButtonScaling(DepartmentBtn);
        setButtonScaling(GradesBtn);
        setButtonScaling(ReportsBtn);
    }

    private void setButtonScaling(Button button) {
        button.setOnMouseEntered(event -> handleButtonScaleOnMouseEntered(event, button));
        button.setOnMouseExited(event -> handleButtonScaleOnMouseExited(event, button));
    }

    private void handleButtonScaleOnMouseEntered(MouseEvent event, Button button) {
        // Create a ScaleTransition for the button
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);

        // Set the initial and target scale values
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);

        // Play the animation
        scaleTransition.play();
    }

    private void handleButtonScaleOnMouseExited(MouseEvent event, Button button) {
        // Create a ScaleTransition for the button
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);

        // Set the initial and target scale values
        scaleTransition.setFromX(1.3);
        scaleTransition.setFromY(1.3);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        // Play the animation
        scaleTransition.play();
    }

    @FXML
    private void Students() throws IOException {
        loadFXML("FXML.fxml");
    }

    @FXML
    private void Courses() throws IOException {
        loadFXML("secondpane.fxml");
    }

    @FXML
    private void Department() throws IOException {
        loadFXML("thirdpane.fxml");
    }

    @FXML
    private void Grades() throws IOException {
        loadFXML("Grades.fxml");
    }

    @FXML
    private void Reports() throws IOException {
        loadFXML("reports.fxml");
    }

    @FXML
    private void Logout(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadFXML(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
        Parent root = fxmlLoader.load();
        main.setCenter(root);
    }
}
