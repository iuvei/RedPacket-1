package com.haisheng.easeim.mvp.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haisheng.easeim.R;
import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.List;

import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonsdk.core.Constants;

public class RoomListAdapter extends BaseQuickAdapter<ChatRoomBean,BaseViewHolder> {

    private int typeStatus;

    public RoomListAdapter(List<ChatRoomBean> infos) {
        super(R.layout.item_room, infos);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatRoomBean item) {
        ImageView ivRoom = helper.getView(R.id.iv_room);
        ImageLoader.displayImage(mContext,item.getImgUrl(),R.drawable.ic_launcher,R.drawable.ic_launcher,ivRoom);
        helper.setText(R.id.tv_room_name,item.getName());
        if(typeStatus == Constants.IM.TYPE_MESSAGE){
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(item.getHxId());
            if(null != conversation){
                int unreadCount = conversation.getUnreadMsgCount();
                if(unreadCount>0){
                    helper.setVisible(R.id.iv_round_red,true);
                    String sUnreadCount = unreadCount > 99 ? "99+": String.valueOf(unreadCount);
                    helper.setText(R.id.tv_room_describe,mContext.getString(R.string.has_unread_mssages,sUnreadCount));

                }else{
                    helper.setVisible(R.id.iv_round_red,false);
                    helper.setText(R.id.tv_room_describe,mContext.getString(R.string.no_unread_messages));
                }
            }else{
                helper.setVisible(R.id.iv_round_red,false);
                helper.setText(R.id.tv_room_describe,mContext.getString(R.string.no_unread_messages));
            }

        }else{
            helper.setText(R.id.tv_room_describe,item.getDescribe());
        }
    }

    public void setTypeStatus(int typeStatus) {
        this.typeStatus = typeStatus;
    }
}
