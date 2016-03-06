package cn.hachin.hfuthelper.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanghanqing on 15/11/7.
 * 数据库访问类
 */
public class Dao {
    public static boolean saveUserInfo(Context context, String username, String password){
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            //存数据
            edit.putString("username",username);
            edit.putString("password",password);

            edit.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static Map<String,String> getUserInfo(Context context){
        Map<String,String> userInfo=null;
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("username", null);
        String password=sharedPreferences.getString("password",null);

        if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)){
            userInfo=new HashMap<>();
            userInfo.put("username",username);
            userInfo.put("password",password);
            return userInfo;
        }

        return userInfo;
    }



}
