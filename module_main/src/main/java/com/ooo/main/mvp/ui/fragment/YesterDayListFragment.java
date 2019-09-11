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
import com.ooo.main.mvp.model.entity.CommissionBean;
import com.ooo.main.mvp.ui.activity.CommissionListActivity;
import com.ooo.main.mvp.ui.adapter.CommissionAdapter;

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
    private CommissionAdapter adapter;
    private List<CommissionBean> list = new ArrayList <> (  );
    private boolean firstLoad = true;
    private boolean mIsPrepare;

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
        mIsPrepare = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint ( isVisibleToUser );
        if (isVisibleToUser && firstLoad && mIsPrepare){
            getCommissionList ();
            adapter = new CommissionAdapter ( list );
            listView.setAdapter ( adapter );
            firstLoad = false;
        }
    }

    private void getCommissionList() {
        for (int i=0;i<15;i++){
            CommissionBean bean = new CommissionBean ();
            bean.setCommission ( (int)(Math.random ()*10000000)/100+"" );
            bean.setNum ( i+1 );
            bean.setNickname ( "用户"+i );
            list.add ( bean );
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        unbinder.unbind ();
    }
}
