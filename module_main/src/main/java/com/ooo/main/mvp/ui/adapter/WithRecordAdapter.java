package com.ooo.main.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ooo.main.R;
import com.ooo.main.mvp.model.entity.WithRecordBean;
import com.ooo.main.mvp.model.entity.WithdrawalRecordBean;

import java.util.List;

/**
 * creat at 2019/9/5
 * description
 * 提现记录adapter
 */
public class WithRecordAdapter extends RecyclerView.Adapter <WithRecordAdapter.ViewHolder> {

    private Context context;
    private List<WithRecordBean.ResultBean.ListBean> data;
    private ItemClickListener itemClickListener;

    public WithRecordAdapter(Context context, List <WithRecordBean.ResultBean.ListBean> data) {
        this.context = context;
        this.data = data;

    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setDatas(List <WithRecordBean.ResultBean.ListBean> datas){
        this.data = datas;
        notifyDataSetChanged();
    }
    public void addData(List <WithRecordBean.ResultBean.ListBean> data){
        this.data.addAll ( data );
        notifyDataSetChanged ();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.item_withdrawal_record, parent, false );
        return new ViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvStatue.setText ( data.get ( position ).getStatusValue () );
        holder.tvTaketime.setText ( data.get ( position ).getAddtime () );
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

        TextView tvMoney;
        TextView tvTaketime;
        TextView tvStatue;
        RelativeLayout rlItem;

        public ViewHolder(View itemView) {
            super ( itemView );
            tvMoney = itemView.findViewById ( R.id.tv_money );
            tvTaketime = itemView.findViewById ( R.id.tv_taketime );
            tvStatue = itemView.findViewById ( R.id.tv_statue );
            rlItem = itemView.findViewById ( R.id.rl_item );

        }
    }

    public interface ItemClickListener {
        void onItemClick(List <WithRecordBean.ResultBean.ListBean> data, int position);
    }
}