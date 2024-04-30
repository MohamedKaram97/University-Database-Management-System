package p2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PC
 */
public class GradesDTO {
    private int StudentID;
    private String StudentName;
    private int CourseID;
    private String CourseName;
    private int Score;
    private String Grade;
    private int Year;
    private String Semester;

    public GradesDTO(int StudentID, String StudentName, int CourseID, String CourseName, int Score, String Grade, int Year, String Semester) {
        this.StudentID = StudentID;
        this.StudentName = StudentName;
        this.CourseID = CourseID;
        this.CourseName = CourseName;
        this.Score = Score;
        this.Grade = Grade;
        this.Year = Year;
        this.Semester = Semester;
    }

    public int getStudentID() {
        return StudentID;
    }

    public void setStudentID(int StudentID) {
        this.StudentID = StudentID;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String StudentName) {
        this.StudentName = StudentName;
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

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String Grade) {
        this.Grade = Grade;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int Year) {
        this.Year = Year;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String Semester) {
        this.Semester = Semester;
    }
}
