package com.ooo.main.mvp.ui.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ooo.main.R;

import java.util.List;

import me.jessyan.armscomponent.commonres.adapter.BaseListAdapter;
import me.jessyan.armscomponent.commonsdk.entity.BannerEntity;

/**
 * 0
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
            viewHolder.ivAnimal = convertView.findViewById ( R.id.iv_animal );
            convertView.setTag ( viewHolder );
        }else{
            viewHolder = (ViewHolder) convertView.getTag ();
        }
        Glide.with ( parent.getContext () )
                .load ( list.get ( position ).getImageUrl () )
                .into ( viewHolder.imageView );
        Log.e ( "tag","viewHolder.imageView="+viewHolder.imageView.getVisibility () );
        viewHolder.imageView.post ( new Runnable () {
            @Override
            public void run() {
                // 平移动画
                Animation translate = new TranslateAnimation (0,-viewHolder.imageView.getWidth (),0f,0f);
                translate.setDuration( (long) (Math.random ()*1500+2500) );
                translate.setRepeatCount ( -1 );
                viewHolder.ivAnimal.startAnimation ( translate );
            }
        } );
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        ImageView ivAnimal;
    }
}
