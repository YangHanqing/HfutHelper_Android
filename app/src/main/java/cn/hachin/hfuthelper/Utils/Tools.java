package cn.hachin.hfuthelper.Utils;

import android.content.Context;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.hachin.hfuthelper.Entity.Course;
import cn.hachin.hfuthelper.Entity.ScoreTable;


/**
 * Created by yanghanqing on 15/11/7.
 */
public class Tools {
    //获取课表
    public static List<Course> getCoursesFromXxmhText(String text) {
        List<Course> courses = new ArrayList<Course>();
        Document doc = Jsoup.parse(text);
        Elements fathers = doc.getElementsByTag("tbody");//获取课表
        Element father = null;
        try {
            father = fathers.get(3);
        } catch (Exception e) {
            return null;
        }
        Elements trs = father.getElementsByTag("tr");//获取所有行
        String bigClassInfo = "";

        for (int i = 0; i < trs.size(); i++) {
            Elements tds = trs.get(i).getElementsByTag("td");//获取一行
            for (int j = 2; j < 9; j++) {
                Element td = tds.get(j);//获取一个时间块
                bigClassInfo = td.text();//获取文本
                if (!TextUtils.isEmpty(bigClassInfo.trim())) {//判断时间块是否为空

                    String[] classInfos = bigClassInfo.split("\\.");
                    for (String classInfo : classInfos) {
                        if (!TextUtils.isEmpty(classInfo.trim())) {
                            //判断课程块是否为空

                            //新建课程
                            Course course = new Course(j - 1, i + 1, null, null, null, null, null);
                            //获取课程详细信息

                            //防止课程名称中又空格

                            String[] bigInfos = classInfo.trim().split("00");//利用班号00开头进行分割
                            String className = bigInfos[0].trim();//课程名称
                            String smallInfos = "00" + bigInfos[1];//其他信息

                            course.setName(className);//设置名称

                            String[] otherInfos = smallInfos.trim().split(" ");
                            for (int m = 0; m < otherInfos.length; m++) {
                                //添加课程详细信息
                                // String classNum, String weeks, String location, String teacher

                                switch (m) {
                                    case 0:
                                        course.setClassNum(otherInfos[m]);
                                        break;
                                    case 1:
                                        course.setWeeks(otherInfos[m]);
                                        break;
                                    case 2:
                                        course.setLocation(otherInfos[m]);
                                        break;
                                    case 3:
                                        course.setTeacher(otherInfos[m]);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            courses.add(course);
                        }

                    }
                }


            }

        }

        return courses;//返回

    }

    //获取成绩
    public static List<ScoreTable> getScoresFromBkjwText(String text) {
        Document doc = Jsoup.parse(text);
        Elements fathers = doc.getElementsByTag("table");//获取课表
        Element father = fathers.get(0);
        Elements trs = father.getElementsByTag("tr");//获取所有行

        List<ScoreTable> scoreTables = new ArrayList<ScoreTable>();
        for (int i = 1; i < trs.size(); i++) {
            Elements tds = trs.get(i).getElementsByTag("td");//获取一行
            int flag = 0;
            ScoreTable scoreTable = new ScoreTable();
            for (Element td : tds) {
                String info = td.text().trim();
                switch (flag) {//填数据
                    case 0:
                        scoreTable.setSemester(info);
                        flag++;
                        break;
                    case 1:
                        scoreTable.setCourseCode(info);
                        flag++;
                        break;
                    case 2:
                        scoreTable.setCourseName(info);
                        flag++;
                        break;
                    case 3:
                        scoreTable.setClassNum(info);
                        flag++;
                        break;
                    case 4:
                        scoreTable.setScore(info);
                        flag++;
                        break;
                    case 5:
                        scoreTable.setSecondScore(info);
                        flag++;
                        break;
                    case 6:
                            if (info.startsWith(".")) {
                                info = "0"+info;
                            }
                            scoreTable.setCredit(Double.parseDouble(info));
                        scoreTables.add(scoreTable);
                        flag = 0;
                        break;

                }
            }

        }
        return scoreTables;
    }

    //获取上课周数
    public static StringBuffer getWeekFromString(String string) {
        StringBuffer stringBuffer = new StringBuffer("0000000000000000000");//初始化 19个
        // (4-8,10-14双周)
        if (string.contains("双")) {
            string = string.replace("周", "").replace("单", "").replace("双", "").replace("(", "").replace(")", "");

            String[] split = string.split("[,-]");// 4-8   10-14
            int[] weekFlag = new int[]{0, 0, 0, 0};
            for (int i = 0; i < split.length; i++) {
                weekFlag[i] = Integer.parseInt(split[i]);//字符串转数字 4 8 10 14
            }
            if (weekFlag[0] % 2 == 1) {//确保起始周数为双
                weekFlag[0]++;
            }

            for (int i = weekFlag[0]; i <= weekFlag[1]; i = i + 2) {
                stringBuffer.setCharAt(i, '1');
            }
            if (weekFlag[2] != 0) {
                if (weekFlag[2] % 2 == 1) {
                    weekFlag[2]++;
                }
                for (int i = weekFlag[2]; i <= weekFlag[3]; i = i + 2) {
                    stringBuffer.setCharAt(i, '1');

                }
            }
        } else if (string.contains("单")) {
            string = string.replace("周", "").replace("单", "").replace("双", "").replace("(", "").replace(")", "");

            String[] split = string.split("[,-]");// 4-8   10-14
            int[] weekFlag = new int[4];
            for (int i = 0; i < split.length; i++) {
                weekFlag[i] = Integer.parseInt(split[i]);//字符串转数字 4 8 10 14
            }
            if (weekFlag[0] % 2 == 0) {//确保起始周数为单
                weekFlag[0]++;
            }

            for (int i = weekFlag[0]; i <= weekFlag[1]; i = i + 2) {
                stringBuffer.setCharAt(i, '1');

            }
            if (weekFlag[2] != 0) {
                if (weekFlag[2] % 2 == 0) {
                    weekFlag[2]++;
                }
                for (int i = weekFlag[2]; i <= weekFlag[3]; i = i + 2) {
                    stringBuffer.setCharAt(i, '1');

                }
            }
        } else {
            //(10-17周)
            string = string.replace("周", "").replace("单", "").replace("双", "").replace("(", "").replace(")", "");

            String[] split = string.split("[,-]");// 4-8   10-14

            int[] weekFlag = new int[4];

            for (int i = 0; i < split.length; i++) {
                weekFlag[i] = Integer.parseInt(split[i]);//字符串转数字 4 8 10 14
            }

            for (int i = weekFlag[0]; i <= weekFlag[1]; i = i + 1) {
                stringBuffer.setCharAt(i, '1');

            }
            if (weekFlag[2] != 0) {
                for (int i = weekFlag[2]; i <= weekFlag[3]; i = i + 1) {
                    stringBuffer.setCharAt(i, '1');

                }
            }

        }

        return stringBuffer;
    }



    /**
     * 获得指定范围内的随机数
     *
     * @param max
     * @return int
     */
    public static int getRandom(int max) {
        return (int) (Math.random() * max);
    }

    public static int dip2px(Context context, float dpValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale + 0.5f);
    }

    public static int getCurrentWeek(){
        long week;
        week=(System.currentTimeMillis()/1000-1442160000)/604800+1;
        if(week>18){
            week=18;
        }
        return (int)week;
    }


}
