package com.ooo.main.mvp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ooo.main.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameRedPacketFragment extends Fragment {


    public GameRedPacketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate ( R.layout.fragment_game_red_packet, container, false );
    }

}
