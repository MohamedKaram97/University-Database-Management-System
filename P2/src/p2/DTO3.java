// DTO2 class
package p2;

public class DTO3 {

    private int departmentID;
    private String departmentName;
    private int departmentCourses;
    private int departmentStudents;
    private int CourseID ; 
    private String CourseName;
    private int CourseHours ; 

    public DTO3(int departmentID, String departmentName, int departmentCourses, int departmentStudents, int CourseID, String CourseName, int CourseHours) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.departmentCourses = departmentCourses;
        this.departmentStudents = departmentStudents;
        this.CourseID = CourseID;
        this.CourseName = CourseName;
        this.CourseHours = CourseHours;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getDepartmentCourses() {
        return departmentCourses;
    }

    public void setDepartmentCourses(int departmentCourses) {
        this.departmentCourses = departmentCourses;
    }

    public int getDepartmentStudents() {
        return departmentStudents;
    }

    public void setDepartmentStudents(int departmentStudents) {
        this.departmentStudents = departmentStudents;
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int CourseID) {
        this.CourseID = CourseID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String CourseName) {
        this.CourseName = CourseName;
    }

    public int getCourseHours() {
        return CourseHours;
    }

    public void setCourseHours(int CourseHours) {
        this.CourseHours = CourseHours;
    }

    
   
}