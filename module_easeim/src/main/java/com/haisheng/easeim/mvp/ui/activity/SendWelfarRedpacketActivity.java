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
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.di.component.DaggerSendRedpacketComponent;
import com.haisheng.easeim.mvp.contract.SendRedpacketContract;
import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.haisheng.easeim.mvp.model.entity.RedpacketBean;
import com.haisheng.easeim.mvp.presenter.SendRedpacketPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lzj.pass.dialog.PayPassDialog;
import com.lzj.pass.dialog.PayPassView;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.ProgressDialogUtils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class SendWelfarRedpacketActivity  extends BaseSupportActivity <SendRedpacketPresenter> implements SendRedpacketContract.View {

    @BindView(R2.id.tv_money)
    TextView tvMoney;
    @BindView(R2.id.tv_hint)
    TextView tvHint;
    @BindView(R2.id.et_total_money)
    EditText etTotalMoney;
    @BindView(R2.id.et_redpacket_number)
    EditText etRedpacketNumber;

    private ProgressDialogUtils progressDialogUtils;
    private ChatRoomBean mChatRoomBean;

    public static void start(Activity context, ChatRoomBean chatRoomBean) {
        Intent intent = new Intent(context, SendWelfarRedpacketActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("chatRoom", chatRoomBean);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, IMConstants.REQUEST_CODE_SEND_REDPACKET);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSendRedpacketComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_send_welfar_redpacket;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if(null != bundle){
            mChatRoomBean = (ChatRoomBean) bundle.getSerializable("chatRoom");
        }
        etTotalMoney.setHint(String.format("%.2f-%.2f",mChatRoomBean.getWelfareMinMoney(),mChatRoomBean.getWelfareMaxMoney()));
        etRedpacketNumber.setHint(String.format("%d-%d",mChatRoomBean.getWelfareMinRedpacketNumber(),mChatRoomBean.getWelfareMaxRedpacketNumber()));

        etTotalMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkTotalMoney();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        etRedpacketNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRedpacketNumber();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @OnClick(R2.id.btn_send_redpacket)
    public void onViewClicked() {
        if(!checkTotalMoney() && !checkRedpacketNumber()){
            return;
        }
        String sTotalMoney = etTotalMoney.getText().toString();
        int totalMoney = Integer.valueOf(sTotalMoney);
        String sRedpacketNumber = etRedpacketNumber.getText().toString();
        int redpacketNumber = Integer.valueOf(sRedpacketNumber);

        payDialog ( redpacketNumber,totalMoney );
    }

    //1 默认方式(推荐)
    private void payDialog(int redpacketNumber,int totalMoney) {
        final PayPassDialog dialog=new PayPassDialog(this);
        dialog.getPayViewPass()
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String passContent) {
                        dialog.dismiss ();
                        //6位输入完成回调
                        mPresenter.sendRedpacket(mChatRoomBean.getId(),null,redpacketNumber,totalMoney,1,passContent);
                    }
                    @Override
                    public void onPayClose() {
                        dialog.dismiss();
                        //关闭弹框
                    }
                    @Override
                    public void onPayForget() {
                        dialog.dismiss();
                        //点击忘记密码回调
                        ARouter.getInstance ().build ( RouterHub.MAIN_FORGETPAYPASSWORDACTIVITY ).navigation ();
                    }
                });
    }

    private boolean checkTotalMoney(){
        String sTotalMoney = etTotalMoney.getText().toString();
        if(!TextUtils.isEmpty(sTotalMoney)){
            int totalMoney = Integer.valueOf(sTotalMoney);
            tvMoney.setText(String.format("￥%d",totalMoney));
            if(totalMoney>mChatRoomBean.getWelfareMaxMoney()){
                tvHint.setVisibility(View.VISIBLE);
                tvHint.setText(String.format(getString( R.string.redpacket_money_not_more_than),mChatRoomBean.getWelfareMaxMoney()));
            }else if(totalMoney<mChatRoomBean.getWelfareMinMoney()){
                tvHint.setVisibility(View.VISIBLE);
                tvHint.setText(String.format(getString( R.string.redpacket_money_not_less_than),mChatRoomBean.getWelfareMinMoney()));
            }else{
                tvHint.setVisibility(View.INVISIBLE);
                return true;
            }
        }else{
            tvHint.setVisibility(View.VISIBLE);
            tvHint.setText(String.format(getString( R.string.redpacket_money_not_less_than),mChatRoomBean.getWelfareMinMoney()));
            tvMoney.setText("￥0");
        }
        return false;
    }

    private boolean checkRedpacketNumber(){
        String sRedpacketNumber = etRedpacketNumber.getText().toString();
        if(!TextUtils.isEmpty(sRedpacketNumber)){
            int redpacketNumber = Integer.valueOf(sRedpacketNumber);
            if(redpacketNumber>mChatRoomBean.getWelfareMaxRedpacketNumber() || redpacketNumber<mChatRoomBean.getWelfareMinRedpacketNumber()){
                tvHint.setVisibility(View.VISIBLE);
                tvHint.setText(String.format(getString( R.string.redpacket_number_scope),mChatRoomBean.getWelfareMinRedpacketNumber(),mChatRoomBean.getWelfareMaxRedpacketNumber()));
            }else{
                tvHint.setVisibility(View.INVISIBLE);
                return true;
            }
        }else{
            tvHint.setVisibility(View.VISIBLE);
            tvHint.setText( R.string.redpacket_number_not_empty);
        }
        return false;
    }

    private void showProgress(final boolean show) {
        if (progressDialogUtils == null) {
            progressDialogUtils = ProgressDialogUtils.getInstance(mContext);
            progressDialogUtils.setMessage(getString( R.string.public_loading));
        }
        if (show) {
            progressDialogUtils.show();
        } else {
            progressDialogUtils.dismiss();
        }
    }

    @Override
    public void showLoading() {
        showProgress(true);
    }

    @Override
    public void hideLoading() {
        showProgress(false);
    }

    @Override
    public void showMessage(@NonNull String message) {
//        checkNotNull(message);
        ToastUtils.showShort(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void sendSuccessfully(RedpacketBean redpacketInfo) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("redpacketInfo",redpacketInfo);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }
}