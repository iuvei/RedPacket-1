package com.ooo.main.mvp.ui.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ooo.main.R;
import com.ooo.main.R2;
import com.ooo.main.di.component.DaggerContactFriendComponent;
import com.ooo.main.mvp.contract.ContactFriendContract;
import com.ooo.main.mvp.model.entity.ContactForMobileBean;
import com.ooo.main.mvp.presenter.ContactFriendPresenter;
import com.ooo.main.mvp.ui.adapter.ContactForMobileListAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
 * Created by MVPArmsTemplate on 09/18/2019 19:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ContactFriendActivity extends BaseActivity <ContactFriendPresenter> implements ContactFriendContract.View {

    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.et_search)
    EditText etSearch;
    @BindView(R2.id.iv_clear)
    ImageView ivClear;
    @BindView(R2.id.lv_contact)
    ListView lvContact;
    @BindView(R2.id.sidebar)
    SideBar sidebar;
    private ProgressDialogUtils progressDialogUtils;
    private ContactForMobileListAdapter adapter;
    private List <ContactForMobileBean.ResultBean> contactList = new ArrayList <> (  );//联系人列表
    private List <ContactForMobileBean.ResultBean> searchContact = new ArrayList <> (  ); //搜索的列表

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerContactFriendComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_contact_friend; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "通讯录好友" );
        initRecyclerView ();
        setListener ();
        requestPermissions ();
    }

    private void requestPermissions() {
        RxPermissions rxPermissions = new RxPermissions ( this );
        rxPermissions
                .request ( Manifest.permission.READ_CONTACTS )
                .subscribe ( granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        Log.e ( "tag", "granted" );
                        showProgress ( true );
                        new Thread ( new Runnable () {
                            @Override
                            public void run() {
                                try {
                                    getContact ();
                                } catch (Exception e) {
                                    e.printStackTrace ();
                                    showProgress ( false );
                                }
                            }
                        } ).start ();
                    } else {
                        // Oups permission denied
                        ToastUtils.showShort ( "没有打开权限" );
                    }
                } );
    }


    //查询所有联系人的姓名，电话
    public void getContact() throws Exception {
        showProgress ( true );
        Uri uri = Uri.parse ( "content://com.android.contacts/contacts" );
        ContentResolver resolver = getApplicationContext ().getContentResolver ();
        Cursor cursor = resolver.query ( uri, new String[]{"_id"}, null, null, null );
        StringBuffer sb = new StringBuffer ();
        while (cursor.moveToNext ()) {
            int contractID = cursor.getInt ( 0 );
            uri = Uri.parse ( "content://com.android.contacts/contacts/" + contractID + "/data" );
            Cursor cursor1 = resolver.query ( uri, new String[]{"mimetype", "data1", "data2"}, null, null, null );
            while (cursor1.moveToNext ()) {
                String data1 = cursor1.getString ( cursor1.getColumnIndex ( "data1" ) );
                String mimeType = cursor1.getString ( cursor1.getColumnIndex ( "mimetype" ) );
                if ("vnd.android.cursor.item/phone_v2".equals ( mimeType )) { //手机
                    sb.append ( data1 + "," );
                }
            }
            cursor1.close ();
        }
        cursor.close ();
        String allContact = sb.substring ( 0, sb.length () - 1 );
        String contacts = allContact.replaceAll("\\s*", ""); //去除所有空格，包括首尾、中间//去除所有的空格
        mPresenter.getContactForMobile ( contacts );
        showProgress ( false );
    }

    private void showProgress(final boolean show) {
        if (progressDialogUtils == null) {
            progressDialogUtils = ProgressDialogUtils.getInstance ( this );
            progressDialogUtils.setMessage ( getString ( R.string.public_loading ) );
        }
        if (show) {
            progressDialogUtils.show ();
        } else {
            progressDialogUtils.dismiss ();
        }
    }

    private void setListener() {
        // 右侧sideBar监听
        sidebar.setOnTouchingLetterChangedListener ( new SideBar.OnTouchingLetterChangedListener () {
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection ( s );
                if (position != -1) {
                    lvContact.setSelection ( position );
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
                    searchContact.clear ();
                    ivClear.setVisibility ( View.VISIBLE );
                    for (int j=0;j<contactList.size ();j++){
                        if (contactList.get ( j ).getNickname ().contains ( search )){
                            searchContact.add ( contactList.get ( j ) );
                        }
                    }
                    adapter.setData ( searchContact );
                }else{
                    ivClear.setVisibility ( View.INVISIBLE );
                    adapter.setData ( contactList );
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        lvContact.setOnItemClickListener ( new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView <?> adapterView, View view, int position, long l) {
                ContactForMobileBean.ResultBean bean = (ContactForMobileBean.ResultBean) lvContact.getItemAtPosition ( position );
                ScanResultActivity.start ( ContactFriendActivity.this, bean.getId ());
            }
        } );
    }
    //初始化RecyclerView
    private void initRecyclerView() {
        adapter = new ContactForMobileListAdapter ( contactList );
        lvContact.setAdapter ( adapter );
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

    @OnClick(R2.id.iv_clear)
    public void onViewClearClicked() {
        etSearch.setText ( "" );
    }

    @Override
    public void getContactFromMobileSuccess(List<ContactForMobileBean.ResultBean> result) {
        if (result==null || result.size ()<0){
            return;
        }
        Collections.sort ( result, new Comparator <ContactForMobileBean.ResultBean> () {
            @Override
            public int compare(ContactForMobileBean.ResultBean userInfo, ContactForMobileBean.ResultBean t1) {
                return userInfo.getSpelling ().compareTo(t1.getSpelling ());
            }
        } );
        //加载所有好友
        contactList = result;
        adapter.setData ( result );
    }

    @Override
    public void getContactFromMobileFail() {

    }
}
