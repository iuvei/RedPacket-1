package com.haisheng.easeim.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.haisheng.easeim.R;
import com.haisheng.easeim.R2;
import com.haisheng.easeim.app.IMHelper;
import com.haisheng.easeim.di.component.DaggerChatDetailsComponent;
import com.haisheng.easeim.mvp.contract.ChatDetailsContract;
import com.haisheng.easeim.mvp.presenter.ChatDetailsPresenter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.BaseCustomDialog;
import me.jessyan.armscomponent.commonres.dialog.BaseDialog;
import me.jessyan.armscomponent.commonres.utils.SpUtils;
import me.jessyan.armscomponent.commonres.view.SwitchButton;
import me.jessyan.armscomponent.commonsdk.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.utils.StatusBarUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/16/2019 16:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ChatDetailsActivity extends BaseSupportActivity <ChatDetailsPresenter> implements ChatDetailsContract.View {
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.iv_contact_head)
    ImageView ivContactHead;
    @BindView(R2.id.tv_nickname)
    TextView tvNickname;
    @BindView(R2.id.swb_top_message)
    SwitchButton swbTopMessage;
    @BindView(R2.id.swb_message_not_notice)
    SwitchButton swbMessageNotNotice;
    @BindView(R2.id.tv_delect_all_message)
    TextView tvDelectAllMessage;
    @BindView(R2.id.tv_delect_message_time)
    TextView tvDelectMessageTime;
    private UserInfo userInfo;
    private BaseDialog dialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChatDetailsComponent //如找不到该类,请编译一下项目
                .builder ()
                .appComponent ( appComponent )
                .view ( this )
                .build ()
                .inject ( this );
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_chat_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentStatus ( this );
        StatusBarUtils.setStatusBarDarkTheme ( this, true );
        tvTitle.setText ( "聊天详情" );
        userInfo = (UserInfo) getIntent ().getSerializableExtra ( "userId" );
        if (userInfo!=null) {
            boolean chatTop = SpUtils.getValue ( ChatDetailsActivity.this, userInfo.getHxId (), false );
            if (chatTop) {
                swbTopMessage.setChecked ( true );
            } else {
                swbTopMessage.setChecked ( false );
            }
            tvNickname.setText ( userInfo.getNickname () );
            Glide.with ( this ).load ( userInfo.getAvatarUrl () ).into ( ivContactHead );
        }
        setListener();
    }

    private void setListener() {
        swbMessageNotNotice.setOnCheckedChangeListener ( new SwitchButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                IMHelper.getInstance ().getModel ().setSettingMsgSound ( isChecked );
                IMHelper.getInstance ().getModel ().setSettingMsgVibrate ( isChecked );
            }
        } );
        swbTopMessage.setOnCheckedChangeListener ( new SwitchButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (userInfo==null){
                    return;
                }
                SpUtils.put ( ChatDetailsActivity.this,userInfo.getHxId (),isChecked);
            }
        } );
    }

    public static void start(Context context, UserInfo userInfo) {

    }

    public static void start(Activity context, UserInfo userInfo) {
        Intent intent = new Intent ( context, ChatDetailsActivity.class );
        Bundle bundle = new Bundle ();
        bundle.putSerializable ( "userId", userInfo );
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
    }

    @OnClick({R2.id.iv_back, R2.id.iv_contact_head, R2.id.rl_delect_message, R2.id.tv_delect_all_message})
    public void onViewClicked(View view) {
        int i = view.getId ();
        if (i == R.id.iv_back) {
            finish ();
        } else if (i == R.id.iv_contact_head) {
            ContactInfoActivity.start ( this,userInfo );
        } else if (i == R.id.rl_delect_message) {
            delectMessageDialgTime();
        } else if (i == R.id.tv_delect_all_message) {
            showClearMessage();
        }
    }

    //保存聊天记录
    private void delectMessageDialgTime() {
        dialog = new BaseCustomDialog.Builder ( this, R.layout.dialog_message_talking_delect, true, new BaseCustomDialog.Builder.OnShowDialogListener () {
            @Override
            public void onShowDialog(View layout) {
                TextView tvClose = layout.findViewById ( R.id.tv_close);
                TextView tvNow = layout.findViewById ( R.id.tv_now);
                TextView tvThreeMinute = layout.findViewById ( R.id.tv_three_minute);
                TextView tvOneHour = layout.findViewById ( R.id.tv_one_hour);
                TextView tvOneDay = layout.findViewById ( R.id.tv_one_day );
                TextView tvSevenDay = layout.findViewById ( R.id.tv_seven_day );
                TextView tvOneMonth = layout.findViewById ( R.id.tv_one_month );
                tvClose.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        tvDelectMessageTime.setText ( "关闭" );
                    }
                } );
                tvNow.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        tvDelectMessageTime.setText ( "即刻焚烧" );
                    }
                } );
                tvThreeMinute.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        tvDelectMessageTime.setText ( "三分钟" );
                    }
                } );
                tvOneHour.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        tvDelectMessageTime.setText ( "一小时" );
                    }
                } );
                tvOneDay.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        tvDelectMessageTime.setText ( "一天" );
                    }
                } );
                tvSevenDay.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        tvDelectMessageTime.setText ( "七天" );
                    }
                } );
                tvOneMonth.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        tvDelectMessageTime.setText ( "一个月" );
                    }
                } );
            }
        } ).create ();
        dialog.show ();
    }

    //清除聊天记录
    private void showClearMessage() {
        dialog = new BaseCustomDialog.Builder ( this, R.layout.dialog_submit_blankinfo, false, new BaseCustomDialog.Builder.OnShowDialogListener () {
            @Override
            public void onShowDialog(View layout) {

                TextView tvMessage = layout.findViewById ( R.id.tv_message );
                tvMessage.setText ( "是否清空所有聊天记录吗？" );
                layout.findViewById ( R.id.tv_sure ).setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss ();
                        if (userInfo==null){
                            return;
                        }
                        //确定
                        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(userInfo.getHxId ());
                        //获取此会话的所有消息
                        List <EMMessage> messages = conversation.getAllMessages();
                        for (int i=0;i<messages.size ();i++){
                            //删除当前会话的某条聊天记录
                            conversation.removeMessage(messages.get (i).getMsgId ());
                        }
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
}
