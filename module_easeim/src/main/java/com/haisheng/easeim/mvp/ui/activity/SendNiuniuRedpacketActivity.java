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
import com.haisheng.easeim.app.AppLifecyclesImpl;
import com.hyphenate.easeui.utils.IMConstants;
import com.haisheng.easeim.di.component.DaggerSendRedpacketComponent;
import com.haisheng.easeim.mvp.contract.SendRedpacketContract;
import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.haisheng.easeim.mvp.model.entity.CheckPayPasswordBean;
import com.hyphenate.easeui.bean.RedpacketBean;
import com.haisheng.easeim.mvp.presenter.SendRedpacketPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.BaseCustomDialog;
import me.jessyan.armscomponent.commonres.dialog.BaseDialog;
import me.jessyan.armscomponent.commonres.utils.ConvertNumUtils;
import me.jessyan.armscomponent.commonres.utils.ProgressDialogUtils;
import me.jessyan.armscomponent.commonres.view.PayPassDialog;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class SendNiuniuRedpacketActivity extends BaseSupportActivity <SendRedpacketPresenter> implements SendRedpacketContract.View {

    @BindView(R2.id.tv_money)
    TextView tvMoney;
    @BindView(R2.id.tv_hint)
    TextView tvHint;
    @BindView(R2.id.et_total_money)
    EditText etTotalMoney;
    @BindView(R2.id.et_redpacket_number)
    EditText etRedpacketNumber;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_redpacket_money)
    TextView tvRedpacketMoney;
    @BindView(R2.id.tv_redpacket_num)
    TextView tvRedpacketNum;
    @BindView(R2.id.btn_send_redpacket)
    TextView btnSendRedPacket;

    private ProgressDialogUtils progressDialogUtils;
    private ChatRoomBean mChatRoomBean;
    private BaseDialog dialog;
    private Integer totalMoney;
    private Integer redpacketNumber;

    public static void start(Activity context, ChatRoomBean chatRoomBean) {
        Intent intent = new Intent ( context, SendNiuniuRedpacketActivity.class );
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
        return R.layout.activity_send_niuniu_redpacket;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        Bundle bundle = getIntent ().getExtras ();
        if (null != bundle) {
            mChatRoomBean = (ChatRoomBean) bundle.getSerializable ( "chatRoom" );
            tvTitle.setText ( mChatRoomBean.getName () );
            tvRedpacketMoney.setText ( "红包金额" +mChatRoomBean.getMinMoney ()+"-"+ mChatRoomBean.getMaxMoney ()+"元" );
            tvRedpacketNum.setText ( "红包个数" +mChatRoomBean.getMinRedpacketNumber ()+"-"+ mChatRoomBean.getMaxRedpacketNumber () +"个");
        }
        etTotalMoney.setHint ( "余额："+ AppLifecyclesImpl.getBalance () );
        etRedpacketNumber.setHint ( String.format ( "%d-%d", mChatRoomBean.getMinRedpacketNumber (), mChatRoomBean.getMaxRedpacketNumber () ) );

        etTotalMoney.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkTotalMoney ();
                if (etRedpacketNumber.getText ().toString ().length () > 0 && etTotalMoney.getText ().toString ().length () > 0) {
                    btnSendRedPacket.setBackgroundResource ( R.drawable.btn_ad_shape );
                } else {
                    btnSendRedPacket.setBackgroundResource ( R.drawable.btn_ad_shape_enable );
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        } );

        etRedpacketNumber.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRedpacketNumber ();
                if (etRedpacketNumber.getText ().toString ().length () > 0 && etTotalMoney.getText ().toString ().length () > 0) {
                    btnSendRedPacket.setBackgroundResource ( R.drawable.btn_ad_shape );
                } else {
                    btnSendRedPacket.setBackgroundResource ( R.drawable.btn_ad_shape_enable );
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        } );
    }

    @OnClick(R2.id.btn_send_redpacket)
    public void onViewClicked() {
        if (!checkTotalMoney () && !checkRedpacketNumber ()) {
            return;
        }
        String sTotalMoney = etTotalMoney.getText ().toString ();
        totalMoney = ConvertNumUtils.stringToInt( sTotalMoney );
        String sRedpacketNumber = etRedpacketNumber.getText ().toString ();
        redpacketNumber = ConvertNumUtils.stringToInt ( sRedpacketNumber );
        mPresenter.checkPayPasswrod ();
    }

    //1 默认方式(推荐)
    private void payDialog(int redpacketNumber, int totalMoney) {
        PayPassDialog dialog = new PayPassDialog ( this,etTotalMoney.getText ().toString () );
        dialog.setPayPasswordInputListener ( new PayPassDialog.PayPasswordInputListener () {
            @Override
            public void inputFinish(String password) {
                //6位输入完成回调
                mPresenter.sendRedpacket ( mChatRoomBean.getId (), null, redpacketNumber, totalMoney, 0, password );
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

    private boolean checkRedpacketNumber() {
        String sRedpacketNumber = etRedpacketNumber.getText ().toString ();
        if (!TextUtils.isEmpty ( sRedpacketNumber )) {
            int redpacketNumber = Integer.valueOf ( sRedpacketNumber );
            if (redpacketNumber > mChatRoomBean.getMaxRedpacketNumber () || redpacketNumber < mChatRoomBean.getMinRedpacketNumber ()) {
                tvHint.setVisibility ( View.VISIBLE );
                tvHint.setText ( String.format ( getString ( R.string.redpacket_number_scope ), mChatRoomBean.getMinRedpacketNumber (), mChatRoomBean.getMaxRedpacketNumber () ) );
            } else {
                tvHint.setVisibility ( View.INVISIBLE );
                return true;
            }
        } else {
            tvHint.setVisibility ( View.VISIBLE );
            tvHint.setText ( R.string.redpacket_number_not_empty );
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
            payDialog ( redpacketNumber, totalMoney );
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
