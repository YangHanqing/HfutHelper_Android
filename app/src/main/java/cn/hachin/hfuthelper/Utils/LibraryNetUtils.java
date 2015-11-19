package cn.hachin.hfuthelper.Utils;

import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.hachin.hfuthelper.Entity.Book;

/**
 * Created by yanghanqing on 15/11/18.
 * 图书馆网络访问工具类
 */
public class LibraryNetUtils {

    private static final String TAG = "LibraryNetUtils";
    private static DefaultHttpClient client = null;

    /**
     * 根据书名获取网页文本
     *
     * @param name 书名
     * @return 网页文本
     */
    public static String getBooksText(String name) {

        String text = null;

        //210.45.242.5:8080/opac/search_adv_result.php
        // ?sType0=any&q0=%E5%B7%A5%E7%A8%8B%E7%BB%8F%E6%B5%8E%E5%AD%A6&with_ebook=
        client = new DefaultHttpClient();
        HttpHost httpHost = new HttpHost("210.45.242.5", 8080);
        try {
            name = URLEncoder.encode(name, "utf-8");//Url 编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String partUrl = "/opac/search_adv_result.php?sType0=any&q0=" + name + "&with_ebook=";
        HttpGet get = new HttpGet(partUrl);

        text = getTextByGet(httpHost, get);


        return text;

    }

    public static String getTheBookInfo(String partUrl) {
        String text = null;
        //http://210.45.242.5:8080/opac/item.php?marc_no=0000485682
        HttpHost httpHost = new HttpHost("210.45.242.5", 8080);
        partUrl = "/opac/" + partUrl;
        HttpGet get = new HttpGet(partUrl);
        text = getTextByGet(httpHost, get);
        return text;
    }


    private static String getTextByGet(HttpHost httpHost, HttpGet get) {

        String text = null;
        try {
            client = new DefaultHttpClient();
            HttpResponse response = client.execute(httpHost, get);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                InputStream is = response.getEntity().getContent();
                text = NetUtils.getStringFromInputStream(is);

            } else {
                Log.i(TAG, "code " + statusCode);
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }


        return text;
    }
    public static String getLocation(String url) {
        String text = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get=new HttpGet(url);
            HttpResponse response = client.execute(get);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                InputStream is = response.getEntity().getContent();
                text = NetUtils.getStringFromInputStream(is);

            } else {
                Log.i(TAG, "code " + statusCode);
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }


        return text;
    }
}
