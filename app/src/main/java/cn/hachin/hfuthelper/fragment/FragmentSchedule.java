package cn.hachin.hfuthelper.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.taptwo.android.widget.TitleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;

import cn.hachin.hfuthelper.TimetableAdapter;
import cn.hachin.hfuthelper.R;
import cn.hachin.hfuthelper.Utils.Tools;


public class FragmentSchedule extends Fragment {

    private ViewFlow viewFlow;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.title_layout, container, false);


        viewFlow = (ViewFlow) view.findViewById(R.id.viewflow);
        TimetableAdapter adapter = new TimetableAdapter(getActivity());
        viewFlow.setAdapter(adapter, Tools.getCurrentWeek()-1);
        TitleFlowIndicator indicator = (TitleFlowIndicator) view.findViewById(R.id.viewflowindic);
        indicator.setTitleProvider(adapter);
        viewFlow.setFlowIndicator(indicator);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
