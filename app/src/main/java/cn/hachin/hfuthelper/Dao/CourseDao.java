package cn.hachin.hfuthelper.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.hachin.hfuthelper.Db.HFUTSQLiteOpenHelper;
import cn.hachin.hfuthelper.Entity.Course;
import cn.hachin.hfuthelper.Entity.ScoreTable;
import cn.hachin.hfuthelper.Utils.Tools;


/**
 * Created by yanghanqing on 15/11/8.
 */
public class CourseDao {

    private HFUTSQLiteOpenHelper HFUTSQLiteOpenHelper;

    public CourseDao(Context context) {
        HFUTSQLiteOpenHelper = new HFUTSQLiteOpenHelper(context);
    }

    public void insert(Course course) {
        SQLiteDatabase database = HFUTSQLiteOpenHelper.getWritableDatabase();
        StringBuffer weeks = Tools.getWeekFromString(course.getWeeks());//获取周数
        if (database.isOpen()) {
            database.execSQL(//存入数据库
                    "insert into Courses(dayOfWeek,timeOfDay,name,classNum,location,teacher,weeks) values(?,?,?,?,?,?,?);",
                    new Object[]{
                            course.getDayOfWeek(), course.getTimeOfDay(), course.getName(), course.getClassNum(), course.getLocation(), course.getTeacher(), weeks

                    });
            database.close();
        }
    }

    public void insertScore(ScoreTable scoreTable) {

        SQLiteDatabase database = HFUTSQLiteOpenHelper.getWritableDatabase();
        if (database.isOpen()) {
            database.execSQL(//存入数据库
                    "insert into Scores(semester,courseCode,courseName,classNum,score,secondScore,credit) values(?,?,?,?,?,?,?);",
                    new Object[]{
                            scoreTable.getSecondScore(), scoreTable.getCourseCode(),
                            scoreTable.getCourseName(), scoreTable.getClassNum(),
                            scoreTable.getScore(), scoreTable.getSecondScore(), scoreTable.getCredit()
                    });
            database.close();
        }
    }

    public void insertWeeks() {
        SQLiteDatabase database = HFUTSQLiteOpenHelper.getWritableDatabase();
        if (database.isOpen()) {

            Cursor cursor = database.rawQuery("select _id,weeks from Courses;", null);

            if (database.isOpen()) {
                if (cursor != null && cursor.getCount() > 0) {//判断查询结果是否为空
                    while (cursor.moveToNext()) {
                        int _id = cursor.getInt(0);
                        String weeks = cursor.getString(1);
                        for (int i = 0; i <= 18; i++) {
                            if (weeks.charAt(i) == '1') {//如果第n周有课
                                database.execSQL(//存入数据库
                                        "insert into Weeks(week,_id) values(?,?);",
                                        new Object[]{
                                                i, _id
                                        });
                            }
                        }
                    }

                }
            }
            database.close();

        }


    }

    public List<Course> qurryAll() {
        List<Course> courses = new ArrayList<Course>();

        SQLiteDatabase database = HFUTSQLiteOpenHelper.getReadableDatabase();
        if (database.isOpen()) {

            Cursor cursor = database.rawQuery("select name,dayOfWeek,timeOfDay,location from Courses;", null);
            if (cursor != null && cursor.getCount() > 0) {//判断查询结果是否为空
                while (cursor.moveToNext()) {
                    Course course = new Course();
                    String name = cursor.getString(0);
                    int dayOfWeek = cursor.getInt(1);
                    int timeOfDay = cursor.getInt(2);
                    String location = cursor.getString(3);

                    course.setName(name);
                    course.setDayOfWeek(dayOfWeek);
                    course.setTimeOfDay(timeOfDay);
                    course.setLocation(location);
                    courses.add(course);
                }

            }
            database.close();
        }

        return courses;
    }

    public List<Course> qurryFromWeek(int week) {
        List<Course> courses = new ArrayList<Course>();

        SQLiteDatabase database = HFUTSQLiteOpenHelper.getReadableDatabase();
        if (database.isOpen()) {

            Cursor cursor = database.rawQuery("select name,dayOfWeek,timeOfDay,location from Courses NATURAL JOIN Weeks where week="+week+";", null);
            if (cursor != null && cursor.getCount() > 0) {//判断查询结果是否为空
                while (cursor.moveToNext()) {
                    Course course = new Course();
                    String name = cursor.getString(0);
                    int dayOfWeek = cursor.getInt(1);
                    int timeOfDay = cursor.getInt(2);
                    String location = cursor.getString(3);

                    course.setName(name);
                    course.setDayOfWeek(dayOfWeek);
                    course.setTimeOfDay(timeOfDay);
                    course.setLocation(location);
                    courses.add(course);
                }

            }
            database.close();
        }
        return courses;

    }

}