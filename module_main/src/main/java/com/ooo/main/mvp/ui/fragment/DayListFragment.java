package com.ooo.main.mvp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.mvp.model.entity.RankingBean;
import com.ooo.main.mvp.ui.adapter.CommissionDayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayListFragment extends Fragment {


    @BindView(R2.id.listView)
    ListView listView;
    Unbinder unbinder;
    @BindView(R2.id.tv_nodata)
    TextView tvNodata;
    private CommissionDayAdapter adapter;
    private List <RankingBean.ResultBean.TodayBean> list = new ArrayList <> ();

    public DayListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_day_list, container, false );
        unbinder = ButterKnife.bind ( this, view );
        return view;
    }

    public void setCommissionList(List <RankingBean.ResultBean.TodayBean> today) {
        if (today != null && today.size () > 0) {
            tvNodata.setVisibility ( View.GONE );
            listView.setVisibility ( View.VISIBLE );
            list.addAll ( today );
            adapter = new CommissionDayAdapter ( list );
            listView.setAdapter ( adapter );
        }else{
            tvNodata.setVisibility ( View.VISIBLE );
            listView.setVisibility ( View.GONE );
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        unbinder.unbind ();
    }
}
