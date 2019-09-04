package com.ooo.main.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ooo.main.R;
import com.ooo.main.mvp.model.entity.GridItemBean;

import java.util.List;

public class GridItemAdapter extends BaseQuickAdapter<GridItemBean, BaseViewHolder> {
    public GridItemAdapter(@Nullable List<GridItemBean> data) {
        super(R.layout.item_grid_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GridItemBean item) {
        helper.setImageResource(R.id.image,item.getIconResId())
                .setText(R.id.text,item.getTextResId());
    }
}
