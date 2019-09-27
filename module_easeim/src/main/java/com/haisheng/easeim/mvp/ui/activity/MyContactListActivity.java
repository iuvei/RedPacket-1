package com.haisheng.easeim.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.di.component.DaggerMyContactListComponent;
import com.haisheng.easeim.mvp.contract.MyContactListContract;
import com.haisheng.easeim.mvp.presenter.MyContactListPresenter;
import com.haisheng.easeim.mvp.ui.adapter.MyContackMultipleListViewAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.armscomponent.commonres.adapter.MyMultipleListViewBaseAdapter;
import me.jessyan.armscomponent.commonres.view.SideBar;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/27/2019 11:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MyContactListActivity extends BaseActivity <MyContactListPresenter> implements MyContactListContract.View {

    @BindView(R2.id.listview)
    ListView listView;
    @BindView(R2.id.letterView)
    SideBar sidebar;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_contack_num)
    TextView tvContackNum;
    @BindView(R2.id.iv_right)
    ImageView ivRight;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.iv_close)
    ImageView ivClear;
    @BindView(R2.id.et_serach)
    EditText etSearch;
    Unbinder unbinder;
    private MyContackMultipleListViewAdapter adapter;
    private List <UserInfo> allContact = new ArrayList <> (  );
    private List <UserInfo> searchContact = new ArrayList <> (  );
    private List <UserInfo> checkContactList = new ArrayList <> (  );
    private String roomID;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyContactListComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_contact_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    public static void star(Context context, long roomID){
        Intent intent = new Intent ( context,MyContactListActivity.class );
        intent.putExtra ( "roomid",roomID+"" );
        context.startActivity ( intent );
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        roomID = getIntent ().getStringExtra ( "roomid" );
        initRecyclerView ();
        mPresenter.getContactList ( 1 );
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "我的好友" );
        ivRight.setImageResource ( R.drawable.ic_talk_add );
        ivRight.setVisibility ( View.VISIBLE );
        setListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
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

        etSearch.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String search = etSearch.getText ().toString ().trim ();
                if (search.length ()>0){
                    ivClear.setVisibility ( View.VISIBLE );
                    for (int j=0;j<allContact.size ();j++){
                        if (allContact.get ( j ).getNickname ().contains ( search )){
                            searchContact.add ( allContact.get ( j ) );
                        }
                    }
                    adapter.setData ( searchContact );
                }else{
                    ivClear.setVisibility ( View.INVISIBLE );
                    adapter.setData ( allContact );
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );

        ivClear.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                etSearch.setText ( "" );
            }
        } );

        adapter.setOnListItemCheckedChangeListener ( new MyMultipleListViewBaseAdapter.OnListItemCheckedChangeListener <UserInfo> () {

            @Override
            public void onListItemCheckedChangeListener(boolean isChecked, int position, UserInfo phoneContacts, List <UserInfo> list, List <Integer> choosePositionLists) {
                //checkContactList = list;
                checkContactList = list;
            }

            @Override
            public void onItemAllCheckedListener(List <UserInfo> list, List <Integer> choosePositionLists) {
                Log.e ( "tag", "onItemAllCheckedListener=====" );
                for (int i = 0; i < list.size (); i++) {
                    Log.e ( "tag", "allchooseData = " + list.get ( i ) );
                }
            }

            @Override
            public void onItemAllNotCheckedListener() {
                Log.e ( "tag", "onItemAllNotCheckedListener=====" );
            }
        } );
    }


    //初始化RecyclerView
    private void initRecyclerView() {
        adapter = new MyContackMultipleListViewAdapter ( allContact,this );
        listView.setAdapter ( adapter );
    }

    @Override
    public void getContactsListSuccess(List <UserInfo> list) {
        if (list==null || list.size ()<0){
            return;
        }
        Collections.sort ( list, new Comparator <UserInfo> () {
            @Override
            public int compare(UserInfo userInfo, UserInfo t1) {
                return userInfo.getSpelling ().compareTo(t1.getSpelling ());
            }
        } );
        adapter.setData ( list );
        tvContackNum.setText ( list.size ()+"位联系人" );
    }

    @Override
    public void getContactsListFail() {

    }

    @Override
    public void invitedSuccess() {
        ToastUtils.showShort ( "邀请成功" );
        finish ();
    }

    @Override
    public void invitedFail() {
        ToastUtils.showShort ( "邀请失败" );
    }

    @OnClick({ R2.id.iv_right,R2.id.iv_back})
    public void onViewClicked(View view) {
        if (view.getId () == R.id.iv_right){
            //邀请好友
            if (checkContactList==null || checkContactList.size ()<=0){
                ToastUtils.showShort ( "请先选择好友" );
                return;
            }
            StringBuilder sb = new StringBuilder (  );
            for (int i = 0;i<checkContactList.size ();i++){
                sb.append ( checkContactList.get ( i ).getId ()+"," );
            }
            String friendid = sb.substring ( 0,sb.length ()-2 );
            mPresenter.invitedJoinRoom ( roomID, friendid);
        }else if (view.getId () ==R.id.iv_back){
            finish ();
        }
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
}
