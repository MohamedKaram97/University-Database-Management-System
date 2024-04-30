// DTO2 class
package p2;

public class DTO2 {

private int courseID;
private String courseName;
private String courseDepartment;
private int courseStudents;
private int courseCreditHours;

    public DTO2(int courseID, String courseName, String courseDepartment, int courseStudents, int courseCreditHours) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseDepartment = courseDepartment;
        this.courseStudents = courseStudents;
        this.courseCreditHours = courseCreditHours;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDepartment() {
        return courseDepartment;
    }

    public void setCourseDepartment(String courseDepartment) {
        this.courseDepartment = courseDepartment;
    }

    public int getCourseStudents() {
        return courseStudents;
    }

    public void setCourseStudents(int courseStudents) {
        this.courseStudents = courseStudents;
    }

    public int getCourseCreditHours() {
        return courseCreditHours;
    }

    public void setCourseCreditHours(int courseCreditHours) {
        this.courseCreditHours = courseCreditHours;
    }

    
}
