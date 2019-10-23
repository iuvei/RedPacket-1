package com.haisheng.easeim.mvp.ui.widget.message;

import android.content.Context;
import android.text.TextUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;
import com.haisheng.easeim.R;
import com.hyphenate.easeui.utils.IMConstants;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

public class ChatJoinRoomMessage extends EaseChatRow {

    private TextView tvContent;

    public ChatJoinRoomMessage(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(R.layout.row_received_joinroom_message , this);
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
        try {
            String  getContent = message.getStringAttribute ( IMConstants.MESSAGE_ATTR_JOIN_ROOM_NICKNAME,"");
            if(!TextUtils.isEmpty(getContent)){
                tvContent.setText ( getContent+"加入房间" );
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace ();
        }
    }


}
