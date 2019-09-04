package com.haisheng.easeim.mvp.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.di.component.DaggerMessageComponent;
import com.haisheng.easeim.di.module.MessageModule;
import com.haisheng.easeim.mvp.contract.MessageContract;
import com.haisheng.easeim.mvp.presenter.MessagePresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import me.jessyan.armscomponent.commonres.utils.ProgressDialogUtils;
import me.jessyan.armscomponent.commonres.view.StatusBarHeightView;
import me.jessyan.armscomponent.commonsdk.adapter.FragmentAdapter;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportFragment;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.entity.BannerEntity;
import me.jessyan.armscomponent.commonres.utils.ImageLoader;

import static com.jess.arms.utils.Preconditions.checkNotNull;

@Route(path = RouterHub.IM_MESSAGEFRAGMENT)
public class MessageFragment extends BaseSupportFragment<MessagePresenter> implements MessageContract.View {

    @BindView(R2.id.viewStatusBar)
    StatusBarHeightView viewStatusBar;
    @BindView(R2.id.tab_im)
    SegmentTabLayout tabIm;
    @BindView(R2.id.convenientBanner)
    BGABanner convenientBanner;
    @BindView(R2.id.viewBanner)
    RelativeLayout viewBanner;
    @BindView(R2.id.viewPager)
    ViewPager viewPager;

    private String[] mTitles = {"消息", "通话"};
    private ProgressDialogUtils progressDialogUtils;

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMessageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .messageModule(new MessageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        viewStatusBar.setBackgroundColor(ContextCompat.getColor(mContext,R.color.theme_color));
        tabIm.setTabData(mTitles);
        tabIm.setOnTabSelectListener(mOnTabSelectListener);
        initBanner();
        initFragments();
    }

    private void initBanner(){
        List<BannerEntity> bannerEntities=new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            BannerEntity bannerEntity = new BannerEntity();
            bannerEntity.setImageUrl("http://bgashare.bingoogolapple.cn/banner/imgs/"+i+".png");
            bannerEntity.setTip("");
            bannerEntity.setUrl("www.baidu.com");
            bannerEntities.add(bannerEntity);
        }
        convenientBanner.setAutoPlayAble(bannerEntities.size() > 1);
        convenientBanner.setAdapter(new BGABanner.Adapter<ImageView,BannerEntity>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable BannerEntity model, int position) {
                ImageLoader.displayImage(mContext,model.getImageUrl(),R.mipmap.home_banner_hover,itemView);
            }
        });
        convenientBanner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, @Nullable Object model, int position) {
                showMessage("position:"+position);
            }
        });
        convenientBanner.setData(bannerEntities,null);
    }

    private void initFragments() {
        ConversationListFragment fragment1 = ConversationListFragment.newInstance(IMConstants.CONVERSATION_ALL);
        CallListFragment fragment2 =    CallListFragment.newInstance();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    private OnTabSelectListener mOnTabSelectListener = new OnTabSelectListener(){

        @Override
        public void onTabSelect(int position) {
            viewPager.setCurrentItem(position);
        }

        @Override
        public void onTabReselect(int position) {

        }
    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            tabIm.setCurrentTab(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

    }

    private void showProgress(final boolean show) {
        if (progressDialogUtils == null) {
            progressDialogUtils = ProgressDialogUtils.getInstance(mContext);
            progressDialogUtils.setMessage(getString(R.string.public_loading));
        }
        if (show) {
            progressDialogUtils.show();
        } else {
            progressDialogUtils.dismiss();
        }

    }

    @Override
    public void showLoading() {
        showProgress(true);
    }

    @Override
    public void hideLoading() {
        showProgress(false);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ToastUtils.showShort(message);
//        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @OnClick({R2.id.imgClearAll, R2.id.imgReadAll, R2.id.imgClose})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.imgClearAll) {
            clearAllConversations();

        } else if (i == R.id.imgReadAll) {
            markAllConversationsAsRead();

        } else if (i == R.id.imgClose) {
            viewBanner.setVisibility(View.GONE);

        }
    }

    private void clearAllConversations(){
        new AlertDialog.Builder(mContext)
                .setMessage(R.string.clear_all_conversations)
                .setNegativeButton(R.string.public_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.public_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.clearAllConversations();
                    }
                }).show();
    }

    private void markAllConversationsAsRead(){
        new AlertDialog.Builder(mContext)
            .setMessage(R.string.mark_all_conversations_as_read)
            .setNegativeButton(R.string.public_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .setPositiveButton(R.string.public_confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mPresenter.markAllConversationsAsRead();
                }
            }).show();
    }
}
