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
public class ScoreDao {

    private HFUTSQLiteOpenHelper HFUTSQLiteOpenHelper;

    public ScoreDao(Context context) {
        HFUTSQLiteOpenHelper = new HFUTSQLiteOpenHelper(context);
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


    public List<ScoreTable> qurryAllScore() {
        List<ScoreTable> scoreTables = new ArrayList<ScoreTable>();

        SQLiteDatabase database = HFUTSQLiteOpenHelper.getReadableDatabase();
        if (database.isOpen()) {

            Cursor cursor = database.rawQuery("select semester,courseCode,courseName,classNum,score,secondScore,credit from Scores;", null);
            if (cursor != null && cursor.getCount() > 0) {//判断查询结果是否为空
                while (cursor.moveToNext()) {
                    ScoreTable scoreTable = new ScoreTable();

                    String semester= cursor.getString(0);
                    String courseCode= cursor.getString(1);
                    String courseName= cursor.getString(2);
                    String classNum= cursor.getString(3);
                    String score= cursor.getString(4);
                    String secondScore= cursor.getString(5);
                    double credit= cursor.getDouble(6);

                    scoreTable.setSemester(semester);
                    scoreTable.setCourseCode(courseCode);
                    scoreTable.setCourseName(courseName);
                    scoreTable.setClassNum(classNum);
                    scoreTable.setScore(score);
                    scoreTable.setSecondScore(secondScore);
                    scoreTable.setCredit(credit);

                    scoreTables.add(scoreTable);
                }

            }
            database.close();
        }

        return scoreTables;
    }
}