package com.hyphenate.easeui.widget.presenter;

import android.content.Context;
import android.widget.BaseAdapter;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.widget.chatrow.CustomChatRowGift;
import com.hyphenate.easeui.widget.chatrow.CustomChatRowGiftAsk;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.exceptions.HyphenateException;

public class CustomChatGiftAskPresenter extends EaseChatRowPresenter {
    private static final String TAG = "CustomChatCallInvitePresenter";

    @Override
    protected EaseChatRow onCreateChatRow(Context cxt, EMMessage message, int position, BaseAdapter adapter) {
        return new CustomChatRowGiftAsk(cxt, message, position, adapter);
    }

    @Override
    public void onBubbleClick(EMMessage message) {
        super.onBubbleClick(message);
    }

    @Override
    protected void handleReceiveMessage(EMMessage message) {
        if (!message.isAcked() && message.getChatType() == EMMessage.ChatType.Chat) {
            try {
                EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
            return;
        }

        // Send the group-ack cmd type msg if this msg is a ding-type msg.
        EaseDingMessageHelper.get().sendAckMessage(message);
    }
}
