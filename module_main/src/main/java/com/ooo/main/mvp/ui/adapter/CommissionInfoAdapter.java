package com.ooo.main.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ooo.main.R;
import com.ooo.main.mvp.model.entity.CommissionInfo;
import com.ooo.main.mvp.model.entity.WithdrawalRecordBean;

import java.util.List;

/**
 * @author lanjian
 * creat at 2019/9/5
 * description
 * 佣金列表adapter
 */
public class CommissionInfoAdapter extends RecyclerView.Adapter <CommissionInfoAdapter.ViewHolder> {

    private Context context;
    private List <CommissionInfo> data;
    private ItemClickListener itemClickListener;

    public CommissionInfoAdapter(Context context, List <CommissionInfo> data) {
        this.context = context;
        this.data = data;

    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setDatas(List <CommissionInfo> datas){
        this.data = datas;
        notifyDataSetChanged();
    }
    public void addData(List <CommissionInfo> data){
        this.data.addAll ( data );
        notifyDataSetChanged ();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.item_commission_info, parent, false );
        return new ViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvID.setText ( data.get ( position ).getID () );
        holder.tvDate.setText ( data.get ( position ).getDate () );
        holder.tvDetail.setText ( data.get ( position ).getDetail () );
        holder.tvCommission.setText ( data.get ( position ).getCommission () );
    }

    @Override
    public int getItemCount() {
        return data.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvID;
        TextView tvCommission;
        TextView tvDetail;
        TextView tvDate;

        public ViewHolder(View itemView) {
            super ( itemView );
            tvID = itemView.findViewById ( R.id.tv_id );
            tvCommission = itemView.findViewById ( R.id.tv_commission );
            tvDetail = itemView.findViewById ( R.id.tv_detail );
            tvDate = itemView.findViewById ( R.id.tv_date );

        }
    }

    public interface ItemClickListener {
        void onItemClick(List <CommissionInfo> data, int position);
    }
}