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
import com.ooo.main.mvp.ui.activity.CommisonActivity;
import com.ooo.main.mvp.ui.activity.CommissionListActivity;
import com.ooo.main.mvp.ui.activity.GameReadmeActivity;
import com.ooo.main.mvp.ui.activity.LuckyWheelActivity;
import com.ooo.main.mvp.ui.activity.PostersActivity;
import com.ooo.main.mvp.ui.activity.ScanResultActivity;
import com.ooo.main.mvp.ui.activity.UnderLineListActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bertsir.zbar.Qr.ScanResult;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
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
    @BindView(R2.id.layout_game_niuniu_info)
    LinearLayout layoutGameNiuNiuInfo;

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
            R2.id.layout_ommission_reward_info, R2.id.layout_game_control_info, R2.id.layout_game_niuniu_info})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.layout_scan) {
            //扫一扫
            QrConfig qrConfig = new QrConfig.Builder()
                    .setLooperWaitTime(5*1000)//连续扫描间隔时间
                    .create();
            QrManager.getInstance().init(qrConfig).startScan(getActivity (), new QrManager.OnScanResultCallback() {
                @Override
                public void onScanSuccess(ScanResult result) {
                    ScanResultActivity.start ( getActivity (), result.getContent());
                }
            });
        } else if (i == R.id.layout_promote) {
            //推广海报
            getActivity ().startActivity ( new Intent ( getActivity (), PostersActivity.class ) );
        } else if (i == R.id.layout_underline_query) {
            //下线查询
            ToastUtils.showShort ( getString ( R.string.discover_coming_soon ) );
        } else if (i == R.id.layout_underline_list) {
            //下线列表
            startActivity ( new Intent ( getActivity (), UnderLineListActivity.class ) );
        } else if (i == R.id.layout_luck) {
            //幸运大转盘
//            WebviewLuckDrawActivity.start ( getActivity (),"幸运大抽奖", ConfigUtil.LINK_LUCKY_DRAW );
            getActivity ().startActivity ( new Intent ( getActivity (),LuckyWheelActivity.class ) );
        } else if (i == R.id.layout_ommission_list) {
            //佣金列表
            startActivity ( new Intent ( getActivity (), CommisonActivity.class ) );
        } else if (i == R.id.layout_ommission_ranking) {
            //佣金排行榜
            startActivity ( new Intent ( getActivity (), CommissionListActivity.class ) );
        }else if (i == R.id.layout_game_reward_info) {
            //扫雷游戏奖励说明
            GameReadmeActivity.start ( getActivity (),0 );
        } else if (i == R.id.layout_ommission_reward_info) {
            //佣金排行榜奖励说明
            GameReadmeActivity.start ( getActivity (),1 );
        } else if (i == R.id.layout_game_control_info) {
            //禁抢游戏说明
            GameReadmeActivity.start ( getActivity (),2 );
        }else if (i == R.id.layout_game_niuniu_info) {
            //牛牛游戏说明
            GameReadmeActivity.start ( getActivity (),3 );
        }

    }
}
