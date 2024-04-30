package p2;

import java.util.Date;

public class Student {
  
    
    private int studentID;
    private String studentName;
    private String Gender;
    private String studentNumber;
    private Date StudentDOB ;
    private String StudentEmail;
    private int DepId;
    private String departmentName;
    private int noOfEnrolledCourses;
    private Float CGPA;

    public Student(int studentID, String studentName, String Gender, String studentNumber, Date StudentDOB, String StudentEmail, int DepId, String departmentName, int noOfEnrolledCourses, Float CGPA) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.Gender = Gender;
        this.studentNumber = studentNumber;
        this.StudentDOB = StudentDOB;
        this.StudentEmail = StudentEmail;
        this.DepId = DepId;
        this.departmentName = departmentName;
        this.noOfEnrolledCourses = noOfEnrolledCourses;
        this.CGPA = CGPA;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Date getStudentDOB() {
        return StudentDOB;
    }

    public void setStudentDOB(Date StudentDOB) {
        this.StudentDOB = StudentDOB;
    }

    public String getStudentEmail() {
        return StudentEmail;
    }

    public void setStudentEmail(String StudentEmail) {
        this.StudentEmail = StudentEmail;
    }

    public int getDepId() {
        return DepId;
    }

    public void setDepId(int DepId) {
        this.DepId = DepId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getNoOfEnrolledCourses() {
        return noOfEnrolledCourses;
    }

    public void setNoOfEnrolledCourses(int noOfEnrolledCourses) {
        this.noOfEnrolledCourses = noOfEnrolledCourses;
    }

    public Float getCGPA() {
        return CGPA;
    }

    public void setCGPA(Float CGPA) {
        this.CGPA = CGPA;
    }
    

  
   

}
