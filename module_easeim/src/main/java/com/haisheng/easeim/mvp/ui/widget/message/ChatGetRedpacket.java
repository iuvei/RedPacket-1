package com.haisheng.easeim.mvp.ui.widget.message;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.haisheng.easeim.R;
import com.hyphenate.easeui.utils.IMConstants;
import com.hyphenate.easeui.bean.GetRedPacketMessageBean;
import com.hyphenate.easeui.bean.RedpacketBean;
import com.haisheng.easeim.mvp.ui.activity.ChatActivity;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

import org.simple.eventbus.EventBus;

import me.jessyan.armscomponent.commonres.utils.CommonMethod;
import me.jessyan.armscomponent.commonres.utils.ConvertNumUtils;
import me.jessyan.armscomponent.commonres.utils.SpUtils;

public class ChatGetRedpacket extends EaseChatRow {

    private TextView getUser,sendUser,tvRedPacket,tvGetAll;
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
        tvGetAll = findViewById( R.id.tv_get_all);
    }


    @Override
    protected void onViewUpdate(EMMessage msg) {

    }

    @Override
    protected void onSetUpView() {

        try {
            String getRedpacket = message.getStringAttribute( IMConstants.MESSAGE_ATTR_CONENT,"");
            Log.e ( "tag","getRedpacket="+getRedpacket );
            if(!TextUtils.isEmpty(getRedpacket)){
                GetRedPacketMessageBean getRedPacketMessageBean = new Gson().fromJson(getRedpacket, GetRedPacketMessageBean.class);
                //谁的包
                String hxid = CommonMethod.getHxidForLocal ( );
                String sendGHXID = getRedPacketMessageBean.getHxid ();
                String getHXID = getRedPacketMessageBean.getHxid1 ();
                String getName = getRedPacketMessageBean.getNickname ();
                String sendName = getRedPacketMessageBean.getNickname1 ();
                redpacketBean = new RedpacketBean ();
                redpacketBean.setId ( ConvertNumUtils.stringToLong ( getRedPacketMessageBean.getRedid () ) );
                if (!TextUtils.isEmpty ( getRedPacketMessageBean.getNiuid () )){
                    redpacketBean.setId ( ConvertNumUtils.stringToLong ( getRedPacketMessageBean.getNiuid () ) );
                }

                ll_msg.setVisibility ( VISIBLE );
                if (hxid.equals ( getHXID )) {
                    //你领取了红包
                    getUser.setText ( "你" );
                    if (hxid.equals ( sendGHXID )) {
                        //领取了自己发的包
                        sendUser.setText ( "自己发的" );
                    } else {
                        //领取了别人发的包
                        sendUser.setText ( sendName + "的" );
                    }
                } else {
                    //别人领取了红包
                    if (hxid.equals ( sendGHXID )) {
                        //领取了自己发的包
                        sendUser.setText ( "你的" );
                        getUser.setText ( getName );
                    } else {
                        //别人领取了别人的包，不显示信息
                        ll_msg.setVisibility ( GONE );
                    }
                }
//                if (hxid.equals ( getRedPacketMessageBean.getHxid () )) {
//                    //抢包人
//                    getUser.setText ( getRedPacketMessageBean.getNickname () );
//                    sendUser.setText ( "你的" );
//                    redpacketBean = new RedpacketBean ();
//                    //红包id
//                    redpacketBean.setId (  ConvertNumUtils.stringToLong ( getRedPacketMessageBean.getRedid () ) );
//                }else if (hxid.equals ( getRedPacketMessageBean.getHxid1 ())){
//                    //自己领取了别人的包
//                    getUser.setText ( getRedPacketMessageBean.getNickname () );
//                    sendUser.setText ( "你的" );
//                    redpacketBean = new RedpacketBean ();
//                    //红包id
//                    redpacketBean.setId (  ConvertNumUtils.stringToLong ( getRedPacketMessageBean.getRedid () ) );
//                }else{
//                    //别人领取了别人的包，不显示信息
//                    ll_msg.setVisibility ( GONE );
//                }
                if (getRedPacketMessageBean.isGetAllRedPacket ()){
                    //红包已领完
                    tvGetAll.setVisibility ( VISIBLE );
                }else{
                    tvGetAll.setVisibility ( GONE );
                }

            }else {
                tvGetAll.setVisibility ( GONE );
                String getName = message.getStringAttribute ( IMConstants.GET_REDPACKET_MSG_GETNAME, "" );
                String getHXID = message.getStringAttribute ( IMConstants.GET_REDPACKET_MSG_GETHXID, "" );
                String sendName = message.getStringAttribute ( IMConstants.GET_REDPACKET_MSG_SENDNAME, "" );
                String sendGHXID = message.getStringAttribute ( IMConstants.GET_REDPACKET_MSG_SENDHXID, "" );
                String redId = message.getStringAttribute ( IMConstants.REDPACKET_MSG_REDID, "" );
                redpacketBean = new RedpacketBean ();
                redpacketBean.setId ( ConvertNumUtils.stringToLong ( redId ) );
                String hxid = CommonMethod.getHxidForLocal ( );
                if (hxid.equals ( getHXID )) {
                    //你领取了红包
                    getUser.setText ( "你" );
                    if (hxid.equals ( sendGHXID )) {
                        //领取了自己发的包
                        sendUser.setText ( "自己发的" );
                    } else {
                        //领取了别人发的包
                        sendUser.setText ( sendName + "的" );
                    }
                } else {
                    //别人领取了红包
                    if (hxid.equals ( sendGHXID )) {
                        //领取了自己发的包
                        sendUser.setText ( "你的" );
                        getUser.setText ( getName );
                    } else {
                        //别人领取了别人的包，不显示信息
                        ll_msg.setVisibility ( GONE );
                    }
                }
            }
            /**
             * 点击红包跳转到详情
             * {@link ChatActivity#clickGetRedPacket(RedpacketBean)}
             */
            ll_msg.setOnClickListener ( new OnClickListener () {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault ().post ( redpacketBean,"onSetUpView" );
                }
            } );
        } catch (JsonSyntaxException e) {
            e.printStackTrace ();
        }
    }


}
