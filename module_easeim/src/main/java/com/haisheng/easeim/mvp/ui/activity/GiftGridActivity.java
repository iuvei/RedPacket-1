package com.haisheng.easeim.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.jess.arms.di.component.AppComponent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonres.view.recyclerview.DividerGridItemDecoration;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.entity.GiftEntity;

public class GiftGridActivity extends BaseSupportActivity {


    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private List<GiftEntity> mGiftEntities;

    public static void start(Activity context, String title, List<GiftEntity> giftEntities) {
        Intent intent = new Intent(context, GiftGridActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putSerializable("giftEntities", (Serializable) giftEntities);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_recycler;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        mGiftEntities = (List<GiftEntity>) bundle.getSerializable("giftEntities");

        setTitle(title);
        mRefreshLayout.setEnableRefresh(false);

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 4);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));

        mRecyclerView.setAdapter(new BaseQuickAdapter<GiftEntity, BaseViewHolder>(R.layout.item_gift_big, mGiftEntities) {

            @Override
            protected void convert(BaseViewHolder helper, GiftEntity item) {
                ImageView iconGift = helper.getView(R.id.imgIcon);
                ImageLoader.displayImage(mContext, item.getIconUrl(), R.mipmap.chat_icon_send_redmoney, R.mipmap.chat_icon_send_redmoney,iconGift);

                helper.setText(R.id.tvName, item.getName());
                helper.setText(R.id.tvNumber, "x" + item.getNumber());
            }
        });

    }

}
