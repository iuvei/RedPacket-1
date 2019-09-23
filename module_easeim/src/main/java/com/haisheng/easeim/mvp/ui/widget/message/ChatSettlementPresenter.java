package com.haisheng.easeim.mvp.ui.widget.message;

import android.content.Context;
import android.widget.BaseAdapter;

import com.haisheng.easeim.app.IMConstants;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.presenter.EaseChatRowPresenter;
import com.hyphenate.exceptions.HyphenateException;

public class ChatSettlementPresenter extends EaseChatRowPresenter {

    @Override
    protected EaseChatRow onCreateChatRow(Context cxt, EMMessage message, int position, BaseAdapter adapter) {
        int type = message.getIntAttribute( IMConstants.MESSAGE_ATTR_TYPE,-1);
        if(type == IMConstants.MSG_TYPE_NIUNIU_SETTLEMENT){
            return new ChatNiuniuSettlement(cxt, message, position, adapter);
        }else{
            return new ChatGunControlSettlement(cxt, message, position, adapter);
        }
    }

    @Override
    protected void handleReceiveMessage(EMMessage message) {
        if (!message.isAcked() && message.getChatType() == EMMessage.ChatType.Chat) {
            try {
                EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }
    }
}
