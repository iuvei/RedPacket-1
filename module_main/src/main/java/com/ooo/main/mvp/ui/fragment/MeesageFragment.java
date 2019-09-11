package com.ooo.main.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
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
import cn.bingoogolapple.bgabanner.BGABanner;
import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportFragment;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.entity.AdBannerInfo;
import me.jessyan.armscomponent.commonsdk.entity.BannerEntity;

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
public class MeesageFragment extends BaseSupportFragment<AdNoticePresenter> implements AdNoticeContract.View {

    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.btn_switch_shortcut)
    ImageButton btnSwitchShortcut;
    @BindView(R2.id.banner)
    BGABanner banner;
    @BindView(R2.id.tv_notices)
    TextView tvNotices;
    @BindView(R2.id.fl_content)
    FrameLayout flContent;

    public static MeesageFragment newInstance() {
        MeesageFragment fragment = new MeesageFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerAdNoticeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meesage, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTitle.setText("消息");

        initBanner();
        btnSwitchShortcut.setOnClickListener(mOnClickListener);

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.IM.TYPE,Constants.IM.TYPE_MESSAGE);
        BaseSupportFragment fragment =(BaseSupportFragment) ARouter.getInstance()
                .build(RouterHub.IM_ROOMLISTFRAGMENT)
                .with(bundle).navigation();

        loadRootFragment(R.id.fl_content,fragment);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showSwitchShortcutPopupWindow(v);
        }
    };

    private void initBanner() {
        banner.setAdapter(new BGABanner.Adapter<ImageView, BannerEntity>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, BannerEntity model, int position) {
                ImageLoader.displayImage(mContext, model.getImageUrl(), itemView);
            }
        });
        banner.setDelegate(new BGABanner.Delegate<ImageView, BannerEntity>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable BannerEntity model, int position) {

            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.initDatas();
    }

    @Override
    public void setAdBannerInfo(AdBannerInfo adBannerInfo) {
        List<BannerEntity> bannerEntities = adBannerInfo.getMsgAdBanners();
        if (null != bannerEntities)
            banner.setData(adBannerInfo.getRoomAdBanners(), null);

        List<String> notices = adBannerInfo.getNotices();
        StringBuilder sbNotices = new StringBuilder();
        if (notices != null && notices.size() > 0) {
            for (String notice : notices) {
                sbNotices.append(notice);
                sbNotices.append("   ");
            }
        }
        tvNotices.setText(sbNotices.toString());
        tvNotices.setSelected(true);
    }

    private SwitchShortcutPopupWindow mShortcutPopupWindow;
    private void showSwitchShortcutPopupWindow(View v){
        if(null == mShortcutPopupWindow){
            mShortcutPopupWindow = new SwitchShortcutPopupWindow(mContext);
        }
        mShortcutPopupWindow.openPopWindow(v);
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
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

}
