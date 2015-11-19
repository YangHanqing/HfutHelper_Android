package cn.hachin.hfuthelper.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cn.hachin.hfuthelper.LoginActivity;
import cn.hachin.hfuthelper.MainActivity;
import cn.hachin.hfuthelper.R;


//
public class MenuFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {
    View view;
    private static FragmentSchedule fragmentSchedule=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.list_view, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView list_view = (ListView) view.findViewById(R.id.list_view);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_expandable_list_item_1, android.R.id.text1, initData());

        list_view.setAdapter(new MyAdapter());
        list_view.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment f = null;
        switch (position) {
            case 0:
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                this.getActivity().finish();
                break;
            case 1:
                if(fragmentSchedule ==null){
                    fragmentSchedule = new FragmentSchedule();
                }
                f = fragmentSchedule;
                break;
            case 2:
                f = new FragmentScore();
                break;
            case 3:
                /*
                f = new FragmentInquiry();
                break;
            case 4:
                */
                f = new FragmentLibrary();
                break;
        }
        switchFragment(f);

    }

    private void switchFragment(Fragment f) {
        if (f != null) {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).switchFragment(f);
            }
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 4;
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
            view = LayoutInflater.from(getActivity()).inflate(R.layout.list_view_item, null);
            ImageView itemImage = (ImageView) view.findViewById(R.id.item_image);
            TextView itemText = (TextView) view.findViewById(R.id.item_text);
            switch (position) {
                case 0:
                    view = LayoutInflater.from(getActivity()).inflate(R.layout.list_view_item_header, null);
                    break;
                case 1:
                    itemImage.setImageResource(R.drawable.timetable);
                    itemText.setText("课表");
                    break;
                case 2:
                    itemImage.setImageResource(R.drawable.score);
                    itemText.setText("成绩");
                    break;
                case 3:
                    /*
                    itemImage.setImageResource(R.drawable.search);
                    itemText.setText("查询");
                    break;
                case 4:
                */
                    itemImage.setImageResource(R.drawable.library);
                    itemText.setText("图书");
                    break;

            }
            return view;
        }
    }


}
