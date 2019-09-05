package com.ooo.main.mvp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.mvp.ui.adapter.ChessGameAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.armscomponent.commonres.view.recyclerview.DividerGridItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 * 棋牌游戏
 */
public class GameElectronicFragment extends Fragment {


    @BindView(R2.id.recyclerView_game)
    RecyclerView recyclerViewGame;
    Unbinder unbinder;

    public GameElectronicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_game_electronic, container, false );
        unbinder = ButterKnife.bind ( this, view );
        init();
        return view;
    }

    private void init() {
        int[] imgs = {R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};
        ChessGameAdapter recycleAdapter = new ChessGameAdapter ( getActivity (), imgs);
        GridLayoutManager gridManager = new GridLayoutManager (getActivity (),4 );
        //设置布局管理器
        recyclerViewGame.setLayoutManager(gridManager);
        //设置为垂直布局，这也是默认的
        gridManager.setOrientation( OrientationHelper. VERTICAL);
        //设置Adapter
        recyclerViewGame.setAdapter(recycleAdapter);
        //设置分隔线
        recyclerViewGame.addItemDecoration( new DividerGridItemDecoration (getActivity () ));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        unbinder.unbind ();
    }
}
