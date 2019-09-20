package com.ooo.main.mvp.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.mvp.model.entity.RankingBean;
import com.ooo.main.mvp.ui.adapter.CommissionDayAdapter;
import com.ooo.main.mvp.ui.adapter.CommissionYesterDayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class YesterDayListFragment extends Fragment {


    @BindView(R2.id.listView)
    ListView listView;
    Unbinder unbinder;
    private CommissionYesterDayAdapter adapter;
    private List<RankingBean.ResultBean.YesterdayBean> list = new ArrayList <> (  );

    public YesterDayListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_yester_day, container, false );
        unbinder = ButterKnife.bind ( this, view );
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        unbinder.unbind ();
    }

    public void setCommissionList(List <RankingBean.ResultBean.YesterdayBean> yesterday) {
        if (yesterday!=null && yesterday.size ()>0){
            list.addAll ( yesterday );
            adapter = new CommissionYesterDayAdapter ( list );
            listView.setAdapter ( adapter );
        }
    }
}
