package com.haisheng.easeim.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.app.IMHelper;
import com.haisheng.easeim.di.component.DaggerContactInfoComponent;
import com.haisheng.easeim.mvp.contract.ContactInfoContract;
import com.haisheng.easeim.mvp.presenter.ContactInfoPresenter;
import com.haisheng.easeim.mvp.ui.fragment.ContactFragment;
import com.hyphenate.easeui.EaseConstant;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.BaseCustomDialog;
import me.jessyan.armscomponent.commonres.dialog.BaseDialog;
import me.jessyan.armscomponent.commonres.utils.SpUtils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/16/2019 15:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ContactInfoActivity extends BaseSupportActivity <ContactInfoPresenter> implements ContactInfoContract.View {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_right)
    TextView tvRight;
    @BindView(R2.id.iv_contact_head)
    ImageView ivContactHead;
    @BindView(R2.id.tv_name)
    TextView tvName;
    @BindView(R2.id.tv_contact_sex)
    ImageView tvContactSex;
    @BindView(R2.id.account)
    TextView account;
    @BindView(R2.id.tv_account)
    TextView tvAccount;
    @BindView(R2.id.tv_remark)
    TextView tvRemark;
    @BindView(R2.id.btn_send_message)
    Button btnSendMessage;
    private BaseDialog dialog;
    private UserInfo userInfo;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerContactInfoComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_contact_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "详细资料" );
        tvRight.setText ( "删除" );
        tvRight.setVisibility ( View.VISIBLE );
        userInfo = (UserInfo) getIntent ().getSerializableExtra ( "userId" );
        if (userInfo!=null){
            tvAccount.setText ( userInfo.getIncode ()+"" );
            tvName.setText ( userInfo.getNickname () );
            if (userInfo.getSexStatus ()==0){
                //男
                tvContactSex.setImageResource ( R.drawable.ic_male );
            }else{
                tvContactSex.setImageResource ( R.drawable.ic_female );
            }
            Glide.with ( this ).load ( userInfo.getAvatarUrl () ).into ( ivContactHead );
        }
    }
    public static void start(Activity context, UserInfo userInfo) {
        start ( context, userInfo, EaseConstant.CHATTYPE_SINGLE );
    }

    public static void start(Activity context, UserInfo userInfo, int chatType) {
        Intent intent = new Intent ( context, ContactInfoActivity.class );
        Bundle bundle = new Bundle ();
        bundle.putSerializable ( "userId", userInfo );
        bundle.putInt ( "chatType", chatType );
        intent.putExtras ( bundle );
        context.startActivity ( intent );
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
        EventBus.getDefault ().register ( this );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        EventBus.getDefault ().unregister ( this );
    }

    @OnClick({R2.id.iv_back, R2.id.tv_right, R2.id.tv_remark, R2.id.btn_send_message})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.tv_right) {
            //删除
            showDelectFriend ();
        } else if (i == R.id.tv_remark) {
            updateNickName();
        } else if (i == R.id.btn_send_message) {
            //发送消息
            if (userInfo!=null && !TextUtils.isEmpty ( userInfo.getHxId () )) {
                //保存昵称跟头像
                SpUtils.put ( this, userInfo.getHxId () + "head", userInfo.getAvatarUrl () );
                SpUtils.put ( this, userInfo.getHxId () + "nickname", userInfo.getNickname () );
                ChatActivity.start ( this, userInfo.getHxId () );
            }
        }
    }

    //修改昵称
    private void updateNickName() {
        dialog = new BaseCustomDialog.Builder ( this, R.layout.dialog_update_nickname, true, new BaseCustomDialog.Builder.OnShowDialogListener () {
            @Override
            public void onShowDialog(View layout) {
                TextView tvTips = layout.findViewById ( R.id.tv_tips );
                tvTips.setText ( "设置备注" );
                EditText etNickName = layout.findViewById ( R.id.et_nickname );
                etNickName.setText ( userInfo.getNickname () );
                etNickName.setSelection ( etNickName.length () );
                TextView btnCancel = layout.findViewById ( R.id.btn_cancel );
                TextView btnSure = layout.findViewById ( R.id.btn_sure );
                btnCancel.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                    }
                } );
                btnSure.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        String remarkName = etNickName.getText ().toString ().trim ();
                        if (TextUtils.isEmpty ( remarkName )){
                            ToastUtils.showShort ( "请输入备注名" );
                            return;
                        }
                        if (userInfo!=null) {
                            mPresenter.setRemark ( remarkName, userInfo.getId () + "" );
                        }
                    }
                } );
            }
        } ).create ();
        dialog.show ();
    }

    //删除好友
    private void showDelectFriend() {
        dialog = new BaseCustomDialog.Builder ( this, R.layout.dialog_submit_blankinfo, false, new BaseCustomDialog.Builder.OnShowDialogListener () {
            @Override
            public void onShowDialog(View layout) {
                TextView tvTips = layout.findViewById ( R.id.tv_tip );
                tvTips.setText ( "删除好友" );
                TextView tvMessage = layout.findViewById ( R.id.tv_message );
                tvMessage.setText ( "将好友");
                if (userInfo!=null){
                    tvMessage.append ( userInfo.getNickname () );
                }
                tvMessage.append ( "删除，将同时删除与该好友的聊天记录" );
                layout.findViewById ( R.id.tv_sure ).setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        //确定
                        mPresenter.delectFriend ( userInfo.getId ()+"" );
                    }
                } );
                layout.findViewById ( R.id.tv_cancel ).setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                    }
                } );
            }
        } )
                .create ();
        dialog.show ();
    }

    @Override
    public void delectFriendSuccess() {
        ToastUtils.showShort ( "删除成功" );
        if (userInfo!=null) {
            IMHelper.getInstance ().delectContact ( userInfo.getHxId () );
        }
        finish ();
    }

    @Override
    public void delectFriendFail() {
        ToastUtils.showShort ( "删除失败" );
    }

    /**
     * {@link ContactFragment#setRemarkSuccess(java.lang.String)}
     * @param remark
     */
    @Override
    public void setRemarkSuccess(String remark) {
        ToastUtils.showShort ( "设置成功" );
        tvName.setText ( remark );
        EventBus.getDefault ().post ( remark,"setRemarkSuccess" );
    }

    @Override
    public void setRemarkFail() {
        ToastUtils.showShort ( "设置失败" );
    }
}
