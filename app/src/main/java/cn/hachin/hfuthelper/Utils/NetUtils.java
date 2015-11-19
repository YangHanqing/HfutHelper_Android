package cn.hachin.hfuthelper.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import cn.hachin.hfuthelper.Entity.ScoreTable;

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
            client = new DefaultHttpClient();
            //IDToken0=&IDToken1=2013214399&IDToken2=19941203s&IDButton=v&goto=aHR0cDovL215LmhmdXQuZWR1LmNuL1N0dUluZGV4LmFzcA%3D%3D&encoded=true&inputCode=&gx_charset=UTF-8
            //IDToken0=&IDToken1=2013214399&IDToken2=19941203s&IDButton=Submit&goto=&encoded=false&inputCode=&gx_charset=UTF-8
            HttpHost httpHost = new HttpHost("ids1.hfut.edu.cn",81);

            HttpPost post = new HttpPost("/amserver/UI/Login");
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("IDToken0", ""));
            parameters.add(new BasicNameValuePair("IDToken1", userName));
            parameters.add(new BasicNameValuePair("IDToken2", password));
            parameters.add(new BasicNameValuePair("IDButton", "Submit"));
            parameters.add(new BasicNameValuePair("goto", ""));
            parameters.add(new BasicNameValuePair("encoded", "false"));
            parameters.add(new BasicNameValuePair("inputCode", ""));
            parameters.add(new BasicNameValuePair("gx_charset", "UTF-8"));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
            post.setEntity(entity);
            HttpResponse response = client.execute(httpHost, post);

            //效验
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                InputStream is = response.getEntity().getContent();

                text = NetUtils.getStringFromInputStream(is);
                System.out.println(text);
                if (text.contains("非法") || text.contains("错误")) {
                    return null;
                }


            } else {

                Log.i(TAG, "code " + statusCode);
                return null;
            }

            CookieStore cookieStore = client.getCookieStore();
            client.setCookieStore(cookieStore);
            httpHost = new HttpHost("my.hfut.edu.cn");
            HttpGet get = new HttpGet("/index.portal");
            response = client.execute(httpHost, get);
            get = new HttpGet(partURL);
            response = client.execute(httpHost, get);

            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                InputStream is = response.getEntity().getContent();//////

                text = NetUtils.getStringFromInputStream(is);
                System.out.println("----->" + text);
            } else {
                Log.i(TAG, "code " + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;


    }

    public static String getScore() {
        String text = null;

        try {


            //设置地址  http://bkjw.hfut.edu.cn/student/asp/Select_Success.asp
            HttpHost httpHost = new HttpHost("bkjw.hfut.edu.cn");
            //先获取bkjw的cookie
            HttpGet get = new HttpGet("/StuIndex.asp");
            client.execute(httpHost, get);
            //在访问课表
            get = new HttpGet("/student/asp/Select_Success.asp");
            HttpResponse response = client.execute(httpHost, get);

            //效验
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                //获取html流
                InputStream is = response.getEntity().getContent();

                text = NetUtils.getStringFromInputStream(is);

                if (text.contains("非法") || text.contains("错误")) {
                    return null;
                }

            } else {

                Log.i(TAG, "code " + statusCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(client!=null){
                client.getConnectionManager().shutdown();

            }
        }

        return text;
    }

    public static String getStringFromInputStream(InputStream is) throws IOException {
        //模版代码 输入流转化为String

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;

        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        is.close();

        String html = baos.toString();
        String charset = "gbk"; // 把流中的数据转换成字符串, 采用的编码是: utf-8
        if (html.contains("utf-8")) {        // 如果包含gbk, gb2312编码, 就采用gbk编码进行对字符串编码
            charset = "utf-8";
        }
        html = new String(baos.toByteArray(), charset);    // 对原有的字节数组进行使用处理后的编码名称进行编码
        baos.close();
        return html;
    }


}
