package com.ooo.main.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.mvp.model.entity.RewardBean;
import com.ooo.main.mvp.ui.adapter.RewardAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.ooo.main.mvp.ui.activity.LongImageActivity;
import me.jessyan.armscomponent.commonres.view.recyclerview.SpaceItemDecoration;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportFragment;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 18:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RewardFragment extends BaseSupportFragment {

    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;

    private RewardAdapter mAdapter;

    public static RewardFragment newInstance() {
        RewardFragment fragment = new RewardFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reward, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTitle.setText(R.string.reward);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        int sspace = ConvertUtils.dp2px(8);
        recyclerView.addItemDecoration(new SpaceItemDecoration(sspace,SpaceItemDecoration.ALL));
        mAdapter = new RewardAdapter(getRewardBeans());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                launchActivity(new Intent(mContext, LongImageActivity.class));
            }
        });
    }

    private List<RewardBean> getRewardBeans(){
        List<RewardBean> rewardBeans = new ArrayList<>();
        rewardBeans.add(new RewardBean(R.drawable.ic_recharge_reward,R.drawable.ic_recharge_reward));
        rewardBeans.add(new RewardBean(R.drawable.ic_send_redpacket_reward,R.drawable.ic_send_redpacket_reward));
        rewardBeans.add(new RewardBean(R.drawable.ic_grab_redpacket_reward,R.drawable.ic_grab_redpacket_reward));
        rewardBeans.add(new RewardBean(R.drawable.ic_water_commission_reward,R.drawable.ic_water_commission_reward));
        rewardBeans.add(new RewardBean(R.drawable.ic_straight_reward,R.drawable.ic_straight_reward));
        rewardBeans.add(new RewardBean(R.drawable.ic_invite_friend_reward,R.drawable.ic_invite_friend_reward));
        return rewardBeans;
    }


    @Override
    public void setData(@Nullable Object data) {

    }

    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }
}
