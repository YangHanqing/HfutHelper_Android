package cn.hachin.hfuthelper.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by yanghanqing on 15/11/8.
 */
public class HFUTSQLiteOpenHelper extends SQLiteOpenHelper {
    public HFUTSQLiteOpenHelper(Context context) {
        super(context, "hfut.db", null, 1);



    }

    public  boolean deleteDatabase(Context context) {
        return context.deleteDatabase( "hfut.db");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql="create table Courses(\n" +
                "\t_id integer primary key,\n" +
                "\tdayOfWeek integer,\n" +
                "\ttimeOfDay integer,\n" +
                "\tname varchar(40),\n" +
                "\tclassNum varchar(10),\n" +
                "\tlocation varchar(20),\n" +
                "\tteacher varchar(30),\n" +
                "\tweeks varchar(20)\n" +
                ");";
        db.execSQL(sql); //创建course表
        sql="create table Weeks(\n" +
                "\tweek integer,\n" +
                "\t_id integer\n" +
                ");";
        db.execSQL(sql); //创建星期表
        sql="create table Scores(\n" +
                "\t_id integer primary key,\n" +
                "\tsemester varchar(40),\n" +
                "\tcourseCode varchar(10),\n" +
                "\tcourseName varchar(40),\n" +
                "\tclassNum varchar(10),\n" +
                "\tscore varchar(10),\n" +
                "\tsecondScore varchar(10),\n" +
                "\tcredit integer\n" +
                ");";
        db.execSQL(sql); //创建成绩表

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
