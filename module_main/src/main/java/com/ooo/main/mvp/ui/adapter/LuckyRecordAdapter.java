package com.ooo.main.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ooo.main.R;
import com.ooo.main.mvp.model.entity.LuckyBean;
import com.ooo.main.mvp.model.entity.UnderLineBean;
import com.ooo.main.mvp.model.entity.WithdrawalRecordBean;

import java.util.List;

/**
 * @author lanjian
 * creat at 2019/9/5
 * description
 * 抽奖记录adapter
 */
public class LuckyRecordAdapter extends RecyclerView.Adapter <LuckyRecordAdapter.ViewHolder> {

    private Context context;
    private List <LuckyBean> data;
    private ItemClickListener itemClickListener;

    public LuckyRecordAdapter(Context context, List <LuckyBean> data) {
        this.context = context;
        this.data = data;

    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setDatas(List <LuckyBean> datas){
        this.data = datas;
        notifyDataSetChanged();
    }
    public void addData(List <LuckyBean> data){
        this.data.addAll ( data );
        notifyDataSetChanged ();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.item_luckyrecord, parent, false );
        return new ViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvPrize.setText ( data.get ( position ).getPrize () );
        holder.tvReceive.setText ( data.get ( position ).getAward () );
    }

    @Override
    public int getItemCount() {
        return data.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPrize;
        TextView tvReceive;

        public ViewHolder(View itemView) {
            super ( itemView );
            tvPrize = itemView.findViewById ( R.id.tv_prize );
            tvReceive = itemView.findViewById ( R.id.tv_receive );
        }
    }

    public interface ItemClickListener {
        void onItemClick(List <WithdrawalRecordBean> data, int position);
    }
}