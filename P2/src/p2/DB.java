package p2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.swing.JOptionPane;
// import oracle.jdbc.OracleDriver;

public class DB {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String DB_USER = "uni";
    private static final String DB_PASSWORD = "uni";
 
    public static Connection connect() throws ClassNotFoundException, SQLException {
        Connection con;

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to connect to the database: " + e.getMessage());
            throw e; // Rethrow the exception to notify the caller about the failure
        }
    }

    public static List<DTO3> getDepartmentInfo() throws SQLException {
        List<DTO3> departmentList = new ArrayList<>();

        try (Connection con = connect()) {
            if (con != null) {
                String getDepartmentInfoQuery = "SELECT \n" +
"    D.DepartmentID,\n" +
"    D.DepartmentName,\n" +
"    COUNT(DISTINCT DC.CourseID) AS NumberOfCourses,\n" +
"    COUNT(DISTINCT SEC.StudentID) AS NumberOfEnrolledStudents,\n" +
"    DC.CourseID AS CourseID,\n" +
"    C.CourseName AS CourseName,\n" +
"    C.CourseCreditHours AS CourseCreditHours\n" +
"FROM\n" +
"    Departments D\n" +
"LEFT JOIN \n" +
"    DepartmentCourses DC ON D.DepartmentID = DC.DepartmentID\n" +
"LEFT JOIN \n" +
"    StudentEnrollCourses SEC ON DC.CourseID = SEC.CourseID\n" +
"LEFT JOIN \n" +
"    Courses C ON DC.CourseID = C.CourseID\n" +
"GROUP BY \n" +
"    D.DepartmentID, D.DepartmentName, DC.CourseID, C.CourseName, C.CourseCreditHours";

                try (PreparedStatement ps = con.prepareStatement(getDepartmentInfoQuery);
                     ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        int departmentID = rs.getInt("DepartmentID");
                        String departmentName = rs.getString("DepartmentName");
                        int departmentCourses = rs.getInt("NumberOfCourses");
                        int departmentStudents = rs.getInt("NumberOfEnrolledStudents");
                        int CourseID = rs.getInt("CourseID");
                        String CourseName = rs.getString("CourseName");
                        int CourseHours = rs.getInt("CourseCreditHours");
                        
                        DTO3 dto = new DTO3(departmentID, departmentName, departmentCourses, departmentStudents,CourseID,CourseName,CourseHours);
                        departmentList.add(dto);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            handleException("Error while fetching department information", e);
        }

        return departmentList;
    }
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 public static List<GradesDTO> getStudentsGrades() throws SQLException {
    List<GradesDTO> gradesList = new ArrayList<>();

    try (Connection con = connect()) {
        if (con != null) {
            String getGradesInfoQuery = "SELECT\n" +
"    SE.StudentID,\n" +
"    S.Name AS StudentName,\n" +
"    SE.CourseID,\n" +
"    C.CourseName,\n" +
"    SE.Grade AS Score,\n" +
"    CASE\n" +
"        WHEN SE.Grade >= 90 THEN 'A'\n" +
"        WHEN SE.Grade >= 80 THEN 'B'\n" +
"        WHEN SE.Grade >= 70 THEN 'C'\n" +
"        WHEN SE.Grade >= 60 THEN 'D'\n" +
"        ELSE 'F'\n" +
"    END AS Grade,\n" +
"    SE.Year,\n" +
"    SE.Semester\n" +
"FROM\n" +
"    StudentEnrollCourses SE\n" +
"JOIN\n" +
"    Students S ON SE.StudentID = S.StudentID\n" +
"JOIN\n" +
"    Courses C ON SE.CourseID = C.CourseID\n" +
"LEFT JOIN\n" +
"    DepartmentCourses DC ON SE.CourseID = DC.CourseID AND S.DepartmentID = DC.DepartmentID";

            try (PreparedStatement ps = con.prepareStatement(getGradesInfoQuery);
                 ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        int studentID = rs.getInt("StudentID");
                        String StudentName = rs.getString("StudentName");
                        int courseID = rs.getInt("CourseID");
                        String courseName = rs.getString("CourseName");
                        int score = rs.getInt("Score");
                        String grade = rs.getString("Grade");
                        int year = rs.getInt("Year");
                        String semester = rs.getString("Semester");

                    GradesDTO gDTO = new GradesDTO(studentID, StudentName, courseID, courseName, score, grade, year, semester);
                    gradesList.add(gDTO);
                }
            }
        }
    } catch (SQLException | ClassNotFoundException e) {
        handleException("Error while fetching grades information", e);
        e.printStackTrace(); // Add this line to print the stack trace
    }

    return gradesList;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
 // 2ndPane Controller DAO 
 
    public static List<DTO2> getCoursesInformation() throws ClassNotFoundException, SQLException {
        List<DTO2> dto2 = new ArrayList<>();

        try (Connection con = connect()) {
            if (con == null) {
                return dto2;
            }

            String selectIdAndNameQuery = "SELECT\n" +
"    DC.CourseID AS DepartmentCourseID,\n" +
"    C.CourseName AS CourseName,\n" +
"    D.DepartmentName AS DepartmentName,\n" +
"    COUNT(SEC.StudentID) AS NumberOfEnrolledStudents,\n" +
"    C.COURSECREDITHOURS AS CourseCreditHours\n" +
"FROM\n" +
"    DepartmentCourses DC\n" +
"JOIN\n" +
"    Courses C ON DC.CourseID = C.CourseID\n" +
"JOIN\n" +
"    Departments D ON DC.DepartmentID = D.DepartmentID\n" +
"LEFT JOIN\n" +
"    StudentEnrollCourses SEC ON DC.CourseID = SEC.CourseID\n" +
"GROUP BY\n" +
"    DC.CourseID, C.CourseName, C.COURSECREDITHOURS, D.DepartmentName";

            try (PreparedStatement ps = con.prepareStatement(selectIdAndNameQuery);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int courseID = rs.getInt("DepartmentCourseID");
                    String courseName = rs.getString("CourseName");
                    String courseDepartment = rs.getString("DepartmentName");
                    int courseStudents = rs.getInt("NumberOfEnrolledStudents");
                    int courseCreditHours = rs.getInt("CourseCreditHours");
                    // Create a DTO2 object with only the required information
                    DTO2 dto = new DTO2(courseID, courseName, courseDepartment, courseStudents,courseCreditHours);
                    dto2.add(dto);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            handleException("Error while fetching student IDs and names", e);
        }

        return dto2;
    }
    
    
    
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public static List<Student> getAllStudents() throws ClassNotFoundException, SQLException {
        List<Student> students = new ArrayList<>();

        try (Connection con = connect()) {
            if (con == null) {
                return students;
            }

            String selectAllStudentsQuery = "SELECT\n" +
    "    S.StudentID,\n" +
    "    S.Name AS StudentName,\n" +
    "    S.Gender,\n" +
    "    S.PhoneNumber,\n" +
    "    S.DOB,\n" +
    "    S.Email,\n" +
    "    S.DepartmentID,\n" +
    "    D.DepartmentName,\n" +
    "    NVL(EC.NumberOfEnrolledCourses, 0) AS NumberOfEnrolledCourses,\n" +
    "    NVL(EC.OverallCGPA, 0) AS OverallCGPA\n" +
    "FROM\n" +
    "    Students S\n" +
    "JOIN\n" +
    "    Departments D ON S.DepartmentID = D.DepartmentID\n" +
    "LEFT JOIN (\n" +
    "    SELECT\n" +
    "        SEC.StudentID,\n" +
    "        COUNT(SEC.CourseID) AS NumberOfEnrolledCourses,\n" +
    "        AVG(SEC.Grade) AS OverallCGPA\n" +
    "    FROM\n" +
    "        StudentEnrollCourses SEC\n" +
    "    GROUP BY\n" +
    "        SEC.StudentID\n" +
    ") EC ON S.StudentID = EC.StudentID";

            try (PreparedStatement ps = con.prepareStatement(selectAllStudentsQuery);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int studentID = rs.getInt("StudentID");
                    String studentName = rs.getString("StudentName");
                    String Gender = rs.getString("Gender");
                    String studentNumber = rs.getString("PhoneNumber");
                    Date StudentDOB = rs.getDate("DOB");
                    String StudentEmail = rs.getString("Email");
                    int DepId = rs.getInt("DepartmentID");
                    String departmentName = rs.getString("DepartmentName");
                    int noOfEnrolledCourses = rs.getInt("NumberOfEnrolledCourses");
                    float CGPA = rs.getFloat("OverallCGPA");

                    Student student = new Student(studentID, studentName , Gender, studentNumber , StudentDOB, StudentEmail, DepId ,  departmentName, noOfEnrolledCourses, CGPA);
                    students.add(student);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            handleException("Error while fetching all students", e);
        }

        return students;
    }



public static boolean insertStudent(int studentId, String studentName, String studentNumber, String departmentName)
        throws ClassNotFoundException, SQLException {
    try (Connection con = connect()) {
        if (con == null) {
            return false;
        }

        String insertStudentQuery = "INSERT INTO students (student_id, student_name, student_number, department_id)\n" +
                "VALUES (?, ?, ?, (SELECT department_id FROM departments WHERE department_name = ?))";

        try (PreparedStatement psStudent = con.prepareStatement(insertStudentQuery)) {
            psStudent.setInt(1, studentId);
            psStudent.setString(2, studentName);
            psStudent.setString(3, studentNumber);
            psStudent.setString(4, departmentName);

            psStudent.executeUpdate();
        }

        return true; // Successful insertion
    } catch (SQLException e) {
        handleException("Insertion failed", e);
        return false;
    }
}


    public static boolean updateStudent(int studentId, String studentName, int departmentId)
            throws ClassNotFoundException, SQLException {
        try (Connection con = connect()) {
            if (con == null) {
                return false;
            }

            String updateStudentQuery = "UPDATE StudentEnrollCourses\n" +
       "SET Grade = ?\n" +
       "WHERE StudentID = ?\n" +
       "  AND CourseID = ?\n" +
       " AND Year = ?\n" +
       " AND Semester = '?'";

            try (PreparedStatement psStudent = con.prepareStatement(updateStudentQuery)) {
                psStudent.setInt(1, departmentId);
                psStudent.setString(2, studentName);
                psStudent.setInt(3, studentId);

                psStudent.executeUpdate();
            }

            return true; // Successful update
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Update failed: " + e.getMessage());
            return false;
        }
    }

    private static void handleException(String message, Exception exception) {
        JOptionPane.showMessageDialog(null, message + ": " + exception.getMessage());
       
    }
    

    
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
public static boolean updateStudentGrade(int studentID, String semester, int courseID, int score, int year)
        throws ClassNotFoundException, SQLException {
    try (Connection con = connect()) {
        if (con == null) {
            return false;
        }

        String updateGradeQuery = "UPDATE StudentEnrollCourses\n" +
                "SET Grade = ?\n" +
                "WHERE StudentID = ?\n" +
                "  AND CourseID = ?\n" +
                "  AND Year = ?\n" +
                "  AND Semester = ?";

        try (PreparedStatement psGrade = con.prepareStatement(updateGradeQuery)) {
            psGrade.setInt(1, score);
            psGrade.setInt(2, studentID);
            psGrade.setInt(3, courseID);
            psGrade.setInt(4, year);
            psGrade.setString(5, semester);
            psGrade.executeUpdate();
        }

        return true; // Successful update
    } catch (SQLException e) {
        showAlert("Update failed", "An error occurred while updating grades: " + e.getMessage());
        return false;
    }
}

private static void showAlert(String title, String content) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}



public static boolean deleteStudentGrade(int studentID, String semester, int courseID, int year)
        throws ClassNotFoundException, SQLException {
    try (Connection con = connect()) {
        if (con == null) {
            return false;
        }

String deleteGradeQuery = "DELETE FROM StudentEnrollCourses\n" +
        "WHERE StudentID = ?\n" +
        "  AND CourseID = ?\n" +
        "  AND Year = ?\n" +
        "  AND Semester = ?";


        try (PreparedStatement psDelete = con.prepareStatement(deleteGradeQuery)) {
            psDelete.setInt(1, studentID);
            psDelete.setInt(2, courseID);
            psDelete.setInt(3, year);
            psDelete.setString(4, semester);
            psDelete.executeUpdate();
        }

        return true; // Successful delete
    } catch (SQLException e) {
        handleException("Deletion failed", e);
        return false;
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////

public static boolean CourseDelete(int courseID) throws ClassNotFoundException, SQLException {
    try (Connection con = connect()) {
        if (con == null) {
            return false;
        }

        String deleteGradeQuery = "DELETE FROM StudentEnrollCourses WHERE CourseID = ?";
        String deleteDepartmentCoursesQuery = "DELETE FROM DepartmentCourses WHERE CourseID = ?";
        String deleteCoursesQuery = "DELETE FROM Courses WHERE CourseID = ?";

        try (PreparedStatement psDeleteGrade = con.prepareStatement(deleteGradeQuery);
             PreparedStatement psDeleteDepartmentCourses = con.prepareStatement(deleteDepartmentCoursesQuery);
             PreparedStatement psDeleteCourses = con.prepareStatement(deleteCoursesQuery)) {

            con.setAutoCommit(false);  // Set auto-commit to false to handle the transaction

            psDeleteGrade.setInt(1, courseID);
            psDeleteGrade.executeUpdate();

            psDeleteDepartmentCourses.setInt(1, courseID);
            psDeleteDepartmentCourses.executeUpdate();

            psDeleteCourses.setInt(1, courseID);
            psDeleteCourses.executeUpdate();

            con.commit();  // Commit the transaction

        } catch (SQLException e) {
            con.rollback();  // Rollback the transaction in case of an exception
            handleException("Deletion failed", e);
            return false;
        } finally {
            con.setAutoCommit(true);  // Reset auto-commit to true
        }

        return true; // Successful delete
    } catch (SQLException e) {
        handleException("Deletion failed", e);
        return false;
    }
}


}
