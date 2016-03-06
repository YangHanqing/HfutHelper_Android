package cn.hachin.hfuthelper.Utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanghanqing on 15/11/7.
 */
public class NetUtils {
    private static final String TAG = "NetUtils";
    private static DefaultHttpClient client = null;

    //统一身份认证系统
    public static String loginof_TYSFZZXT(String userName, String password, String partURL) {

        String text = null;
        try {
            HttpResponse response = getHttpResponse(userName, password);

            text = getWebString(text, response);
            if (text == null) return null;//如果出错  返回空
            response = getHttpResponse(partURL);//获取返回的实体

            text = getWebString(text, response);//合并方法getString和getWebString
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;


    }

    /**
     * 根据用户名密码登录统一身份验证系统
     * @param userName
     * @param password
     * @return 访问结果的实体
     * @throws IOException
     */
    private static HttpResponse getHttpResponse(String userName, String password) throws IOException {
        client = new DefaultHttpClient();
        //IDToken0=&IDToken1=2013214399&IDToken2=19941203s&IDButton=Submit&goto=&encoded=false&inputCode=&gx_charset=UTF-8
        HttpHost httpHost = new HttpHost("ids1.hfut.edu.cn", 81);
        HttpPost post = new HttpPost("/amserver/UI/Login");
        List<NameValuePair> parameters = setPostParameters(userName, password);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
        post.setEntity(entity);
        return client.execute(httpHost, post);
    }

    @Nullable
    private static String getWebString(String text, HttpResponse response) throws IOException {
        //效验
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            InputStream is = response.getEntity().getContent();

            text = Tools.getStringFromInputStream(is);
            if (text.contains("非法") || text.contains("错误")) {
                return null;
            }
        } else {
            Log.i(TAG, "code " + statusCode);
            return null;
        }
        return text;
    }



    /**
     * 获取成绩 必须在成功获取Cookie后执行
     *
     * @return
     */
    public static String getScore() {
        String text = null;

        try {
            HttpResponse response = getHttpResponse();
            //效验是否有问题
            text = getWebString(text, response);
            if (text == null)
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.getConnectionManager().shutdown();
            }
        }

        return text;
    }

    private static HttpResponse getHttpResponse() throws IOException {
        //设置地址  http://bkjw.hfut.edu.cn/student/asp/Select_Success.asp
        HttpHost httpHost = new HttpHost("bkjw.hfut.edu.cn");
        //先获取bkjw的cookie
        HttpGet get = new HttpGet("/StuIndex.asp");
        client.execute(httpHost, get);
        //在访问课表
        get = new HttpGet("/student/asp/Select_Success.asp");
        return client.execute(httpHost, get);
    }


    /**
     * 根据url获取返回的实体 必须在获取了统一身份验证系统的cookie后使用
     *
     * @param partURL
     * @return
     * @throws IOException
     */
    private static HttpResponse getHttpResponse(String partURL) throws IOException {
        HttpHost httpHost;
        HttpResponse response;
        CookieStore cookieStore = client.getCookieStore();
        client.setCookieStore(cookieStore);
        httpHost = new HttpHost("my.hfut.edu.cn");
        HttpGet get = new HttpGet("/index.portal");
        response = client.execute(httpHost, get);
        get = new HttpGet(partURL);
        response = client.execute(httpHost, get);
        return response;
    }

    @NonNull
    private static List<NameValuePair> setPostParameters(String userName, String password) {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("IDToken0", ""));
        parameters.add(new BasicNameValuePair("IDToken1", userName));
        parameters.add(new BasicNameValuePair("IDToken2", password));
        parameters.add(new BasicNameValuePair("IDButton", "Submit"));
        parameters.add(new BasicNameValuePair("goto", ""));
        parameters.add(new BasicNameValuePair("encoded", "false"));
        parameters.add(new BasicNameValuePair("inputCode", ""));
        parameters.add(new BasicNameValuePair("gx_charset", "UTF-8"));
        return parameters;
    }




}
