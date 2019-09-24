package com.haisheng.easeim.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.di.component.DaggerSendRedpacketComponent;
import com.haisheng.easeim.mvp.contract.SendRedpacketContract;
import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.haisheng.easeim.mvp.model.entity.CheckPayPasswordBean;
import com.haisheng.easeim.mvp.model.entity.RedpacketBean;
import com.haisheng.easeim.mvp.presenter.SendRedpacketPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lzj.pass.dialog.PayPassDialog;
import com.lzj.pass.dialog.PayPassView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.BaseCustomDialog;
import me.jessyan.armscomponent.commonres.dialog.BaseDialog;
import me.jessyan.armscomponent.commonres.utils.ProgressDialogUtils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/04/2019 18:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SendMineRedpacketActivity extends BaseSupportActivity <SendRedpacketPresenter> implements SendRedpacketContract.View {

    @BindView(R2.id.tv_money)
    TextView tvMoney;
    @BindView(R2.id.tv_hint)
    TextView tvHint;
    @BindView(R2.id.et_total_money)
    EditText etTotalMoney;
    @BindView(R2.id.et_redpacket_number)
    EditText etRedpacketNumber;
    @BindView(R2.id.tv_money_scope)
    TextView tvMoneyScope;
    @BindView(R2.id.et_mine_number)
    EditText etMineNumber;
    @BindView(R2.id.tv_mine_scope)
    TextView tvMineScope;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;

    private ProgressDialogUtils progressDialogUtils;
    private ChatRoomBean mChatRoomBean;
    private BaseDialog dialog;
    private Integer totalMoney;
    private String sMineNumber;

    public static void start(Activity context, ChatRoomBean chatRoomBean) {
        Intent intent = new Intent ( context, SendMineRedpacketActivity.class );
        Bundle bundle = new Bundle ();
        bundle.putSerializable ( "chatRoom", chatRoomBean );
        intent.putExtras ( bundle );
        context.startActivityForResult ( intent, IMConstants.REQUEST_CODE_SEND_REDPACKET );
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSendRedpacketComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_send_mine_redpacket; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        Bundle bundle = getIntent ().getExtras ();
        if (null != bundle) {
            mChatRoomBean = (ChatRoomBean) bundle.getSerializable ( "chatRoom" );
            tvTitle.setText ( mChatRoomBean.getName () );
        }
        etTotalMoney.setHint ( String.format ( "%.2f-%.2f", mChatRoomBean.getMinMoney (), mChatRoomBean.getMaxMoney () ) );
        etRedpacketNumber.setText ( String.valueOf ( mChatRoomBean.getRedpacketNumber () ) );
        tvMoneyScope.setText ( String.format ( getString ( R.string.redpacket_money_scope ), mChatRoomBean.getMinMoney (), mChatRoomBean.getMaxMoney () ) );

        etTotalMoney.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkTotalMoney ();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        } );

        etMineNumber.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkMineNumber ();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        } );
    }

    @OnClick(R2.id.btn_send_redpacket)
    public void onViewClicked() {
        if (!checkTotalMoney () || !checkMineNumber ()) {
            return;
        }
        String sTotalMoney = etTotalMoney.getText ().toString ();
        totalMoney = Integer.valueOf ( sTotalMoney );
        sMineNumber = etMineNumber.getText ().toString ();
        mPresenter.checkPayPasswrod ();
    }

    //1 默认方式(推荐)
    private void payDialog(String sbBoom, double money) {
        final PayPassDialog dialog = new PayPassDialog ( this );
        dialog.getPayViewPass ()
                .setPayClickListener ( new PayPassView.OnPayClickListener () {
                    @Override
                    public void onPassFinish(String passContent) {
                        dialog.dismiss ();
                        //6位输入完成回调
                        mPresenter.sendRedpacket ( mChatRoomBean.getId (), sbBoom, mChatRoomBean.getRedpacketNumber (), money, 0, passContent );
                    }

                    @Override
                    public void onPayClose() {
                        dialog.dismiss ();
                        //关闭弹框
                    }

                    @Override
                    public void onPayForget() {
                        dialog.dismiss ();
                        //点击忘记密码回调
                        ARouter.getInstance ().build ( RouterHub.MAIN_FORGETPAYPASSWORDACTIVITY ).navigation ();
                    }
                } );
    }

    private boolean checkTotalMoney() {
        String sTotalMoney = etTotalMoney.getText ().toString ();
        if (!TextUtils.isEmpty ( sTotalMoney )) {
            int totalMoney = Integer.valueOf ( sTotalMoney );
            tvMoney.setText ( String.format ( "￥%d", totalMoney ) );
            if (totalMoney > mChatRoomBean.getMaxMoney ()) {
                tvHint.setVisibility ( View.VISIBLE );
                tvHint.setText ( String.format ( getString ( R.string.redpacket_money_not_more_than ), mChatRoomBean.getMaxMoney () ) );
            } else if (totalMoney < mChatRoomBean.getMinMoney ()) {
                tvHint.setVisibility ( View.VISIBLE );
                tvHint.setText ( String.format ( getString ( R.string.redpacket_money_not_less_than ), mChatRoomBean.getMinMoney () ) );
            } else {
                tvHint.setVisibility ( View.INVISIBLE );
                return true;
            }
        } else {
            tvHint.setVisibility ( View.VISIBLE );
            tvHint.setText ( String.format ( getString ( R.string.redpacket_money_not_less_than ), mChatRoomBean.getMinMoney () ) );
            tvMoney.setText ( "￥0" );
        }
        return false;
    }

    private boolean checkMineNumber() {
        String sMineNumber = etMineNumber.getText ().toString ();
        if (!TextUtils.isEmpty ( sMineNumber )) {
            tvHint.setVisibility ( View.INVISIBLE );
            return true;
        } else {
            tvHint.setVisibility ( View.VISIBLE );
            tvHint.setText ( R.string.mine_scope );
        }
        return false;
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
//        ArmsUtils.snackbarText(message);
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
    public void sendSuccessfully(RedpacketBean redpacketInfo) {
        Intent intent = new Intent ();
        Bundle bundle = new Bundle ();
        bundle.putSerializable ( "redpacketInfo", redpacketInfo );
        intent.putExtras ( bundle );
        setResult ( RESULT_OK, intent );
        finish ();
    }

    @Override
    public void checkPayPasswordSuccessfully(CheckPayPasswordBean response) {
        if (response.isHasPayPassword ()){
            payDialog ( sMineNumber, totalMoney );
        }else{
            showAuthDialog();
        }
    }

    @Override
    public void checkPayPasswordFail() {

    }

    private void showAuthDialog() {
        dialog = new BaseCustomDialog.Builder ( this, R.layout.dialog_submit_blankinfo, false, new BaseCustomDialog.Builder.OnShowDialogListener () {
            @Override
            public void onShowDialog(View layout) {
                TextView tvMessage = layout.findViewById ( R.id.tv_message );
                tvMessage.setText ( "还没有设置支付密码，是否前往设置" );
                layout.findViewById ( R.id.tv_sure ).setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        //确定
                        ARouter.getInstance ().build ( RouterHub.MAIN_PAYPASSWORDACTIVITY ).navigation ();
                    }
                } );
                layout.findViewById ( R.id.tv_cancel ).setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        finish ();
                    }
                } );
            }
        } )
                .create ();
        dialog.show ();
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
