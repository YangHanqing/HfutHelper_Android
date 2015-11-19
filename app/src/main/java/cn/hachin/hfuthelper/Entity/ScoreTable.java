package cn.hachin.hfuthelper.Entity;

/**
 * Created by yanghanqing on 15/11/17.
 */
public class ScoreTable {

    String semester;
    String courseCode;
    String courseName;
    String classNum;
    String score;
    String secondScore;
    double credit;

    public ScoreTable() {
    }

    public ScoreTable(String semester, String courseCode, String courseName, String classNum, String score, String secondScore, double credit) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.classNum = classNum;
        this.score = score;

        this.secondScore = secondScore;
        this.credit = credit;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSecondScore() {
        return secondScore;
    }

    public void setSecondScore(String secondScore) {
        this.secondScore = secondScore;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "ScoreTable{" +
                "semester='" + semester + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", classNum='" + classNum + '\'' +
                ", score='" + score + '\'' +
                ", secondScore='" + secondScore + '\'' +
                ", credit=" + credit +
                '}'+"\n";
    }
}

