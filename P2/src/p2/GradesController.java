package p2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GradesController implements Initializable {

    @FXML
    private TableView<GradesDTO> tableID;
    @FXML
    private TableColumn<GradesDTO, Integer> studentIDColumn;
    @FXML
    private TableColumn<GradesDTO, String> studentNameColumn;
    @FXML
    private TableColumn<GradesDTO, Integer> courseIDColumn;
    @FXML
    private TableColumn<GradesDTO, String> courseNameColumn;
    @FXML
    private TableColumn<GradesDTO, Integer> scoreColumn;
    @FXML
    private TableColumn<GradesDTO, String> gradeColumn;
    @FXML
    private TableColumn<GradesDTO, Integer> yearColumn;
    @FXML
    private TableColumn<GradesDTO, String> semesterColumn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button DeleteBtn;
    @FXML
    private TextField studentIDField;
    @FXML
    private TextField courseIDField;
    @FXML
    private TextField semesterIDField;
    @FXML
    private TextField yearIDField;
    @FXML
    private TextField scoreField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTableColumns();

        ObservableList<GradesDTO> gradesList = fetchGradesData();

        tableID.setItems(gradesList);

        // Add a listener to handle clicks on TableView items
        tableID.setOnMouseClicked(event -> handleTableClick());

        updateBtn.setOnAction(this::handleUpdateGradesButton);
    }

    private void initializeTableColumns() {
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        courseIDColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
    }

    private ObservableList<GradesDTO> fetchGradesData() {
        ObservableList<GradesDTO> gradesList = FXCollections.observableArrayList();
        try {
            gradesList.addAll(DB.getStudentsGrades());
        } catch (SQLException e) {
            handleException("Error while fetching grades information", e);
        }
        return gradesList;
    }

    private void handleTableClick() {
        GradesDTO selectedGrade = tableID.getSelectionModel().getSelectedItem();

        if (selectedGrade != null) {
            studentIDField.setText(String.valueOf(selectedGrade.getStudentID()));
            courseIDField.setText(String.valueOf(selectedGrade.getCourseID()));
            semesterIDField.setText(selectedGrade.getSemester());
            yearIDField.setText(String.valueOf(selectedGrade.getYear()));
            scoreField.setText(String.valueOf(selectedGrade.getScore()));
        }
    }

    @FXML
    private void handleUpdateGradesButton(ActionEvent event) {
        try {
            int studentID = Integer.parseInt(studentIDField.getText());
            String semester = semesterIDField.getText();
            int courseID = Integer.parseInt(courseIDField.getText());
            int score = Integer.parseInt(scoreField.getText());
            int year = Integer.parseInt(yearIDField.getText());

            boolean updateSuccess;
            try {
                updateSuccess = DB.updateStudentGrade(studentID, semester, courseID, score, year);
            } catch (ClassNotFoundException | SQLException ex) {
                handleException("Update Failed", ex);
                return;
            }

            if (updateSuccess) {
                showAlert("Update Successful", "Student grade details were updated in the database.");
                // Reload data after update
                ObservableList<GradesDTO> updatedGradesList = fetchGradesData();
                tableID.setItems(updatedGradesList);
            }

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numeric values for grades data.");
        }
    }

    private void handleException(String message, Exception exception) {
        System.err.println(message + ": " + exception.getMessage());
        exception.printStackTrace();
    }

    private void showAlert(String title, String content) {
        // Implement this method for showing alerts
    }
    
    
    
    @FXML
private void handleDeleteButton(ActionEvent event) {
    try {
        int studentID = Integer.parseInt(studentIDField.getText());
        String semester = semesterIDField.getText();
        int courseID = Integer.parseInt(courseIDField.getText());
        int year = Integer.parseInt(yearIDField.getText());

        boolean deleteSuccess;
        try {
            deleteSuccess = DB.deleteStudentGrade(studentID, semester, courseID, year);
        } catch (ClassNotFoundException | SQLException ex) {
            handleException("Deletion Failed", ex);
            return;
        }

        if (deleteSuccess) {
            showAlert("Deletion Successful", "Student grade details were deleted from the database.");
            // Reload data after delete
            ObservableList<GradesDTO> updatedGradesList = fetchGradesData();
            tableID.setItems(updatedGradesList);
        }

    } catch (NumberFormatException e) {
        showAlert("Input Error", "Please enter valid numeric values for grades data.");
    }
}
}
