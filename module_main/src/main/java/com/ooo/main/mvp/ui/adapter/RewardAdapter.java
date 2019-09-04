package com.ooo.main.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ooo.main.R;
import com.ooo.main.mvp.model.entity.BillBean;
import com.ooo.main.mvp.model.entity.RewardBean;

import java.util.List;

public class RewardAdapter extends BaseQuickAdapter<RewardBean, BaseViewHolder> {


    public RewardAdapter(@Nullable List<RewardBean> data) {
        super(R.layout.item_reward,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RewardBean item) {
        helper.setImageResource(R.id.image,item.getImgResId());
    }
}
