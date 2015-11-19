package cn.hachin.hfuthelper.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.taptwo.android.widget.ViewFlow;

import java.util.Iterator;
import java.util.List;

import cn.hachin.hfuthelper.Dao.CourseDao;
import cn.hachin.hfuthelper.Dao.ScoreDao;
import cn.hachin.hfuthelper.Entity.Course;
import cn.hachin.hfuthelper.Entity.ScoreTable;
import cn.hachin.hfuthelper.R;
import cn.hachin.hfuthelper.TimetableAdapter;
import cn.hachin.hfuthelper.Utils.Tools;


public class FragmentScore extends Fragment {
    private ListView mListView;
    private List<ScoreTable> scoreTables;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //获得数据库中的分数
        scoreTables = new ScoreDao(getActivity()).qurryAllScore();

        //Score list 填充数据
        View view = inflater.inflate(R.layout.fragment_score, container, false);
        mListView = (ListView) view.findViewById(R.id.lv_score);
        mListView.setAdapter(new MyAdapter());
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return scoreTables.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_score, null);
            } else {
                view = convertView;
            }
            int flag=0;//下标
            for(ScoreTable s:scoreTables){
                if(flag==position)
                {
                    ((TextView) view.findViewById(R.id.tv_courseName)).setText(s.getCourseName());
                    ((TextView) view.findViewById(R.id.tv_courseCode)).setText("课程代码:"+s.getCourseCode());
                    ((TextView) view.findViewById(R.id.tv_classNum)).setText("课程班级:"+s.getClassNum());
                    ( (TextView) view.findViewById(R.id.tv_score)).setText(s.getScore());
                    ( (TextView) view.findViewById(R.id.tv_secondScore)).setText(s.getSecondScore());
                    ( (TextView) view.findViewById(R.id.tv_credit)).setText(String.valueOf(s.getCredit()));
                    ( (TextView) view.findViewById(R.id.tv_point)).setText("0");
                    break;
                }else {
                    flag++;
                }
            }





            return view;
        }
    }


}
