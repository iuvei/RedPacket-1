package com.haisheng.easeim.mvp.ui.widget.message;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haisheng.easeim.R;
import com.hyphenate.easeui.utils.IMConstants;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

public class ChatHelpMessage extends EaseChatRow {

    private TextView tvContent;

    public ChatHelpMessage(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(R.layout.row_received_help_message , this);
    }

    @Override
    protected void onFindViewById() {
        tvContent = findViewById( R.id.tv_content);
    }


    @Override
    protected void onViewUpdate(EMMessage msg) {

    }

    @Override
    protected void onSetUpView() {
        String  getContent = message.getStringAttribute ( IMConstants.MESSAGE_ATTR_CONENT,"");
        tvContent.setText ( getContent );
    }


}
