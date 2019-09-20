package com.ooo.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerCommissionListComponent;
import com.ooo.main.mvp.contract.CommissionListContract;
import com.ooo.main.mvp.model.entity.RankingBean;
import com.ooo.main.mvp.presenter.CommissionListPresenter;
import com.ooo.main.mvp.ui.fragment.DayListFragment;
import com.ooo.main.mvp.ui.fragment.WeekListFragment;
import com.ooo.main.mvp.ui.fragment.YesterDayListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 15:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 * 佣金排行榜
 */
public class CommissionListActivity extends BaseActivity <CommissionListPresenter> implements CommissionListContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R2.id.viewpager)
    ViewPager viewpager;
    List <Fragment> fragments = new ArrayList <> ();
    List<String> titles = new ArrayList<>();
    DayListFragment dayListFragment = new DayListFragment();
    YesterDayListFragment yesterDayListFragment = new YesterDayListFragment();
    WeekListFragment weekListFragment = new WeekListFragment();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommissionListComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_commission_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "佣金排行榜" );
        // 添加 tab item
        titles.add("今日排行榜");
        titles.add("昨日排行榜");
        titles.add("周排行榜");
        fragments.add(dayListFragment);
        fragments.add(yesterDayListFragment);
        fragments.add(weekListFragment );
        mPresenter.getRankingList ( "" );
        viewpager.setAdapter(new FragmentStatePagerAdapter (getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {

                return titles.get(position);
            }
        });
        viewpager.setOffscreenPageLimit ( titles.size () );
        tabLayout.setupWithViewPager(viewpager);
        setListener();
    }

    private void setListener() {
        tabLayout.addOnTabSelectedListener ( new TabLayout.OnTabSelectedListener () {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        } );
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
        finish ();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        // TODO: add setContentView(...) invocation
        ButterKnife.bind ( this );
    }

    @OnClick(R2.id.iv_back)
    public void onViewClicked() {
        finish ();
    }

    @Override
    public void getRankingListSuccess(RankingBean.ResultBean result) {
        if (result!=null){
            dayListFragment.setCommissionList (result.getToday ());
            yesterDayListFragment.setCommissionList (result.getYesterday ());
            weekListFragment.setCommissionList (result.getWeek ());
        }
    }

    @Override
    public void getRankingListFail() {

    }
}
