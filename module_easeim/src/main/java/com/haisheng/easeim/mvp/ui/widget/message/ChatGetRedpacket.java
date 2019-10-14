package com.haisheng.easeim.mvp.ui.widget.message;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haisheng.easeim.R;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.mvp.model.entity.RedpacketBean;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

import org.simple.eventbus.EventBus;

import me.jessyan.armscomponent.commonres.utils.ConvertNumUtils;
import me.jessyan.armscomponent.commonres.utils.SpUtils;

public class ChatGetRedpacket extends EaseChatRow {

    private TextView getUser,sendUser,tvRedPacket;
    private LinearLayout ll_msg;
    private RedpacketBean redpacketBean;

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
        ll_msg = findViewById( R.id.ll_packetMsg);
        tvRedPacket = findViewById( R.id.tv_redpacket);
    }


    @Override
    protected void onViewUpdate(EMMessage msg) {

    }

    @Override
    protected void onSetUpView() {
        String   getName = message.getStringAttribute ( IMConstants.GET_REDPACKET_MSG_GETNAME,"");
        String   getHXID = message.getStringAttribute ( IMConstants.GET_REDPACKET_MSG_GETHXID,"");
        String  sendName = message.getStringAttribute ( IMConstants.GET_REDPACKET_MSG_SENDNAME,"");
        String  sendGHXID = message.getStringAttribute ( IMConstants.GET_REDPACKET_MSG_SENDHXID,"");
        String  redId = message.getStringAttribute ( IMConstants.REDPACKET_MSG_REDID,"");
        redpacketBean = new RedpacketBean ();
        redpacketBean.setId ( ConvertNumUtils.stringToLong ( redId ) );
        String hxid = SpUtils.getValue ( context,"hxid","" );
        if (hxid.equals ( getHXID )){
            //你领取了红包
            getUser.setText ( "你" );
            if (hxid.equals ( sendGHXID )){
                //领取了自己发的包
                sendUser.setText ( "自己发的" );
            }else{
                //领取了别人发的包
                sendUser.setText ( sendName+"的");
            }
        }else{
            //别人领取了红包
            if (hxid.equals ( sendGHXID )){
                //领取了自己发的包
                sendUser.setText ( "你的" );
                getUser.setText ( getName );
            }else{
                //别人领取了别人的包，不显示信息
                ll_msg.setVisibility ( GONE );
            }
        }

        tvRedPacket.setOnClickListener ( new OnClickListener () {
            @Override
            public void onClick(View view) {
                EventBus.getDefault ().post ( redpacketBean,"onSetUpView" );
            }
        } );
    }


}
