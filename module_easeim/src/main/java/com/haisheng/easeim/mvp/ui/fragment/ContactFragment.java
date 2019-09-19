package com.haisheng.easeim.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.di.component.DaggerContactComponent;
import com.haisheng.easeim.mvp.contract.ContactContract;
import com.haisheng.easeim.mvp.presenter.ContactPresenter;
import com.haisheng.easeim.mvp.ui.adapter.ContactListAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.armscomponent.commonres.view.SideBar;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportFragment;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/02/2019 18:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

@Route(path = RouterHub.IM_CONTACTFRAGMENT)
public class ContactFragment extends BaseSupportFragment <ContactPresenter> implements ContactContract.View {

    @BindView(R2.id.listview)
    ListView listView;
    @BindView(R2.id.sidebar)
    SideBar sidebar;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_contack_num)
    TextView tvContackNum;
    @BindView(R2.id.iv_right)
    ImageView ivRight;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    Unbinder unbinder;
    private ContactListAdapter adapter;
    private List<UserInfo> contactList;

    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment ();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerContactComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate ( R.layout.fragment_contact, container, false );
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView ();
        mPresenter.getContactList ( 1 );
        StatusBarUtils.setTranslucentStatus ( getActivity () );
        StatusBarUtils.setStatusBarDarkTheme ( getActivity (), true );
        ivBack.setVisibility ( View.GONE );
        tvTitle.setText ( "通讯录" );
        ivRight.setImageResource ( R.mipmap.alipay );
        ivRight.setVisibility ( View.VISIBLE );
        setListener();

    }

    private void setListener() {
        // 右侧sideBar监听
        sidebar.setOnTouchingLetterChangedListener ( new SideBar.OnTouchingLetterChangedListener () {
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s);
                if (position != -1) {
                    listView.setSelection(position);
                }
            }
        } );
    }


    //初始化RecyclerView
    private void initRecyclerView() {
        contactList = new ArrayList<> (  );
        UserInfo newFriend = new UserInfo ();
        newFriend.setNickname ( "新的好友" );
        contactList.add ( newFriend );
        UserInfo groupTalking = new UserInfo ();
        groupTalking.setNickname ( "群聊" );
        contactList.add ( groupTalking );
        adapter = new ContactListAdapter ( contactList );
        listView.setAdapter ( adapter );
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
    public void getContactsListFail() {

    }

    @Override
    public void getContactsListSuccess(List <UserInfo> list) {
       Collections.sort ( list, new Comparator <UserInfo> () {
           @Override
           public int compare(UserInfo userInfo, UserInfo t1) {
               return userInfo.getSpelling ().compareTo(t1.getSpelling ());
           }
       } );
        //加载所有好友
        adapter.addData ( list );
        tvContackNum.setText ( list.size ()+"位联系人" );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView ( inflater, container, savedInstanceState );
        unbinder = ButterKnife.bind ( this, rootView );
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        unbinder.unbind ();
    }

    @OnClick({ R2.id.iv_right})
    public void onViewClicked() {
        //添加好友
        ARouter.getInstance ().build ( RouterHub.ADD_FRIEND_ACTIVITY ).navigation ();
    }
}
