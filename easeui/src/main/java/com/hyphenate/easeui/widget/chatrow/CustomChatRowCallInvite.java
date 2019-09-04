package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.utils.EaseSmileUtils;

import java.util.List;

public class CustomChatRowCallInvite extends EaseChatRow{

	private TextView tvInvitHit,tvAccept;
	private ImageView imgChatType;

    public CustomChatRowCallInvite(Context context, EMMessage message, int position, BaseAdapter adapter) {
		super(context, message, position, adapter);
	}

	@Override
	protected void onInflateView() {
		inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
				R.layout.custom_row_received_call_invite : R.layout.custom_row_sent_call_invite, this);
	}

	@Override
	protected void onFindViewById() {
        tvInvitHit = (TextView) findViewById(R.id.tvInvitHit);
        tvAccept = (TextView) findViewById(R.id.tvAccept);
        imgChatType = (ImageView) findViewById(R.id.imgChatType);
	}

    @Override
    public void onSetUpView() {
//        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
//        Spannable span = EaseSmileUtils.getSmiledText(context, txtBody.getMessage());
//        // 设置内容
//        contentView.setText(span, BufferType.SPANNABLE);
        int inviteType = message.getIntAttribute(EaseConstant.MESSAGE_ATTR_CALL_INVITE_TYPE,EaseConstant.CALL_TYPE_VOICE);
        if(EaseConstant.CALL_TYPE_VIDEO == inviteType){
            tvInvitHit.setText(R.string.send_voice_invite);
            imgChatType.setImageResource(R.mipmap.message_icon_call_line);
        }else{
            tvInvitHit.setText(R.string.send_video_invite);
            imgChatType.setImageResource(R.mipmap.message_icon_video_line_left);
        }

        if(message.direct() == EMMessage.Direct.RECEIVE){
            boolean isCall = message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_CALL_ALREADY,false);
            if(isCall){
                tvAccept.setText(R.string.call_already);
                tvAccept.setTextColor(ContextCompat.getColor(context,R.color.text_light_color));
            }else{
                tvAccept.setText(R.string.immediately_call);
                tvAccept.setTextColor(ContextCompat.getColor(context,R.color.text_color));
            }
        }else{
            tvAccept.setText(R.string.chat_up);
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
                    ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), count));
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
            ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), count));
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
