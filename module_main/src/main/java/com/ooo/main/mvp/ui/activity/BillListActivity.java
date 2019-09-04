package com.ooo.main.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.mvp.model.entity.GridItemBean;
import com.ooo.main.mvp.ui.adapter.BillRecordAdapter;
import com.ooo.main.mvp.ui.adapter.GridItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.view.recyclerview.DividerGridItemDecoration;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;

public class BillListActivity extends BaseSupportActivity {

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;

    private GridItemAdapter mAdapter;
    private List<GridItemBean> mDatas;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bill_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDatas();

        recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        GridLayoutManager layoutParams = new GridLayoutManager(this,2);
        ArmsUtils.configRecyclerView(recyclerView, layoutParams);

        mAdapter = new GridItemAdapter(mDatas);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GridItemBean gridItemBean = (GridItemBean) adapter.getItem(position);
                BillRecordActivity.start(mContext,gridItemBean.getTextResId());
            }
        });
    }

    private void initDatas(){
        mDatas = new ArrayList<>();
        mDatas.add(new GridItemBean(R.string.all_record,R.drawable.icon_all_record));
        mDatas.add(new GridItemBean(R.string.reward_record,R.drawable.icon_reward_record));
        mDatas.add(new GridItemBean(R.string.recharge_record,R.drawable.icon_cz_record));
        mDatas.add(new GridItemBean(R.string.withdraw_record,R.drawable.icon_tx_record));
        mDatas.add(new GridItemBean(R.string.send_redpacket_record,R.drawable.icon_send_record));
        mDatas.add(new GridItemBean(R.string.grab_redpacket_record,R.drawable.icon_grab_record));
        mDatas.add(new GridItemBean(R.string.profit_loss_record,R.drawable.icon_yk_record));
        mDatas.add(new GridItemBean(R.string.commission_income_record,R.drawable.icon_yj_record));
        mDatas.add(new GridItemBean(R.string.fruit_machine_record,R.drawable.icon_fruit_record));
        mDatas.add(new GridItemBean(R.string.coming_soon,R.drawable.icon_wait_update));
    }

//    @OnClick({R2.id.tv_all_record, R2.id.tv_reward_record, R2.id.tv_recharge_record, R2.id.tv_withdraw_record, R2.id.tv_send_redpacket_record,
//            R2.id.tv_grab_redpacket_record, R2.id.tv_profit_loss_record, R2.id.tv_commission_income, R2.id.tv_fruit_machine_record})
//    public void onViewClicked(View view) {
//        int i = view.getId();
//        if (i == R.id.tv_all_record) {
//        } else if (i == R.id.tv_reward_record) {
//        } else if (i == R.id.tv_recharge_record) {
//        } else if (i == R.id.tv_withdraw_record) {
//        } else if (i == R.id.tv_send_redpacket_record) {
//        } else if (i == R.id.tv_grab_redpacket_record) {
//        } else if (i == R.id.tv_profit_loss_record) {
//        } else if (i == R.id.tv_commission_income) {
//        } else if (i == R.id.tv_fruit_machine_record) {
//        }
//    }
}
