package com.haisheng.easeim.mvp.ui.widget.message;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.haisheng.easeim.R;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.mvp.model.entity.RedpacketBean;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

import java.util.List;

import me.jessyan.armscomponent.commonres.utils.ImageLoader;

public class ChatBasicRedpacket extends EaseChatRow {

    private TextView tvMessage, tvRedpacketStatus, tvRedpacketType,tvCenterMessage;
    private RelativeLayout bubble;

    private int mType;

    public ChatBasicRedpacket(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.row_received_basic_redpacket : R.layout.row_sent_basic_redpacket, this);
    }

    @Override
    protected void onFindViewById() {
        bubble= findViewById( R.id.bubble);
        tvCenterMessage = findViewById( R.id.tv_center_message);
        tvMessage = findViewById( R.id.tv_message);
        tvRedpacketStatus = findViewById( R.id.tv_redpacket_status);
        tvRedpacketType = findViewById( R.id.tv_redpacket_type);
    }

    @Override
    protected void onSetUpView() {
        mType = message.getIntAttribute( IMConstants.MESSAGE_ATTR_TYPE,-1);
        int redpacketStatus=message.getIntAttribute( IMConstants.MESSAGE_ATTR_REDPACKET_STATUS,0);
        if(redpacketStatus == 0){
            if (message.direct() == EMMessage.Direct.RECEIVE ){
                if(mType== IMConstants.MSG_TYPE_NIUNIU_REDPACKET ){
                    bubble.setBackgroundResource( R.drawable.ic_receive_cow_nor);
                }else if(mType== IMConstants.MSG_TYPE_WELFARE_REDPACKET){
                    bubble.setBackgroundResource( R.drawable.ic_receive_reward_nor);
                }else{
                    bubble.setBackgroundResource( R.drawable.ic_receive_red_nor);
                }
            }else {
                if(mType== IMConstants.MSG_TYPE_NIUNIU_REDPACKET){
                    bubble.setBackgroundResource( R.drawable.ic_send_cow_nor);
                }else if(mType== IMConstants.MSG_TYPE_WELFARE_REDPACKET){
                    bubble.setBackgroundResource( R.drawable.ic_send_reward_nor);
                }else{
                    bubble.setBackgroundResource( R.drawable.ic_send_red_nor);
                }
            }
            tvRedpacketStatus.setText( R.string.check_redpacket);
            tvCenterMessage.setText( R.string.check_redpacket);
        }else{
            if (message.direct() == EMMessage.Direct.RECEIVE ){
                if(mType== IMConstants.MSG_TYPE_NIUNIU_REDPACKET){
                    bubble.setBackgroundResource( R.drawable.ic_receive_cow_sel);
                }else if(mType== IMConstants.MSG_TYPE_WELFARE_REDPACKET){
                    bubble.setBackgroundResource( R.drawable.ic_receive_reward_sel);
                }else{
                    bubble.setBackgroundResource( R.drawable.ic_receive_red_sel);
                }
            }else {
                if(mType== IMConstants.MSG_TYPE_NIUNIU_REDPACKET){
                    bubble.setBackgroundResource( R.drawable.ic_send_cow_sel);
                }else if(mType== IMConstants.MSG_TYPE_WELFARE_REDPACKET){
                    bubble.setBackgroundResource( R.drawable.ic_send_reward_sel);
                }else{
                    bubble.setBackgroundResource( R.drawable.ic_send_red_sel);
                }
            }
            if(redpacketStatus == 1){
                tvRedpacketStatus.setText( R.string.redpacket_already_received);
                tvCenterMessage.setText( R.string.redpacket_already_received);
            }else if(redpacketStatus == 2){
                tvRedpacketStatus.setText( R.string.redpackets_have_expired);
                tvCenterMessage.setText( R.string.redpackets_have_expired);
            } else{
                tvRedpacketStatus.setText( R.string.redpackets_are_gone);
                tvCenterMessage.setText( R.string.redpackets_are_gone);
            }
        }
        tvRedpacketStatus.setVisibility(mType== IMConstants.MSG_TYPE_WELFARE_REDPACKET ? INVISIBLE : VISIBLE);
        tvMessage.setVisibility(mType== IMConstants.MSG_TYPE_WELFARE_REDPACKET ? INVISIBLE : VISIBLE);
        tvCenterMessage.setVisibility(mType!= IMConstants.MSG_TYPE_WELFARE_REDPACKET ? INVISIBLE : VISIBLE);

        String sRedpacketInfo =message.getStringAttribute( IMConstants.MESSAGE_ATTR_CONENT,"");
        if(!TextUtils.isEmpty(sRedpacketInfo)){
            RedpacketBean redpacketBean = new Gson().fromJson(sRedpacketInfo, RedpacketBean.class);
            if(null != usernickView)
                usernickView.setText(redpacketBean.getNickname());
            if(null != userAvatarView) {
                ImageLoader.displayHeaderImage ( context, redpacketBean.getAvatarUrl(), userAvatarView );
            }
            if(mType== IMConstants.MSG_TYPE_MINE_REDPACKET){
                tvMessage.setText(String.format("%.0f-%s",redpacketBean.getMoney(),redpacketBean.getBoomNumbers()));
                tvRedpacketType.setText( R.string.mine_redpacket);

            }else if(mType== IMConstants.MSG_TYPE_WELFARE_REDPACKET){
//                tvMessage.setText(String.format("%.0f-%d",redpacketBean.getMoney(),redpacketBean.getNumber()));
                tvRedpacketType.setText( R.string.welfare_redpacket);

            }else if(mType== IMConstants.MSG_TYPE_NIUNIU_REDPACKET){
                tvMessage.setText(String.format("%.0f-%d",redpacketBean.getMoney(),redpacketBean.getNumber()));
                tvRedpacketType.setText( R.string.niuniu_redpacket);

            }else if(mType== IMConstants.MSG_TYPE_GUN_CONTROL_REDPACKET){
                tvMessage.setText(String.format("[%s]",redpacketBean.getBoomNumbers()));
                tvRedpacketType.setText( R.string.gun_control_redpacket);
            }
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
                    ackedView.setText(String.format(getContext().getString(com.hyphenate.easeui.R.string.group_ack_read_count), count));
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
            ackedView.setText(String.format(getContext().getString(com.hyphenate.easeui.R.string.group_ack_read_count), count));
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
