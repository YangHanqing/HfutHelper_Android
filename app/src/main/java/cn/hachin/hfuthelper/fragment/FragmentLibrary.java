package cn.hachin.hfuthelper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cn.hachin.hfuthelper.BooksActivity;
import cn.hachin.hfuthelper.R;


public class FragmentLibrary extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Score list 填充数据
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        final EditText et_bookName = (EditText) view.findViewById(R.id.et_library_bookName);
        Button btn_quarry = (Button) view.findViewById(R.id.btn_library_quarry);

        btn_quarry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName=et_bookName.getText().toString();
                Intent intent=new Intent(getActivity(),BooksActivity.class);
                intent.putExtra("bookName", bookName);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
