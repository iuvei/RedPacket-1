package com.haisheng.easeim.mvp.ui.widget.message;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.haisheng.easeim.R;
import com.hyphenate.easeui.utils.IMConstants;
import com.hyphenate.easeui.bean.GunControlSettlementInfo;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

import java.util.List;

public class ChatGunControlSettlement extends EaseChatRow {

    TextView tvResult;

    public ChatGunControlSettlement(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate( R.layout.row_gun_control_settlement, this);
    }

    @Override
    protected void onFindViewById() {
        tvResult = findViewById( R.id.tv_result);
    }

    @Override
    public void onSetUpView() {
        String sGunControlSettlementInfo =message.getStringAttribute( IMConstants.MESSAGE_ATTR_CONENT,"");
        if(!TextUtils.isEmpty(sGunControlSettlementInfo)){
            GunControlSettlementInfo settlementInfo = new Gson().fromJson(sGunControlSettlementInfo, GunControlSettlementInfo.class);
            StringBuilder sb = new StringBuilder (  );
            sb.append ( "恭喜" );
            sb.append ( "<font color ='#FF0000'>"+settlementInfo.getNickname ()+"</font>," );
            sb.append ( settlementInfo.getMoney()+"-"+ settlementInfo.getMineNumber());
            sb.append ( "收获"+settlementInfo.getBombNumber()+"个雷," );
            sb.append ( "奖励" );
            sb.append ("<font color ='#FF0000'>"+settlementInfo.getAwardMoney()+"元</font>" );
            tvResult.setText( Html.fromHtml ( sb.toString () ) );
            LogUtils.e ( "tag----",settlementInfo.toString () );
        }
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        switch (msg.status()) {
            case CREATE:
                onMessageCreate();
                break;
            case SUCCESS:
                onMessageSuccess();
                break;
            case FAIL:
                onMessageError();
                break;
            case INPROGRESS:
                onMessageInProgress();
                break;
        }
    }

    public void onAckUserUpdate(final int count) {
        if (ackedView != null) {
            ackedView.post(new Runnable() {
                @Override
                public void run() {
                    ackedView.setVisibility(VISIBLE);
                    ackedView.setText(String.format(getContext().getString( R.string.group_ack_read_count), count));
                }
            });
        }
    }

    private void onMessageCreate() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    private void onMessageSuccess() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.GONE);

        // Show "1 Read" if this msg is a ding-type msg.
        if (EaseDingMessageHelper.get().isDingMessage(message) && ackedView != null) {
            ackedView.setVisibility(VISIBLE);
            List<String> userList = EaseDingMessageHelper.get().getAckUsers(message);
            int count = userList == null ? 0 : userList.size();
            ackedView.setText(String.format(getContext().getString( R.string.group_ack_read_count), count));
        }

        // Set ack-user list change listener.
        EaseDingMessageHelper.get().setUserUpdateListener(message, userUpdateListener);
    }

    private void onMessageError() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.VISIBLE);
    }

    private void onMessageInProgress() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    private EaseDingMessageHelper.IAckUserUpdateListener userUpdateListener =
            new EaseDingMessageHelper.IAckUserUpdateListener() {
                @Override
                public void onUpdate(List<String> list) {
                    onAckUserUpdate(list.size());
                }
            };
}
