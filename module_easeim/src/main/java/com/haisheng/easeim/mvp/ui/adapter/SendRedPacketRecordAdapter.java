package com.haisheng.easeim.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haisheng.easeim.R;
import com.haisheng.easeim.mvp.model.entity.RedPacketRecordBean;

import java.util.List;

/**
 * creat at 2019/9/5
 * description
 *
 * 发包明细adapter
 */
public class SendRedPacketRecordAdapter extends RecyclerView.Adapter <SendRedPacketRecordAdapter.ViewHolder> {

    private Context context;
    private List <RedPacketRecordBean.ResultBean.ListBean> data;
    private ItemClickListener itemClickListener;
    private String nickName;

    public SendRedPacketRecordAdapter(Context context, List <RedPacketRecordBean.ResultBean.ListBean> data,String nickName) {
        this.context = context;
        this.data = data;
        this.nickName = nickName;
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setDatas(List <RedPacketRecordBean.ResultBean.ListBean> datas){
        this.data = datas;
        notifyDataSetChanged();
    }
    public void addData(List <RedPacketRecordBean.ResultBean.ListBean> data){
        this.data.addAll ( data );
        notifyDataSetChanged ();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.item_send_redpacket_record, parent, false );
        return new ViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvNickName.setText ( nickName );
        holder.tvTime.setText ( data.get ( position ).getAddtime () );
        holder.tvMoney.setText ( data.get ( position ).getGold () );

        holder.rlItem.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick ( data, position );
                }
            }
        } );

    }

    @Override
    public int getItemCount() {
        return data.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNickName;
        ImageView ivRedPack;
        TextView tvTime;
        TextView tvMoney;
        RelativeLayout rlItem;

        public ViewHolder(View itemView) {
            super ( itemView );
            tvNickName = itemView.findViewById ( R.id.tv_nickname );
            ivRedPack = itemView.findViewById ( R.id.iv_redpack );
            tvTime = itemView.findViewById ( R.id.tv_time );
            tvMoney = itemView.findViewById ( R.id.tv_money );
            rlItem = itemView.findViewById ( R.id.rl_item );
        }
    }

    public interface ItemClickListener {
        void onItemClick(List <RedPacketRecordBean.ResultBean.ListBean> data, int position);
    }
}