package com.ooo.main.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.ooo.main.R;

/**
 * 0
 * creat at 2019/9/5
 * description
 * 选择默认头像
 */
public class ChooseHeadImgAdapter extends RecyclerView.Adapter<ChooseHeadImgAdapter.ViewHolder> {

    private Context context;
    private int[] data;
    private ItemClickListener itemClickListener;

    public ChooseHeadImgAdapter(Context context, int[] data){
        this.context = context;
        this.data = data;

    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
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
                if (itemClickListener!=null){
                    itemClickListener.onItemClick ( data,position );
                }
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

    public interface ItemClickListener{
        void onItemClick(int[] data,int position);
    }
}