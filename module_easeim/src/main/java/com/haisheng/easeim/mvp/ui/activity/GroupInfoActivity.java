package com.haisheng.easeim.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.app.IMHelper;
import com.haisheng.easeim.app.IMModel;
import com.haisheng.easeim.di.component.DaggerGroupInfoComponent;
import com.haisheng.easeim.mvp.contract.GroupInfoContract;
import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.haisheng.easeim.mvp.presenter.GroupInfoPresenter;
import com.haisheng.easeim.mvp.ui.adapter.UserGridAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.ui.LongImageActivity;
import me.jessyan.armscomponent.commonres.utils.ProgressDialogUtils;
import me.jessyan.armscomponent.commonres.view.SwitchButton;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.utils.ARouterUtils;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/01/2019 16:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class GroupInfoActivity extends BaseSupportActivity <GroupInfoPresenter> implements GroupInfoContract.View {

    @BindView(R2.id.rv_user)
    RecyclerView rvUser;
    @BindView(R2.id.tv_user_number)
    TextView tvUserNumber;
    @BindView(R2.id.tv_group_name)
    TextView tvGroupName;
    @BindView(R2.id.tv_group_notice)
    TextView tvGroupNotice;
    @BindView(R2.id.tv_group_rules)
    TextView tvGroupRules;
    @BindView(R2.id.tv_game_rules)
    TextView tvGameRules;
    @BindView(R2.id.switch_voice_notify)
    SwitchButton switchVoiceNotify;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    UserGridAdapter mAdapter;
    TextView tvTitle;

    private IMModel mIMModel;
    private List <String> mDisabledGroupIds = new ArrayList <> ();
    private ChatRoomBean mChatRoomBean;
    private ProgressDialogUtils progressDialogUtils;

    public static void start(Context context, ChatRoomBean chatRoomInfo) {
        Intent intent = new Intent ( context, GroupInfoActivity.class );
        Bundle bundle = new Bundle ();
        bundle.putSerializable ( "chatRoomInfo", chatRoomInfo );
        intent.putExtras ( bundle );
        context.startActivity ( intent );
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGroupInfoComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_group_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle = findViewById ( R.id.tv_title );
        tvTitle.setText ( "群信息" );
        Bundle bundle = getIntent ().getExtras ();
        if (null != bundle) {
            mChatRoomBean = (ChatRoomBean) bundle.getSerializable ( "chatRoomInfo" );
        }
        mIMModel = IMHelper.getInstance ().getModel ();

        List <String> disabledGroups = mIMModel.getDisabledGroups ();
        if (null != disabledGroups)
            mDisabledGroupIds.addAll ( mIMModel.getDisabledGroups () );
        boolean isDisabled = !mDisabledGroupIds.contains ( mChatRoomBean.getHxId () );
        switchVoiceNotify.setChecked ( !isDisabled );

        switchVoiceNotify.setOnCheckedChangeListener ( new SwitchButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    if (null == mDisabledGroupIds) {
                        mDisabledGroupIds = new ArrayList <> ();
                    }
                    mDisabledGroupIds.add ( mChatRoomBean.getHxId () );
                } else {
                    if (null != mDisabledGroupIds) {
                        mDisabledGroupIds.remove ( mChatRoomBean.getHxId () );
                    }
                }
                mIMModel.setDisabledGroups ( mDisabledGroupIds );
            }
        } );

        initRecyclerView ();
        if (null != mChatRoomBean) {
            setChatRoomInfo ( mChatRoomBean );
        }
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView ( rvUser, mLayoutManager );
        mAdapter.setOnItemClickListener ( new BaseQuickAdapter.OnItemClickListener () {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        } );
        mAdapter.setAddGroupContactListener ( new UserGridAdapter.AddGroupContactListener () {
            @Override
            public void onAddGroupContact() {
                MyContactListActivity.star ( GroupInfoActivity.this,mChatRoomBean.getId () );
            }
        } );
        rvUser.setAdapter ( mAdapter );

        tvUserNumber.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                UserListActivity.start ( mContext, mChatRoomBean.getId () );
            }
        } );
    }


    @OnClick({R2.id.ll_group_rules, R2.id.ll_game_rules, R2.id.btn_delect_exit})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.ll_group_rules) {
            //群规则
            LongImageActivity.start ( mContext, mChatRoomBean.getGroupRulesImgUrl () );

        } else if (i == R.id.ll_game_rules) {
            //群公告
            LongImageActivity.start ( mContext, mChatRoomBean.getGameRulesImgUrl () );

        } else if (i == R.id.btn_delect_exit) {
            mPresenter.quitRoom ( mChatRoomBean.getId () );
        }
    }

//    @OnClick({R2.id.ll_group_rules, R2.id.ll_game_rules, R2.id.btn_delect_exit})
//    public void onViewClicked(View view) {
//        int i = view.getId();
//        if (i == R.id.ll_group_rules) {
//            LongImageActivity.start(mContext,mChatRoomBean.getGroupRulesImgUrl());
//
//        } else if (i == R.id.ll_game_rules) {
//            LongImageActivity.start(mContext,mChatRoomBean.getGameRulesImgUrl());
//
//        } else if (i == R.id.btn_delect_exit) {
//            mPresenter.quitRoom(mChatRoomBean.getId());
//        }
//    }

    @Override
    public void setChatRoomInfo(ChatRoomBean chatRoomInfo) {
        List <UserInfo> userInfos = chatRoomInfo.getUserInfos ();
        for (int i = 0;i<userInfos.size ();i++){
            if (userInfos.get ( i ).getNickname ().equals ( "群主" ) && TextUtils.isEmpty ( userInfos.get ( i ).getHxId () )){
                userInfos.remove ( i );
                break;
            }
        }
        if (userInfos.size () > 14) {
            userInfos = userInfos.subList ( 0, 14 );
        }
        tvUserNumber.setText ( String.format ( "全部群成员（%d）", userInfos.size () ) );
        userInfos.add ( new UserInfo () );
        mAdapter.setNewData ( userInfos );
        tvGroupName.setText ( chatRoomInfo.getName () );
        tvGroupNotice.setText ( chatRoomInfo.getNotice () );
        tvGameRules.setText ( chatRoomInfo.getGameRules () );
        tvGroupRules.setText ( chatRoomInfo.getGroupRules () );
    }

    @Override
    public void quitSuccessful() {
        ARouterUtils.navigation ( mContext, RouterHub.APP_MAINACTIVITY );
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    private void showProgress(final boolean show) {
        if (progressDialogUtils == null) {
            progressDialogUtils = ProgressDialogUtils.getInstance ( mContext );
            progressDialogUtils.setMessage ( getString ( R.string.public_loading ) );
        }
        if (show) {
            progressDialogUtils.show ();
        } else {
            progressDialogUtils.dismiss ();
        }
    }

    @Override
    public void showLoading() {
        showProgress ( true );
    }

    @Override
    public void hideLoading() {
        showProgress ( false );
    }

    @Override
    public void showMessage(@NonNull String message) {
//        checkNotNull(message);
        ToastUtils.showShort ( message );
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
    public void onViewBackClicked() {
        finish ();
    }
}
