package p2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FXMLController implements Initializable {

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, Integer> studentIdColumn;

    @FXML
    private TableColumn<Student, String> studentNameColumn;

    @FXML
    private TableColumn<Student, String> courseName;

    @FXML
    private TableColumn<Student, Integer> departmentNameColumn;

    @FXML
    private TableColumn<Student, Float> GPAIdColumn;

    @FXML
    private TableColumn<Student, Float> CGPAIdColumn;

    @FXML
    private TableColumn<Student, Date> DOBColumn;

    @FXML
    private TableColumn<Student, String> GenderColumn;

    @FXML
    private TableColumn<Student, String> EmailColumn;

    @FXML
    private TableColumn<Student, Integer> DEPIDColumn;

    @FXML
    private TextField SIDFeild;

    @FXML
    private TextField SNameFeild;

    @FXML
    private TextField DepIDFeild;

    @FXML
    private TextField PhoneFeild;

    @FXML
    private TextField SEmailFeild;

    @FXML
    private TextField SGenderFeild;

    @FXML
    private DatePicker DateOfBirth;

    @FXML
    private Button insertButton;

    @FXML
    private Button updateButton;

    // ObservableList to hold data for TableView
    private final ObservableList<Student> studentData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize table columns
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        GenderColumn.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        DOBColumn.setCellValueFactory(new PropertyValueFactory<>("StudentDOB"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("StudentEmail"));
        DEPIDColumn.setCellValueFactory(new PropertyValueFactory<>("DepId"));
        departmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        GPAIdColumn.setCellValueFactory(new PropertyValueFactory<>("noOfEnrolledCourses"));
        CGPAIdColumn.setCellValueFactory(new PropertyValueFactory<>("CGPA"));

        // Set table data
        studentTable.setItems(studentData);


        // Button action event
        insertButton.setOnAction(event -> {
            try {
                int studentId = Integer.parseInt(SIDFeild.getText());
                String studentName = SNameFeild.getText();
                String studentNumber = PhoneFeild.getText();
                String departmentName = DepIDFeild.getText();

                // Insert student into the database
                boolean success = false;
                try {
                    success = DB.insertStudent(studentId, studentName, studentNumber, studentNumber);
                } catch (ClassNotFoundException | SQLException ex) {
                    handleException("Insertion Failed", "An error occurred while inserting the student.", ex);
                }

                if (success) {
                    showAlert("Insertion Successful", "Student added to the database.");
                    try {
                        // Reload data after insertion
                        loadData();
                    } catch (ClassNotFoundException | SQLException ex) {
                        handleException("Data Reload Failed", "An error occurred while reloading data.", ex);
                    }
                }

            } catch (NumberFormatException e) {
                showAlert("Input Error", "Please enter valid numeric values for Student ID and Department ID.");
            }
        });

        // Button action event
        updateButton.setOnAction(event -> {
            try {
                int studentId = Integer.parseInt(SIDFeild.getText());
                String studentName = SNameFeild.getText();
                int departmentId = Integer.parseInt(DepIDFeild.getText());

                // Update student in the database
                boolean update1 = false;
                try {
                    update1 = DB.updateStudent(studentId, studentName, departmentId);
                } catch (ClassNotFoundException | SQLException ex) {
                    handleException("Update Failed", "An error occurred while updating the student.", ex);
                }

                if (update1) {
                    showAlert("Update Successful", "Student details were updated in the database.");
                    try {
                        // Reload data after update
                        loadData();
                    } catch (ClassNotFoundException | SQLException ex) {
                        handleException("Data Reload Failed", "An error occurred while reloading data.", ex);
                    }
                }

            } catch (NumberFormatException e) {
                showAlert("Input Error", "Please enter valid numeric values for Student ID and Department ID.");
            }
        });

        // Add a listener to handle clicks on TableView items
        studentTable.setOnMouseClicked(event -> handleTableClick());

        try {
            // Load initial data
            loadData();
        } catch (ClassNotFoundException | SQLException ex) {
            handleException("Initialization Failed", "Failed to load initial data.", ex);
        }
    }

    // Load data from the database into the TableView
    private void loadData() throws ClassNotFoundException, SQLException {
        studentData.clear();
        studentData.addAll(DB.getAllStudents());
    }

    // Show an information alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Handle exceptions by logging and showing an alert
    private void handleException(String title, String message, Exception exception) {
        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, exception);
        showAlert(title, message + "\nError Details: " + exception.getMessage());
    }

    private void handleTableClick() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            SIDFeild.setText(String.valueOf(selectedStudent.getStudentID()));
            SNameFeild.setText(selectedStudent.getStudentName());
            PhoneFeild.setText(selectedStudent.getStudentNumber());
            DepIDFeild.setText(selectedStudent.getDepartmentName());

            // Assuming these methods exist in your Student class
            SEmailFeild.setText(selectedStudent.getStudentEmail());
            SGenderFeild.setText(selectedStudent.getGender());
            // Set DatePicker value and format it as "YYYY MM DD"
            Date dobDate = selectedStudent.getStudentDOB();

            if (dobDate != null) {
                // Convert java.sql.Date to java.util.Date
                java.util.Date utilDate = new java.util.Date(dobDate.getTime());

                // Convert java.util.Date to LocalDate
                LocalDate dob = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                // Set DatePicker value
                DateOfBirth.setValue(dob);

                // Format the date as "YYYY MM DD"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
                DateOfBirth.getEditor().setText(dob.format(formatter));
            }
        }
    }
}
