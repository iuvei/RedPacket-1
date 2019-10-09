package com.haisheng.easeim.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.di.component.DaggerUserListComponent;
import com.haisheng.easeim.mvp.contract.UserListContract;
import com.haisheng.easeim.mvp.model.entity.GroupListBean;
import com.haisheng.easeim.mvp.presenter.UserListPresenter;
import com.haisheng.easeim.mvp.ui.adapter.GroupUserListAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.view.SideBar;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/19/2019 15:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UserListActivity extends BaseSupportActivity <UserListPresenter> implements UserListContract.View {

    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.et_serach)
    EditText etSerach;
    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.listview)
    ListView listview;
    @BindView(R2.id.sidebar)
    SideBar sidebar;

    private GroupUserListAdapter adapter;
    private ArrayList<GroupListBean.ResultBean> contactList;
    private List <GroupListBean.ResultBean> searchContact = new ArrayList <> (  );

    public static void start(Context context, ArrayList <GroupListBean.ResultBean> userInfos) {
        Intent intent = new Intent ( context, UserListActivity.class );
        Bundle bundle = new Bundle ();
        bundle.putSerializable ( "userlist", userInfos );
        intent.putExtras ( bundle );
        context.startActivity ( intent );
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserListComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_recycler; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "群成员" );
        initRecyclerView ();
        Bundle bundle = getIntent ().getExtras ();
        if (null != bundle) {
            contactList = (ArrayList <GroupListBean.ResultBean>) bundle.getSerializable ( "userlist" );
            if (contactList!=null && contactList.size ()>0) {
                Collections.sort ( contactList, new Comparator <GroupListBean.ResultBean> () {
                    @Override
                    public int compare(GroupListBean.ResultBean userInfo, GroupListBean.ResultBean t1) {
                        return userInfo.getSpelling ().compareTo ( t1.getSpelling () );
                    }
                } );
                //加载所有好友
                adapter.setData ( contactList );
            }
        }

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
                    listview.setSelection(position);
                }
            }
        } );

        etSerach.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String search = etSerach.getText ().toString ().trim ();
                if (search.length ()>0){
                    searchContact.clear ();
                    ivClose.setVisibility ( View.VISIBLE );
                    for (int j=0;j<contactList.size ();j++){
                        if (contactList.get ( j ).getNickname ().contains ( search )){
                            searchContact.add ( contactList.get ( j ) );
                        }
                    }
                    adapter.setData ( searchContact );
                }else{
                    ivClose.setVisibility ( View.INVISIBLE );
                    adapter.setData ( contactList );
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );

        ivClose.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                etSerach.setText ( "" );
            }
        } );

    }

    //初始化RecyclerView
    private void initRecyclerView() {

        contactList = new ArrayList <> (  );
        adapter = new GroupUserListAdapter ( contactList );
        listview.setAdapter ( adapter );
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

    @OnClick({R2.id.iv_back, R2.id.iv_close})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.iv_close) {
            etSerach.setText ( "" );
        }
    }
}
