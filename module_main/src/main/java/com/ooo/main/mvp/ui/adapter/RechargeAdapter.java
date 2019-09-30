package com.ooo.main.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ooo.main.R;

import java.util.List;

/**
 * creat at 2019/9/5
 * description
 * 充值金额列表
 */
public class RechargeAdapter extends RecyclerView.Adapter<RechargeAdapter.ViewHolder> {

    private Context context;
    private List<Integer> data;
    private ItemClickListener itemClickListener;

    public RechargeAdapter(Context context, List<Integer> data){
        this.context = context;
        this.data = data;

    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.item_recharge_money,parent,false);
        return new ViewHolder(view);
    }

    private TextView lastText;
    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {
        holder.btnMoney.setText ( data.get ( position )+"" );

        holder.btnMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener!=null){
                    itemClickListener.onItemClick ( data,position );
                }
                //改变上一次选中的选项背景色
                if (lastText!=null){
                    lastText.setBackgroundResource ( R.drawable.bg_frame_button );
                }
                holder.btnMoney.setBackgroundResource ( R.drawable.bg_frame_green_button );
                lastText = holder.btnMoney;
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView btnMoney;

        public ViewHolder(View itemView) {
            super ( itemView );
            btnMoney = itemView.findViewById ( R.id.tv_money );

        }
    }

    public interface ItemClickListener{
        void onItemClick(List<Integer> data, int position);
    }
}