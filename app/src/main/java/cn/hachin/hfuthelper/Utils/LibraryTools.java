package cn.hachin.hfuthelper.Utils;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hachin.hfuthelper.Entity.Book;
import cn.hachin.hfuthelper.Entity.TheBook;

/**
 * Created by yanghanqing on 15/11/18.
 */
public class LibraryTools {

    public static List<Book> getBooksFromText(String text) {
        if (text == null) {
            return null;
        }


        Document doc = Jsoup.parse(text);
        Elements fathers = doc.getElementsByTag("table");//获取结果
        Element father = fathers.get(0);
        Elements trs = father.getElementsByTag("tr");//获取所有行

        List<Book> books = new ArrayList<Book>();
        for (int i = 1; i < trs.size(); i++) {
            Elements tds = trs.get(i).getElementsByTag("td");//获取一行
            int flag = 0;
            Book book = new Book();
            for (Element td : tds) {
                String info = td.text().trim();
                switch (flag) {//填数据
                    case 0:
                        flag++;
                        break;
                    case 1:
                        book.setName(info);
                        for (Element a : td.getElementsByTag("a")) {
                            String url = a.attr("href");
                            book.setUrl(url);
                        }
                        flag++;
                        break;
                    case 2:
                        book.setAuthor(info);
                        flag++;
                        break;
                    case 3:
                        book.setPublishing(info);
                        flag++;
                        break;
                    case 4:
                        book.setBookNumber(info);
                        flag++;
                        break;
                    case 5:
                        book.setBookType(info);
                        flag = 0;
                        books.add(book);
                        break;
                }
            }

        }

        return books;
    }

    /**
     * 根据文本获取馆藏信息
     *
     * @param text 待解析的html
     * @return 解析好的THE book list
     */
    public static List<TheBook> getTheBookFromText(String text) {
        if (text == null) {
            return null;
        }


        Document doc = Jsoup.parse(text);
        Elements fathers = doc.getElementsByTag("table");//获取结果
        Element father = fathers.get(0);
        Elements trs = father.getElementsByTag("tr");//获取所有行

        List<TheBook> theBooks = new ArrayList<TheBook>();
        for (int i = 1; i < trs.size(); i++) {
            Elements tds = trs.get(i).getElementsByTag("td");//获取一行
            int flag = 0;
            TheBook theBook = new TheBook();
            for (Element td : tds) {
                String info = td.text().trim();
                switch (flag) {//填数据
                    case 0:
                        theBook.setBarCode("索书号:" + info);
                        flag++;
                        break;
                    case 1:
                        theBook.setBookId(info);
                        flag++;
                        break;
                    case 2:
                        flag++;
                        break;
                    case 3:
                        theBook.setLocation(info);
                        flag++;
                        break;
                    case 4:
                        theBook.setState(info);
                        flag++;
                        theBooks.add(theBook);
                        break;
                    case 5:
                        String url = null;

                        for (Element a : td.getElementsByTag("a")) {
                            url = a.attr("href");
                        }
                        theBook.setUrl(url);
                        theBooks.add(theBook);
                        break;
                }
            }

        }

        return theBooks;
    }

    public static String getLocationFromText(String text) {
        if (text == null) {
            return null;
        }
        String location = null;
        String strWZxxxxxx = null;
        String strMsg = null;
        Document doc = Jsoup.parse(text);
        Elements scriptTags = doc.getElementsByTag("script");
        String nodeStr = null;
        for (Element tag : scriptTags) {
            for (DataNode node : tag.dataNodes()) {
                nodeStr = node.getWholeData();
                System.out.println("node:" + node);
            }
        }
        for (String str : nodeStr.split("\n")) {
            System.out.println("str:" + str);
            if (str.contains("strWZxxxxxx") && str.contains("|")) {
                strWZxxxxxx = ((str.split("\\|"))[1].split("\""))[0];
                System.out.println(strWZxxxxxx);
            } else if (str.contains("strMsg")) {
                strMsg = (str.split("\""))[1];
                System.out.println(strMsg);
                break;
            }
        }
        if (strMsg != null&&!TextUtils.isEmpty(strMsg)) {
            location = strMsg;
        } else{
            location = strWZxxxxxx;

        }

        return location;
    }
}
