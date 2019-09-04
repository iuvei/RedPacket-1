package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.utils.EaseImageUtils;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

public class CustomChatRowGiftAsk extends EaseChatRow{

	private TextView tvGiftName;
	private ImageView imgGift;

    public CustomChatRowGiftAsk(Context context, EMMessage message, int position, BaseAdapter adapter) {
		super(context, message, position, adapter);
	}

	@Override
	protected void onInflateView() {
		inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
				R.layout.custom_row_received_gift_ask : R.layout.custom_row_sent_gift_ask, this);
	}

	@Override
	protected void onFindViewById() {
        tvGiftName = (TextView) findViewById(R.id.tvGiftName);
        imgGift = (ImageView) findViewById(R.id.imgGift);
	}

    @Override
    public void onSetUpView() {
//        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
//        Spannable span = EaseSmileUtils.getSmiledText(context, txtBody.getMessage());
//        // 设置内容
//        contentView.setText(span, BufferType.SPANNABLE);
        try {
            String giftName = message.getStringAttribute(EaseConstant.MESSAGE_ATTR_GIFT_NAME);
            String iconUrl = message.getStringAttribute(EaseConstant.MESSAGE_ATTR_GIFT_ICON_URL);

            tvGiftName.setText(giftName);
            EaseImageUtils.display2PlaceholderImage(context,iconUrl,R.drawable.ease_default_image,imgGift);
            if(message.direct() == EMMessage.Direct.RECEIVE){
                tvGiftName.setText(String.format(context.getString(R.string.asks_you_a_gift),giftName));
            }else{
                tvGiftName.setText(String.format(context.getString(R.string.you_asks_a_gift),giftName));
            }

        } catch (HyphenateException e) {
            e.printStackTrace();
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
