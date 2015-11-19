/*
 * Copyright (C) 2011 Patrik �kerfeldt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.hachin.hfuthelper;

import org.taptwo.android.widget.TitleProvider;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

import cn.hachin.hfuthelper.Dao.CourseDao;
import cn.hachin.hfuthelper.Entity.Course;
import cn.hachin.hfuthelper.Utils.Tools;

public class TimetableAdapter extends BaseAdapter implements TitleProvider {

    private LayoutInflater mInflater;
    private Context context;
    private static final String[] names = {"第1周", "第2周", "第3周", "第4周", "第5周", "第6周", "第7周", "第8周", "第9周", "第10周", "第11周", "第12周", "第13周", "第14周", "第15周", "第16周", "第17周", "第18周"};

    //课程页面的button引用，6行7列
    private int[][] lessons = {
            {R.id.lesson11, R.id.lesson12, R.id.lesson13, R.id.lesson14, R.id.lesson15, R.id.lesson16, R.id.lesson17},
            {R.id.lesson21, R.id.lesson22, R.id.lesson23, R.id.lesson24, R.id.lesson25, R.id.lesson26, R.id.lesson27},
            {R.id.lesson31, R.id.lesson32, R.id.lesson33, R.id.lesson34, R.id.lesson35, R.id.lesson36, R.id.lesson37},
            {R.id.lesson41, R.id.lesson42, R.id.lesson43, R.id.lesson44, R.id.lesson45, R.id.lesson46, R.id.lesson47},
            {R.id.lesson51, R.id.lesson52, R.id.lesson53, R.id.lesson54, R.id.lesson55, R.id.lesson56, R.id.lesson57},
    };
    //某节课的背景图,用于随机获取
    private int[] bg = {R.drawable.kb1,
            R.drawable.kb2,
            R.drawable.kb3,
            R.drawable.kb4,
            R.drawable.kb5,
            R.drawable.kb6,
            R.drawable.kb7};


    public TimetableAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            convertView=null;
            System.gc();
        }
        View view = null;
        view = mInflater.inflate(R.layout.activity_course, null);



        //
        List<Course> courses = new CourseDao(context).qurryFromWeek(position + 1);//获得数据库中本周的课程
        Course course = new Course();
        //循环遍历
        Iterator<Course> courseIterator = courses.iterator();
        while (courseIterator.hasNext()) {
            course = courseIterator.next();


            int dayOfWeek = course.getDayOfWeek() - 1;//转换为lessons数组对应的下标
            int section = course.getTimeOfDay() - 1;//转换为lessons数组对应的下标
            Button lesson = (Button) view.findViewById(lessons[section][dayOfWeek]);//获得该节课的button
            int bgRes = bg[Tools.getRandom(bg.length - 1)];//随机获取背景色
            lesson.setBackgroundResource(bgRes);//设置背景
            String text = lesson.getText().toString().trim();
            if (TextUtils.isEmpty(text)) {
                lesson.setText(course.getName() + "@" + course.getLocation());//设置文本为课程名+“@”+教室
            } else {    //同一时间有不止一节课
                lesson.setText(text + "/" + course.getName() + "@" + course.getLocation());//设置文本为课程名+“@”+教室
            }
        }

        for (int i = 0; i < 7; i++) {
            Button oldLesson = (Button) view.findViewById(lessons[0][i]);//获得该节课的button
            for (int j = 1; j < 4; j++) {
                Button newLesson = (Button) view.findViewById(lessons[j][i]);//获得下节课的button
                int time = 1;
                if (!TextUtils.isEmpty(oldLesson.getText().toString()) && oldLesson.getText().toString().equals(newLesson.getText().toString())) {//相同
                    time++;
                    int height = Tools.dip2px(context, 100) * time;
                    oldLesson.getLayoutParams().height = height;
                    newLesson.setVisibility(View.GONE);
                } else {
                    oldLesson = newLesson;
                }

            }
        }
        return view;
    }

    /* (non-Javadoc)
     * @see org.taptwo.android.widget.TitleProvider#getTitle(int)
     */
    @Override
    public String getTitle(int position) {
        return names[position];
    }

}
