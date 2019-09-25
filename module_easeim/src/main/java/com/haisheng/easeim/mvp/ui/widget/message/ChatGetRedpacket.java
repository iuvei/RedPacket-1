package com.haisheng.easeim.mvp.ui.widget.message;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haisheng.easeim.R;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.mvp.model.entity.RedpacketBean;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

import java.util.List;

import me.jessyan.armscomponent.commonres.utils.ImageLoader;

public class ChatGetRedpacket extends EaseChatRow {

    private TextView getUser,sendUser;

    public ChatGetRedpacket(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(R.layout.row_received_get_redpacket , this);
    }

    @Override
    protected void onFindViewById() {
        getUser = findViewById( R.id.tv_get_user);
        sendUser = findViewById( R.id.tv_send_user);
    }


    @Override
    protected void onViewUpdate(EMMessage msg) {
        String   isMyinfo = msg.getStringAttribute ( IMConstants.GET_REDPACKET_IS_MYINFO,"");
        String  msgName = msg.getStringAttribute ( IMConstants.GET_REDPACKET_MSG_NAME,"");
        if (isMyinfo.equals ( "1" )){
            //你领取了自己的红包
            getUser.setText ( "你" );
            sendUser.setText ( "自己发的" );
        }else{
            //你领取了xxx的红包
            getUser.setText ( "你" );
            sendUser.setText ( msgName+"的" );
        }
    }

    @Override
    protected void onSetUpView() {

    }


}
