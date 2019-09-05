package com.ooo.main.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerAdNoticeComponent;
import com.ooo.main.mvp.contract.AdNoticeContract;
import com.ooo.main.mvp.presenter.AdNoticePresenter;
import com.ooo.main.view.popupwindow.SwitchShortcutPopupWindow;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonres.view.RoundBackgroudLinearyLayout;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportFragment;
import me.jessyan.armscomponent.commonsdk.entity.AdBannerInfo;
import me.jessyan.armscomponent.commonsdk.entity.BannerEntity;
import pl.droidsonroids.gif.GifImageView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 10:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class GameFragment extends BaseSupportFragment <AdNoticePresenter> implements AdNoticeContract.View {

    @BindView(R2.id.banner)
    BGABanner banner;
    @BindView(R2.id.tv_notices)
    TextView tvNotices;
    @BindView(R2.id.giv_game_redpacket)
    GifImageView givGameRedpacket;
    @BindView(R2.id.tv_game_redpacket)
    TextView tvGameRedpacket;
    @BindView(R2.id.giv_game_electronic)
    GifImageView givGameElectronic;
    @BindView(R2.id.tv_game_electronic)
    TextView tvGameElectronic;
    @BindView(R2.id.giv_game_chess)
    GifImageView givGameChess;
    @BindView(R2.id.tv_game_chess)
    TextView tvGameChess;
    @BindView(R2.id.giv_game_leisure)
    GifImageView givGameLeisure;
    @BindView(R2.id.tv_game_leisure)
    TextView tvGameLeisure;

    @BindView(R2.id.ll_game_redpacket)
    LinearLayout llGameRedpacket;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.round_redpacket)
    RoundBackgroudLinearyLayout roundRedpacket;
    @BindView(R2.id.round_electronic)
    RoundBackgroudLinearyLayout roundElectronic;
    @BindView(R2.id.round_chess)
    RoundBackgroudLinearyLayout roundChess;
    @BindView(R2.id.round_leisure)
    RoundBackgroudLinearyLayout roundLeisure;
    private View mCurrentSelectedView;
    private GameRedPacketFragment redPacketFragment;
    private GameElectronicFragment electronicFragment;

    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment ();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerAdNoticeComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate ( R.layout.fragment_game, container, false );
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTitle.setText ( "游戏" );
        redPacketFragment = new GameRedPacketFragment ();
        getFragmentManager ().beginTransaction ().add ( R.id.frameLayout_game,redPacketFragment ).commit ();
        initBanner ();
        mCurrentSelectedView = roundRedpacket;
        mCurrentSelectedView.setVisibility ( View.VISIBLE );
    }

    private void initBanner() {
        banner.setAdapter ( new BGABanner.Adapter <ImageView, BannerEntity> () {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, BannerEntity model, int position) {
                ImageLoader.displayImage ( mContext, model.getImageUrl (), itemView );
            }
        } );
        banner.setDelegate ( new BGABanner.Delegate <ImageView, BannerEntity> () {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable BannerEntity model, int position) {

            }
        } );
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener () {
        @Override
        public void onClick(View v) {
            showSwitchShortcutPopupWindow ( v );
        }
    };

    @OnClick({R2.id.ll_game_redpacket, R2.id.ll_game_electronic, R2.id.ll_game_chess, R2.id.ll_game_leisure})
    public void onViewClicked(View view) {
        int i = view.getId ();
        mCurrentSelectedView.setVisibility ( View.GONE );
        if (i == R.id.ll_game_redpacket) {
            //红包
            mCurrentSelectedView = roundRedpacket;
            if (electronicFragment!=null){
                getFragmentManager ().beginTransaction ().show ( redPacketFragment ).hide ( electronicFragment ).commit ();
            }
        } else if (i == R.id.ll_game_electronic) {
            //棋牌
            mCurrentSelectedView = roundElectronic;
            if (electronicFragment==null){
                electronicFragment = new GameElectronicFragment ();
                getFragmentManager ().beginTransaction ().add ( R.id.frameLayout_game,electronicFragment ).hide ( redPacketFragment ).commit ();
            }else{
                getFragmentManager ().beginTransaction ().show ( electronicFragment ).hide ( redPacketFragment ).commit ();
            }
        } else if (i == R.id.ll_game_chess) {
            //彩票
            ToastUtils.showShort ( "正在开发中" );
        } else if (i == R.id.ll_game_leisure) {
            //捕鱼
            ToastUtils.showShort ( "余额不足，请您在充值后再体验游戏！" );
        }
        mCurrentSelectedView.setVisibility ( View.VISIBLE );
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible ();
        mPresenter.initDatas ();
    }

    @Override
    public void setAdBannerInfo(AdBannerInfo adBannerInfo) {
        List <BannerEntity> bannerEntities = adBannerInfo.getRoomAdBanners ();
        if (null != bannerEntities)
            banner.setData ( adBannerInfo.getRoomAdBanners (), null );

        List <String> notices = adBannerInfo.getNotices ();
        StringBuilder sbNotices = new StringBuilder ();
        if (notices != null && notices.size () > 0) {
            for (String notice : notices) {
                sbNotices.append ( notice );
                sbNotices.append ( "   " );
            }
        }
        tvNotices.setText ( sbNotices.toString () );
        tvNotices.setSelected ( true );
    }

    private SwitchShortcutPopupWindow mShortcutPopupWindow;

    private void showSwitchShortcutPopupWindow(View v) {
        if (null == mShortcutPopupWindow) {
            mShortcutPopupWindow = new SwitchShortcutPopupWindow ( mContext );
        }
        mShortcutPopupWindow.openPopWindow ( v );
    }

    @Override
    public void setData(@Nullable Object data) {
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull ( message );
        ArmsUtils.snackbarText ( message );
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull ( intent );
        ArmsUtils.startActivity ( intent );
    }

    @Override
    public void killMyself() {

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
}
