package cn.hachin.hfuthelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.hachin.hfuthelper.Dao.CourseDao;
import cn.hachin.hfuthelper.Dao.ScoreDao;
import cn.hachin.hfuthelper.Db.HFUTSQLiteOpenHelper;
import cn.hachin.hfuthelper.Entity.Course;
import cn.hachin.hfuthelper.Entity.ScoreTable;
import cn.hachin.hfuthelper.Utils.Dao;
import cn.hachin.hfuthelper.Utils.NetUtils;
import cn.hachin.hfuthelper.Utils.Tools;

public class LoginActivity extends Activity {

    private static EditText etUsername;
    private static EditText etPassword;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        Map<String, String> userInfo = Dao.getUserInfo(this);


        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        if (userInfo != null) {
            etUsername.setText(userInfo.get("username"));
            etPassword.setText(userInfo.get("password"));
        }
    }

    public void LoginFromXXMH(View v) {
        pd = ProgressDialog.show(LoginActivity.this, "", "初始化数据请稍后");
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);


        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        //访问课程表
                        String partURL = "/index.portal?.pn=p383_p771830";
                        //final String text = NetUtils.loginof_TYSFZZXT(username, password, partURL);
                        //final String text = NetUtils.loginof_XXMH(username, password, code, partURL);

                        final String text = NetUtils.loginof_TYSFZZXT(username, password, partURL);


                        if (text != null) {
                            //保存用户名密码

                            List<Course> courses = null;//得到课程表
                            try {
                                courses = Tools.getCoursesFromXxmhText(text);
                            } catch (Exception e) {//解析课表时出错
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        Toast.makeText(LoginActivity.this, "帐号密码错误", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }
                            Dao.savaUserInfo(getApplicationContext(), username, password);
                            if(courses==null){//如果学校数据出错
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "教务处数据异常", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                }
                            });

                            //第一次连接数据库,onCreate被调用
                            HFUTSQLiteOpenHelper HFUTSQLiteOpenHelper = new HFUTSQLiteOpenHelper(LoginActivity.this);
                            try {
                            //先清空数据库
                                HFUTSQLiteOpenHelper.deleteDatabase(LoginActivity.this);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            HFUTSQLiteOpenHelper.getReadableDatabase();

                            CourseDao courseDao = new CourseDao(LoginActivity.this);
                            //遍历List

                            Iterator<Course> courseIterator = courses.iterator();
                            while (courseIterator.hasNext()) {
                                Course course = courseIterator.next();
                                courseDao.insert(course);//插入数据
                            }
                            courseDao.insertWeeks();//插入周数与课程的关系
                            //获取成绩

                            ScoreDao scoreDao=new ScoreDao(LoginActivity.this);
                            String scoreText = NetUtils.getScore();
                            List<ScoreTable> scoreTables = Tools.getScoresFromBkjwText(scoreText);//获取成绩
                            for (ScoreTable scoreTable : scoreTables) {
                                scoreDao.insertScore(scoreTable);//插入成绩数据
                            }


                            runOnUiThread(new Runnable() {//测试
                                @Override
                                public void run() {
                                 //   Toast.makeText(LoginActivity.this, "数据库写入成功", Toast.LENGTH_SHORT).show();
                                }
                            });


                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);

                            LoginActivity.this.finish();

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();
                                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                }
        ).start();

    }

}
