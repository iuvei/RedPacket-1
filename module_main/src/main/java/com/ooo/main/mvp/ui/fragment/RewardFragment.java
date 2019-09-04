package com.ooo.main.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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
 * 发现模块
 */
public class RewardFragment extends BaseSupportFragment {

    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.layout_scan)
    LinearLayout layoutScan;
    @BindView(R2.id.layout_promote)
    LinearLayout layoutPromote;
    @BindView(R2.id.layout_underline_query)
    LinearLayout layoutUnderlineQuery;
    @BindView(R2.id.layout_underline_list)
    LinearLayout layoutUnderlineList;
    @BindView(R2.id.layout_luck)
    LinearLayout layoutLuck;
    @BindView(R2.id.layout_ommission_list)
    LinearLayout layoutOmmissionList;
    @BindView(R2.id.layout_ommission_ranking)
    LinearLayout layoutOmmissionRanking;
    @BindView(R2.id.layout_game_reward_info)
    LinearLayout layoutGameRewardInfo;
    @BindView(R2.id.layout_ommission_reward_info)
    LinearLayout layoutOmmissionRewardInfo;
    @BindView(R2.id.layout_game_control_info)
    LinearLayout layoutGameControlInfo;

    public static RewardFragment newInstance() {
        RewardFragment fragment = new RewardFragment ();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate ( R.layout.fragment_reward, container, false );
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTitle.setText ( R.string.discover );
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    public void launchActivity(@NonNull Intent intent) {
        checkNotNull ( intent );
        ArmsUtils.startActivity ( intent );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView ( inflater, container, savedInstanceState );
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
    }

    @OnClick({R2.id.layout_scan, R2.id.layout_promote, R2.id.layout_underline_query, R2.id.layout_underline_list,
            R2.id.layout_luck, R2.id.layout_ommission_list, R2.id.layout_ommission_ranking, R2.id.layout_game_reward_info,
            R2.id.layout_ommission_reward_info, R2.id.layout_game_control_info})
    public void onViewClicked(View view) {
        switch (view.getId ()) {
            case R2.id.layout_scan:
                //扫一扫
                break;
            case R2.id.layout_promote:
                //推广海报
                break;
            case R2.id.layout_underline_query:
                //下线查询
                break;
            case R2.id.layout_underline_list:
                //下线列表
                break;
            case R2.id.layout_luck:
                //幸运大转盘
                break;
            case R2.id.layout_ommission_list:
                //佣金列表
                break;
            case R2.id.layout_ommission_ranking:
                //佣金排行榜
                break;
            case R2.id.layout_game_reward_info:
                //扫雷游戏奖励说明
                break;
            case R2.id.layout_ommission_reward_info:
                //佣金排行榜奖励说明
                break;
            case R2.id.layout_game_control_info:
                //禁抢游戏说明
                break;
        }
    }
}
