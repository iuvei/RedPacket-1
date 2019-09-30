package com.ooo.main.mvp.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ooo.main.R;
import com.ooo.main.mvp.model.entity.RedPacketGameRomeBean;

import java.util.List;

import me.jessyan.armscomponent.commonres.adapter.BaseListAdapter;

/**
 * 0
 * creat at 2019/9/17
 * description
 * 红包游戏的房间列表adapter
 */
public class RedPacketGameRoomListAdapter extends BaseListAdapter <RedPacketGameRomeBean.ResultBean> {

    public RedPacketGameRoomListAdapter(List <RedPacketGameRomeBean.ResultBean> list) {
        super ( list );
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder ();
            convertView = View.inflate ( parent.getContext (), R.layout.item_redpacket_game_list,null );
            viewHolder.imageView = convertView.findViewById ( R.id.iv_game );
            viewHolder.tvTitle = convertView.findViewById ( R.id.tv_room_title );
            convertView.setTag ( viewHolder );
        }else{
            viewHolder = (ViewHolder) convertView.getTag ();
        }
        Glide.with ( parent.getContext () ).load ( list.get ( position ).getSurl () ).into ( viewHolder.imageView );
        viewHolder.tvTitle.setText ( list.get ( position ).getRoomname () );
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView tvTitle;
    }
}
