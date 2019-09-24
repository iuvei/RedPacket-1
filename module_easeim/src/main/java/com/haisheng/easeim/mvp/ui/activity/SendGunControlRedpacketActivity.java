package com.haisheng.easeim.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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
import com.haisheng.easeim.mvp.model.entity.RedpacketBean;
import com.haisheng.easeim.mvp.presenter.SendRedpacketPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lzj.pass.dialog.PayPassDialog;
import com.lzj.pass.dialog.PayPassView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.ProgressDialogUtils;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class SendGunControlRedpacketActivity extends BaseSupportActivity <SendRedpacketPresenter> implements SendRedpacketContract.View {

    @BindView(R2.id.et_total_money)
    EditText etTotalMoney;
    @BindView(R2.id.tfl_redpacket_number)
    TagFlowLayout tflRedpacketNumber;
    @BindView(R2.id.tfl_mine_numbers)
    TagFlowLayout tflMineNumbers;
    @BindView(R2.id.tv_money)
    TextView tvMoney;

    private static final Integer REDPACKET_NUMBERS[] = {5, 6, 7, 9, 10};
    private static final Integer MINE_NUMBERS[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

    TagAdapter mRedpacketNumberAdapter;
    TagAdapter mMineNumberAdapter;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;

    private ProgressDialogUtils progressDialogUtils;
    private ChatRoomBean mChatRoomBean;
    private int mCurrentRedpacketNumber = 0;

    public static void start(Activity context, ChatRoomBean chatRoomBean) {
        Intent intent = new Intent ( context, SendGunControlRedpacketActivity.class );
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
        return R.layout.activity_send_gun_control_redpacket;
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
        etTotalMoney.setHint ( String.format ( "%.0f-%.0f", mChatRoomBean.getMinMoney (), mChatRoomBean.getMaxMoney () ) );
        List <Integer> redpacketNumbers = new ArrayList <> ( Arrays.asList ( REDPACKET_NUMBERS ) );
        mRedpacketNumberAdapter = new TagAdapter ( redpacketNumbers ) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                int number = (int) o;
                TextView tv = (TextView) LayoutInflater.from ( mContext ).inflate ( R.layout.item_circle_number,
                        tflRedpacketNumber, false );
                tv.setText ( String.valueOf ( number ) );
                return tv;
            }
        };
        tflRedpacketNumber.setOnSelectListener ( new TagFlowLayout.OnSelectListener () {
            @Override
            public void onSelected(Set <Integer> selectPosSet) {
                Iterator <Integer> value = selectPosSet.iterator ();
                mCurrentRedpacketNumber = REDPACKET_NUMBERS[value.next ()];
                tflMineNumbers.setMaxSelectCount ( mCurrentRedpacketNumber > 7 ? 7 : mCurrentRedpacketNumber );
                if (mCurrentRedpacketNumber == 10) {
                    tflMineNumbers.setMaxSelectCount ( 8 );
                }

                mMineNumberAdapter.setSelectedList ();
            }
        } );
        tflRedpacketNumber.setAdapter ( mRedpacketNumberAdapter );

        List <Integer> mineNumbers = new ArrayList <> ( Arrays.asList ( MINE_NUMBERS ) );
        mMineNumberAdapter = new TagAdapter ( mineNumbers ) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                int number = (int) o;
                TextView tv = (TextView) LayoutInflater.from ( mContext ).inflate ( R.layout.item_circle_number,
                        tflMineNumbers, false );
                tv.setText ( String.valueOf ( number ) );
                return tv;
            }
        };
        tflMineNumbers.setOnTagClickListener ( new TagFlowLayout.OnTagClickListener () {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (0 == mCurrentRedpacketNumber) {
                    showMessage ( "请选择红包个数!" );
                } else if (!view.isSelected ()) {
                    int canSelectMaxNumber = mCurrentRedpacketNumber > 7 ? 7 : mCurrentRedpacketNumber;
                    if (mCurrentRedpacketNumber == 10) {
                        canSelectMaxNumber = 8;
                    }

                    if (canSelectMaxNumber == tflMineNumbers.getSelectedList ().size () && !tflMineNumbers.getSelectedList ().contains ( position )) {
                        showMessage ( String.format ( "红包个数为：%d的红包只能够选：%d个雷号", mCurrentRedpacketNumber, canSelectMaxNumber ) );
                    }
                }
                return false;
            }
        } );
        tflMineNumbers.setAdapter ( mMineNumberAdapter );

        etTotalMoney.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String sMoney = s.toString ();
                if (!TextUtils.isEmpty ( sMoney )) {
                    tvMoney.setText ( String.format ( "￥%s", s ) );
                } else {
                    tvMoney.setText ( "￥0" );
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        } );
    }

    @OnClick(R2.id.btn_send_redpacket)
    public void onViewClicked() {
        String sMoney = etTotalMoney.getText ().toString ();
        if (TextUtils.isEmpty ( sMoney )) {
            showMessage ( "红包金额不能为空!" );
            return;
        }
        int money = Integer.valueOf ( sMoney );
        if (money > mChatRoomBean.getMaxMoney () || money < mChatRoomBean.getMinMoney ()) {
            showMessage ( String.format ( "红包金额只能输入%.0f - %.0f", mChatRoomBean.getMinMoney (), mChatRoomBean.getMaxMoney () ) );
            return;
        }
        Set <Integer> redpacketNumbers = tflRedpacketNumber.getSelectedList ();
        if (redpacketNumbers.size () == 0) {
            showMessage ( "请先选择红包个数!" );
            return;
        }
        Set <Integer> mineNumbers = tflMineNumbers.getSelectedList ();
        if (mineNumbers.size () == 0) {
            showMessage ( "雷数至少选一个数!" );
            return;
        }
        StringBuilder sbBoom = new StringBuilder ();
        Iterator <Integer> value = mineNumbers.iterator ();
        sbBoom.append ( MINE_NUMBERS[value.next ()] );
        while (value.hasNext ()) {
            sbBoom.append ( "," ).append ( MINE_NUMBERS[value.next ()] );
        }
        payDialog ( sbBoom.toString (), money );
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
                        mPresenter.sendRedpacket ( mChatRoomBean.getId (), sbBoom, mCurrentRedpacketNumber, money, 0, passContent );
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
