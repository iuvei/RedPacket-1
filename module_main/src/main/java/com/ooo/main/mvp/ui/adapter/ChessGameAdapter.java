package com.ooo.main.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.ooo.main.R;
import com.ooo.main.R2;

/**
 * creat at 2019/9/5
 * description
 * 棋牌游戏
 */
public class ChessGameAdapter extends RecyclerView.Adapter<ChessGameAdapter.ViewHolder> {

    private Context context;
    private int[] data;

    public ChessGameAdapter(Context context, int[] data){
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.item_game_img,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {
        holder.ivGame.setImageResource ( data[position] );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort ( "余额不足，请您在充值后再体验游戏！" );
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivGame;

        public ViewHolder(View itemView) {
            super ( itemView );
            ivGame = itemView.findViewById ( R.id.iv_game );

        }
    }
}