package com.ooo.main.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ooo.main.R;
import com.ooo.main.mvp.model.entity.UnderLineBean;
import com.ooo.main.mvp.model.entity.UnderPayerBean;
import com.ooo.main.mvp.model.entity.WithdrawalRecordBean;

import java.util.List;
import java.util.logging.Level;

/**
 * creat at 2019/9/5
 * description
 * 下线列表adapter
 */
public class UnderLineListAdapter extends RecyclerView.Adapter <UnderLineListAdapter.ViewHolder> {

    private Context context;
    private List <UnderPayerBean.ResultBean.ListBean> data;
    private ItemClickListener itemClickListener;
    private int level = 1;

    public UnderLineListAdapter(Context context, List <UnderPayerBean.ResultBean.ListBean> data) {
        this.context = context;
        this.data = data;

    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setDatas(List <UnderPayerBean.ResultBean.ListBean> datas){
        this.data = datas;
        notifyDataSetChanged();
    }
    public void addData(List <UnderPayerBean.ResultBean.ListBean> data){
        this.data.addAll ( data );
        notifyDataSetChanged ();
    }

    public void setLevel(int level){
        this.level = level;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.item_underlist, parent, false );
        return new ViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvCreatDate.setText ( data.get ( position ).getTimes () );
        holder.tvNickName.setText ( data.get ( position ).getAgentnickname () );
        holder.tvUnderLineNum.setText ( level+"" );
        holder.llItem.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (itemClickListener!=null){
                    itemClickListener.onItemClick ( data,position );
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
        TextView tvUnderLineNum;
        TextView tvCreatDate;
        LinearLayout llItem;

        public ViewHolder(View itemView) {
            super ( itemView );
            tvNickName = itemView.findViewById ( R.id.tv_nickname );
            tvUnderLineNum = itemView.findViewById ( R.id.tv_underline_num );
            tvCreatDate = itemView.findViewById ( R.id.tv_creatDate );
            llItem = itemView.findViewById ( R.id.ll_item );
        }
    }

    public interface ItemClickListener {
        void onItemClick(List <UnderPayerBean.ResultBean.ListBean> data, int position);
    }
}