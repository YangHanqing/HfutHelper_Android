package cn.hachin.hfuthelper.Entity;

/**
 * Created by yanghanqing on 15/11/7.
 */
public class Course {
    public Course(int dayOfWeek, int timeOfDay, String name, String classNum, String weeks, String location, String teacher) {
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
        this.name = name;
        this.classNum = classNum;
        this.weeks = weeks;
        this.location = location;
        this.teacher = teacher;
    }

    private int dayOfWeek;

    public Course() {
        this.dayOfWeek =0;
        this.timeOfDay = 0;
        this.name = null;
        this.classNum = null;
        this.weeks = null;
        this.location = null;
        this.teacher = null;
    }

    public int getTimeOfDay() {
        return timeOfDay;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public String getName() {
        return name;
    }

    public String getClassNum() {
        return classNum;
    }

    public String getWeeks() {
        return weeks;
    }

    public String getLocation() {
        return location;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTimeOfDay(int timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    private int timeOfDay;
    private String name;
    private String classNum;
    private String weeks;
    private String location;
    private String teacher;

    @Override
    public String toString() {
        return "Course{" +
                "dayOfWeek=" + dayOfWeek +
                ", timeOfDay=" + timeOfDay +
                ", name='" + name + '\'' +
                ", classNum='" + classNum + '\'' +
                ", weeks='" + weeks + '\'' +
                ", location='" + location + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
