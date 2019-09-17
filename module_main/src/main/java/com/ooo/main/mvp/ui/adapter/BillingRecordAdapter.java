package com.ooo.main.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ooo.main.R;
import com.ooo.main.mvp.model.entity.BillingDetailBean;
import com.ooo.main.mvp.model.entity.WithdrawalRecordBean;

import java.util.List;

/**
 * @author lanjian
 * creat at 2019/9/5
 * description
 *
 * 账单明细adapter
 */
public class BillingRecordAdapter extends RecyclerView.Adapter <BillingRecordAdapter.ViewHolder> {

    private Context context;
    private List <BillingDetailBean.ResultBean.ListBean> data;
    private ItemClickListener itemClickListener;

    public BillingRecordAdapter(Context context, List <BillingDetailBean.ResultBean.ListBean> data) {
        this.context = context;
        this.data = data;

    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setDatas(List <BillingDetailBean.ResultBean.ListBean> datas){
        this.data = datas;
        notifyDataSetChanged();
    }
    public void addData(List <BillingDetailBean.ResultBean.ListBean> data){
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
        holder.tvTakeMoney.setText ( data.get ( position ).getDetails () );
        holder.tvTaketime.setText ( data.get ( position ).getAddtime () );
        if (Double.parseDouble ( data.get ( position ).getGold () )>=0){
            holder.tvMoney.setText ( "+"+data.get ( position ).getGold () );
            holder.tvMoney.setTextColor ( Color.BLACK );
            holder.tvStatue.setText ( "收入" );
        }else{
            holder.tvMoney.setText ( data.get ( position ).getGold () );
            holder.tvMoney.setTextColor ( Color.RED );
            holder.tvStatue.setText ( "支出" );
        }

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
        TextView tvTakeMoney;
        RelativeLayout rlItem;

        public ViewHolder(View itemView) {
            super ( itemView );
            tvMoney = itemView.findViewById ( R.id.tv_money );
            tvTaketime = itemView.findViewById ( R.id.tv_taketime );
            tvStatue = itemView.findViewById ( R.id.tv_statue );
            tvTakeMoney = itemView.findViewById ( R.id.tv_takemoney );
            rlItem = itemView.findViewById ( R.id.rl_item );

        }
    }

    public interface ItemClickListener {
        void onItemClick(List <BillingDetailBean.ResultBean.ListBean> data, int position);
    }
}