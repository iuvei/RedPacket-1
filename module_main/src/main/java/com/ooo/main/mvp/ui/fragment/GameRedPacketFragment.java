package com.ooo.main.mvp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.mvp.ui.activity.RedPacketGameRoomActivity;
import com.ooo.main.mvp.ui.adapter.RedPacketGameAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.armscomponent.commonres.utils.NoScrollListView;
import me.jessyan.armscomponent.commonres.view.NoScrollViewPager;
import me.jessyan.armscomponent.commonsdk.entity.BannerEntity;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameRedPacketFragment extends Fragment {


    @BindView(R2.id.lv_game)
    NoScrollListView lvGame;
    Unbinder unbinder;

    public GameRedPacketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_game_red_packet, container, false );
        unbinder = ButterKnife.bind ( this, view );
        setListener();
        return view;
    }

    private void setListener() {
        lvGame.setOnItemClickListener ( new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView <?> adapterView, View view, int position, long id) {
                BannerEntity entity = (BannerEntity) lvGame.getItemAtPosition ( position );
                RedPacketGameRoomActivity.start ( getActivity (), entity );
            }
        } );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        unbinder.unbind ();
    }

    public void setLvGameData(List <BannerEntity> roomAdBanners){
        Log.e ( "tag","setLvGameData" );
        RedPacketGameAdapter adapter = new RedPacketGameAdapter ( roomAdBanners );
        lvGame.setAdapter ( adapter );
    }
}
