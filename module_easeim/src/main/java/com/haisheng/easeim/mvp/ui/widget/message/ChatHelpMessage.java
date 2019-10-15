package com.haisheng.easeim.mvp.ui.widget.message;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haisheng.easeim.R;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.mvp.model.entity.RedpacketBean;
import com.haisheng.easeim.mvp.ui.activity.ChatActivity;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

import org.simple.eventbus.EventBus;

import me.jessyan.armscomponent.commonres.utils.ConvertNumUtils;
import me.jessyan.armscomponent.commonres.utils.SpUtils;

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
