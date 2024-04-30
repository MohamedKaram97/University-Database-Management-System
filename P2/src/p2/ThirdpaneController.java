package p2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ThirdpaneController implements Initializable {

    @FXML
    private TableView<DTO3> ThirdTableView;

    @FXML
    private TableColumn<DTO3, Integer> ColumnIDa;

    @FXML
    private TableColumn<DTO3, String> ColumnIDb;

    @FXML
    private TableColumn<DTO3, Integer> ColumnIDc;

    @FXML
    private TableColumn<DTO3, Integer> ColumnIDd;

    @FXML
    private TableView<DTO3> Third2TableView;

    @FXML
    private TextField DepartmentName;
    @FXML
    private TextField DepartmentID;
    
    @FXML
    private Button DeleteDepBtn;
    @FXML
    private Button UpdateDepBtn;
    @FXML
    private Button InsertDepBtn;
    
    @FXML
    private TableColumn<DTO3, Integer> Info1;
    @FXML
    private TableColumn<DTO3, String> Info2;
    @FXML
    private TableColumn<DTO3, Integer> Info3;

    private final ObservableList<DTO3> ThirdDataList;

    public ThirdpaneController() {
        this.ThirdDataList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the first TableView (ThirdTableView)
        ColumnIDa.setCellValueFactory(new PropertyValueFactory<>("departmentID"));
        ColumnIDb.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        ColumnIDc.setCellValueFactory(new PropertyValueFactory<>("departmentCourses"));
        ColumnIDd.setCellValueFactory(new PropertyValueFactory<>("departmentStudents"));

        try {
            ThirdDataList.addAll(DB.getDepartmentInfo());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ThirdTableView.setItems(ThirdDataList);

        // Initialize the second TableView (Third2TableView)
        Info1.setCellValueFactory(new PropertyValueFactory<>("CourseID")); // Adjust this based on the actual property you want to display
        Info2.setCellValueFactory(new PropertyValueFactory<>("CourseName"));
        Info3.setCellValueFactory(new PropertyValueFactory<>("CourseHours"));
        ObservableList<DTO3> dto3Data2 = FXCollections.observableArrayList();

        try {
            dto3Data2.addAll(DB.getDepartmentInfo());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Third2TableView.setItems(dto3Data2);
        ThirdTableView.setOnMouseClicked(event -> handleTableClick());
    }
        private void handleTableClick() {
        DTO3 selectedDepartment = ThirdTableView.getSelectionModel().getSelectedItem();

        if (selectedDepartment != null) {
            DepartmentID.setText(String.valueOf(selectedDepartment.getDepartmentID()));
            DepartmentName.setText(selectedDepartment.getDepartmentName());

        }
}
}