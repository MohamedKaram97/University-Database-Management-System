package p2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SecondpaneController implements Initializable {

    @FXML
    private TableView<DTO2> secondTableView;

    @FXML
    private TableColumn<DTO2, Integer> ColumnID1;

    @FXML
    private TableColumn<DTO2, String> ColumnID2;

    @FXML
    private TableColumn<DTO2, String> ColumnID3;

    @FXML
    private TableColumn<DTO2, Integer> ColumnID4;

    @FXML
    private TableColumn<DTO2, Integer> ColumnID5;

    @FXML
    private TextField CourseIDFeild;

    @FXML
    private TextField CourseNameField;

    @FXML
    private TextField CourseCreditHoursField;

    @FXML
    private Button UpdateCourse;

    @FXML
    private Button DeleteCourse;

    @FXML
    private Button InsertCourse;

    // ObservableList to hold data for the secondary TableView
    private final ObservableList<DTO2> secondaryDataList;

    // Default constructor
    public SecondpaneController() {
        // Create an empty observable list
        this.secondaryDataList = FXCollections.observableArrayList();
    }

    // Constructor to receive the mainDataList from MainViewController
    public SecondpaneController(ObservableList<DTO2> mainDataList) {
        // Create a new observable list and link it to the mainDataList
        this.secondaryDataList = FXCollections.observableArrayList(mainDataList);
    }

@Override
public void initialize(URL url, ResourceBundle rb) {
    // Initialize table columns
    ColumnID1.setCellValueFactory(new PropertyValueFactory<>("courseID"));
    ColumnID2.setCellValueFactory(new PropertyValueFactory<>("courseName"));
    ColumnID3.setCellValueFactory(new PropertyValueFactory<>("courseDepartment"));
    ColumnID4.setCellValueFactory(new PropertyValueFactory<>("courseStudents"));
    ColumnID5.setCellValueFactory(new PropertyValueFactory<>("courseCreditHours"));

    try {
        // Fetch data from the database using your DB class method
        secondaryDataList.addAll(DB.getCoursesInformation());
    } catch (SQLException | ClassNotFoundException e) {
        handleException("Error fetching data", e);
    }

    // Set table data
    secondTableView.setItems(secondaryDataList);

    // Add listener to handle table selection
    secondTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            // Populate text fields with selected data
            CourseIDFeild.setText(String.valueOf(newSelection.getCourseID()));
            CourseNameField.setText(newSelection.getCourseName());
            CourseCreditHoursField.setText(String.valueOf(newSelection.getCourseCreditHours()));
            // Add similar lines for other fields
        }
    });
}


    @FXML
    private void CourseDelete(ActionEvent event) {
        try {
            int courseID = Integer.parseInt(CourseIDFeild.getText());

            boolean deleteSuccess;
            try {
                deleteSuccess = DB.CourseDelete(courseID);
            } catch (ClassNotFoundException | SQLException ex) {
                handleException("Deletion Failed", ex);
                return;
            }

            if (deleteSuccess) {
                showAlert("Deletion Successful", "Student course details were deleted from the database.");
                // Reload data after delete
                ObservableList<DTO2> updatedGradesList = fetchGradesData();
                secondTableView.setItems(updatedGradesList);
            }

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid numeric value for the course ID.");
        }
    }

    private ObservableList<DTO2> fetchGradesData() {
        try {
            // Fetch data from the database using your DB class method
            return FXCollections.observableArrayList(DB.getCoursesInformation());
        } catch (SQLException | ClassNotFoundException e) {
            handleException("Error fetching data", e);
            return FXCollections.observableArrayList();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void handleException(String message, Exception exception) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message + ": " + exception.getMessage());
        alert.showAndWait();
    }
}
