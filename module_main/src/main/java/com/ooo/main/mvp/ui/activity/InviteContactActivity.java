package com.ooo.main.mvp.ui.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerInviteContactComponent;
import com.ooo.main.mvp.contract.InviteContactContract;
import com.ooo.main.mvp.model.entity.PhoneContacts;
import com.ooo.main.mvp.presenter.InviteContactPresenter;
import com.ooo.main.mvp.ui.adapter.MyMultipleListViewAdapter;
import me.jessyan.armscomponent.commonres.adapter.MyMultipleListViewBaseAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.ProgressDialogUtils;
import me.jessyan.armscomponent.commonres.view.SideBar;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/12/2019 11:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class InviteContactActivity extends BaseActivity <InviteContactPresenter> implements InviteContactContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_right)
    TextView tvRight;
    @BindView(R2.id.listView)
    ListView listView;
    @BindView(R2.id.letterView)
    SideBar letterView;
    private MyMultipleListViewAdapter adapter;
    private List <PhoneContacts> contacts = new ArrayList <> ();
    private ProgressDialogUtils progressDialogUtils;
    private List <PhoneContacts> checkContactList = new ArrayList <> (  );

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerInviteContactComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_invite_contact; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "邀请手机联系人" );
        tvRight.setText ( "邀请" );
        tvRight.setVisibility ( View.VISIBLE );
        requestPermissions ();
    }

    private void setListener() {
        // 右侧sideBar监听
        letterView.setOnTouchingLetterChangedListener ( new SideBar.OnTouchingLetterChangedListener () {
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s);
                if (position != -1) {
                    listView.setSelection(position);
                }
            }
        } );
        adapter.setOnListItemCheckedChangeListener ( new MyMultipleListViewBaseAdapter.OnListItemCheckedChangeListener <PhoneContacts> () {

            @Override
            public void onListItemCheckedChangeListener(boolean isChecked, int position, PhoneContacts phoneContacts, List <PhoneContacts> list, List <Integer> choosePositionLists) {
                checkContactList = list;
            }

            @Override
            public void onItemAllCheckedListener(List <PhoneContacts> list, List <Integer> choosePositionLists) {
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

    private void requestPermissions() {
        RxPermissions rxPermissions = new RxPermissions ( this );
        rxPermissions
                .request ( Manifest.permission.READ_CONTACTS )
                .subscribe ( granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        Log.e ( "tag", "granted" );
                        showProgress(true);
                        new Thread ( new Runnable () {
                            @Override
                            public void run() {
                                try {
                                    getContact ();
                                } catch (Exception e) {
                                    e.printStackTrace ();
                                    showProgress(false);
                                }
                            }
                        } ).start ();
                    } else {
                        // Oups permission denied
                        ToastUtils.showShort ( "没有打开权限" );
                    }
                } );
    }

    //初始化RecyclerView
    private void initListView() {
        Collections.sort ( contacts );
        adapter = new MyMultipleListViewAdapter ( contacts, this );
        adapter.setChooseMode ( MyMultipleListViewBaseAdapter.CHOOSE_MODE.MULTI_MODE );
        listView.setAdapter ( adapter );
    }

    //查询所有联系人的姓名，电话
    public void getContact() throws Exception {
        showProgress(true);
        Uri uri = Uri.parse ( "content://com.android.contacts/contacts" );
        ContentResolver resolver = getApplicationContext ().getContentResolver ();
        Cursor cursor = resolver.query ( uri, new String[]{"_id"}, null, null, null );
        while (cursor.moveToNext ()) {
            int contractID = cursor.getInt ( 0 );
            PhoneContacts phoneContacts = new PhoneContacts ();
            uri = Uri.parse ( "content://com.android.contacts/contacts/" + contractID + "/data" );
            Cursor cursor1 = resolver.query ( uri, new String[]{"mimetype", "data1", "data2"}, null, null, null );
            while (cursor1.moveToNext ()) {
                String data1 = cursor1.getString ( cursor1.getColumnIndex ( "data1" ) );
                String mimeType = cursor1.getString ( cursor1.getColumnIndex ( "mimetype" ) );
                if ("vnd.android.cursor.item/name".equals ( mimeType )) { //是姓名
                    phoneContacts.setName ( data1 );
                } else if ("vnd.android.cursor.item/phone_v2".equals ( mimeType )) { //手机
                    phoneContacts.setTeleNumber ( data1 );
                }
            }
            cursor1.close ();
            contacts.add ( phoneContacts );
        }
        cursor.close ();
        runOnUiThread ( new Runnable () {
            @Override
            public void run() {
                showProgress(false);
                initListView ();
                Log.e ( "Tag", "contacts.size=" + contacts.size () );
                //此时已在主线程中，更新UI
                adapter.notifyDataSetChanged ();
                setListener ();
            }
        } );
    }

    private void showProgress(final boolean show) {
        if (progressDialogUtils == null) {
            progressDialogUtils = ProgressDialogUtils.getInstance(this);
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

    @OnClick({R2.id.iv_back, R2.id.tv_right})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.tv_right) {
            //邀请联系人
            if (checkContactList==null ||checkContactList.size ()<0){
                ToastUtils.showShort ( "请先选择邀请的联系人" );
                return;
            }
            StringBuffer sb = new StringBuffer (  );
            for (int j=0;j<checkContactList.size ();j++){
                sb.append ( checkContactList.get ( j ).getTeleNumber () +",");
            }
            String contact = sb.substring ( 0,sb.length ()-1 );
            Log.e ( "tag","contact="+contact );
            mPresenter.inviteContact ( contact );
        }
    }

    @Override
    public void inviteContactSuccess() {
        ToastUtils.showShort ( "邀请成功" );
        finish ();
    }

    @Override
    public void inviteContactFail() {
    }
}
