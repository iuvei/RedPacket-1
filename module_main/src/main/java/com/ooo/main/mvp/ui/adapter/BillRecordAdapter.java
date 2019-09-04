package com.ooo.main.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ooo.main.R;
import com.ooo.main.mvp.model.entity.BillBean;
import com.ooo.main.mvp.model.entity.GridItemBean;

import java.util.List;

public class BillRecordAdapter extends BaseQuickAdapter<BillBean, BaseViewHolder> {


    public BillRecordAdapter(@Nullable List<BillBean> data) {
        super(R.layout.item_bill,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillBean item) {
        double money = item.getMoney();
        int textColor;
        if(money > 0){
            textColor = ContextCompat.getColor(mContext,R.color.text_green);
            helper.setText(R.id.tv_status,"收入");
        }else{
            textColor = ContextCompat.getColor(mContext,R.color.text_red);
            helper.setText(R.id.tv_status,"支出");
        }
        helper.setText(R.id.tv_money, String.format("%.2f",item.getMoney()))
                .setText(R.id.tv_describe, item.getDescribe())
                .setText(R.id.tv_time, item.getTime())
                .setTextColor(R.id.tv_status,textColor)
                .setTextColor(R.id.tv_money,textColor);
    }
}
