package cn.hachin.hfuthelper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Map;

import cn.hachin.hfuthelper.Utils.Dao;

public class SplashActivity extends AppCompatActivity {

    private Map<String, String> userInfo=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userInfo = Dao.getUserInfo(this);

        /*
            防止一闪而过
         */
        new Thread(){

            @Override
            public void run() {
                super.run();
                try {
                    sleep(1*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //提取用户名密码
                if(userInfo==null){//如果用户第一次打开app,跳转login
                    Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }


                SplashActivity.this.finish();
            }
        }.start();


    }


}
