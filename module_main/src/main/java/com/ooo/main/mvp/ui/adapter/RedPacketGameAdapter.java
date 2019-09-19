package com.ooo.main.mvp.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ooo.main.R;

import java.util.List;

import me.jessyan.armscomponent.commonres.adapter.BaseListAdapter;
import me.jessyan.armscomponent.commonsdk.entity.BannerEntity;

/**
 * @author lanjian
 * creat at 2019/9/17
 * description
 * 红包游戏的adapter
 */
public class RedPacketGameAdapter extends BaseListAdapter <BannerEntity> {

    public RedPacketGameAdapter(List <BannerEntity> list) {
        super ( list );
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder ();
            convertView = View.inflate ( parent.getContext (), R.layout.item_redpacket_game,null );
            viewHolder.imageView = convertView.findViewById ( R.id.iv_game );
            convertView.setTag ( viewHolder );
        }else{
            viewHolder = (ViewHolder) convertView.getTag ();
        }
        Glide.with ( parent.getContext () ).load ( list.get ( position ).getImageUrl () ).into ( viewHolder.imageView );
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
    }
}
