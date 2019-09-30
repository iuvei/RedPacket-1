package com.haisheng.easeim.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haisheng.easeim.R;
import com.haisheng.easeim.mvp.model.entity.ProfitRecordBean;
import com.haisheng.easeim.mvp.model.entity.RedPacketRecordBean;

import java.util.List;

import me.jessyan.armscomponent.commonres.utils.ConvertNumUtils;

/**
 * 0
 * creat at 2019/9/5
 * description
 *
 * 盈亏记录adapter
 */
public class ProfitRecordAdapter extends RecyclerView.Adapter <ProfitRecordAdapter.ViewHolder> {

    private Context context;
    private List <ProfitRecordBean.ResultBean.ListBean> data;
    private ItemClickListener itemClickListener;

    public ProfitRecordAdapter(Context context, List <ProfitRecordBean.ResultBean.ListBean> data) {
        this.context = context;
        this.data = data;
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setDatas(List <ProfitRecordBean.ResultBean.ListBean> datas){
        this.data = datas;
        notifyDataSetChanged();
    }
    public void addData(List <ProfitRecordBean.ResultBean.ListBean> data){
        this.data.addAll ( data );
        notifyDataSetChanged ();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.item_profit_record, parent, false );
        return new ViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ProfitRecordBean.ResultBean.ListBean bean = data.get ( position );
        double money = ConvertNumUtils.stringToDouble ( bean.getGold () );
        int textColor;

        if(money > 0){
            textColor = ContextCompat.getColor(context,R.color.text_green);
            holder.tv_status.setText ( "收入" );
            holder.tv_status.setTextColor ( textColor );
        }else{
            textColor = ContextCompat.getColor(context,R.color.text_red);
            holder.tv_status.setText ( "支出" );
            holder.tv_status.setTextColor ( textColor );
        }
        holder.tv_time.setText ( bean.getAddtime () );
        holder.tv_money.setText ( bean.getGold () );
        holder.tv_describe.setText ( bean.getDetails () );
        if (!TextUtils.isEmpty ( bean.getRoomid () ) && !TextUtils.isEmpty ( bean.getSetid () )){
            holder.tv_detail.setVisibility ( View.VISIBLE );
        }else{
            holder.tv_detail.setVisibility ( View.GONE );
        }
        holder.tv_detail.setOnClickListener ( new View.OnClickListener () {
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

        TextView tv_status;
        TextView tv_money;
        TextView tv_describe;
        TextView tv_time;
        TextView tv_detail;

        public ViewHolder(View itemView) {
            super ( itemView );
            tv_status = itemView.findViewById ( R.id.tv_status );
            tv_money = itemView.findViewById ( R.id.tv_money );
            tv_describe = itemView.findViewById ( R.id.tv_describe );
            tv_time = itemView.findViewById ( R.id.tv_time );
            tv_detail = itemView.findViewById ( R.id.tv_detail );
        }
    }

    public interface ItemClickListener {
        void onItemClick(List <ProfitRecordBean.ResultBean.ListBean> data, int position);
    }
}