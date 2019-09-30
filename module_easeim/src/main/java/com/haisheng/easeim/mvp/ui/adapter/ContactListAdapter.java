package com.haisheng.easeim.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haisheng.easeim.R;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonres.adapter.BaseListAdapter;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;

/**
 * 0
 * creat at 2019/9/19
 * description
 */
public class ContactListAdapter extends BaseListAdapter <UserInfo> {

    private List<Integer> positions = new ArrayList <> (  );
    // 字母数组

    public static String[] bArray = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",

            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    public ContactListAdapter(List <UserInfo> list) {
        super ( list );
        isFirstPosition();
    }

    public void setData(List <UserInfo> list){
        this.list = list;
        isFirstPosition();
        notifyDataSetChanged ();
    }

    public void removeData(){
        if (this.list!=null && list.size ()>2){
            for (int i=list.size ()-1;i>=2;i--){
                list.remove ( i );
            }
        }
        notifyDataSetChanged ();
    }

    public void addData(List <UserInfo> list){
        this.list.addAll ( list );
        isFirstPosition();
        notifyDataSetChanged ();
    }

    public void isFirstPosition(){
        positions.clear ();
        for (int i=0;i<bArray.length;i++){
            positions.add ( getPositionForSection ( bArray[i] ) );
        }
    }

    public int getPositionForSection(String charAt) {
        for (int i=2;i<list.size();i++){

            if (list.get(i).getSpelling().substring(1,2).toUpperCase ().contains(charAt)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder ();
            convertView = View.inflate ( parent.getContext (), R.layout.item_contact,null );
            viewHolder.ivHeadPic = convertView.findViewById ( R.id.iv_avatar );
            viewHolder.tvNickName = convertView.findViewById ( R.id.tv_nickname );
            viewHolder.tvMessageNum = convertView.findViewById ( R.id.unread_msg_number );
            viewHolder.tvLetter = convertView.findViewById ( R.id.tv_header_letter );
            convertView.setTag ( viewHolder );
        }else{
            viewHolder = (ViewHolder) convertView.getTag ();
        }
        Glide.with ( parent.getContext () ).load ( list.get ( position ).getAvatarUrl () ).into ( viewHolder.ivHeadPic );
        if (position == 0){
            viewHolder.tvLetter.setText ( "☆" );
            viewHolder.tvLetter.setVisibility ( View.VISIBLE );
            Glide.with ( parent.getContext () ).load ( R.drawable.ic_new_friend ).into ( viewHolder.ivHeadPic );
        }else if (position == 1){
            Glide.with ( parent.getContext () ).load ( R.drawable.ic_group_talking ).into ( viewHolder.ivHeadPic );
        }else if (positions.contains ( position )){
            viewHolder.tvLetter.setText ( bArray[positions.indexOf ( position )] );
            viewHolder.tvLetter.setVisibility ( View.VISIBLE );
        }else{
            viewHolder.tvLetter.setText ( "" );
            viewHolder.tvLetter.setVisibility ( View.GONE );
        }
        viewHolder.tvNickName.setText ( list.get ( position ).getNickname () );
        return convertView;
    }

    class ViewHolder{
        ImageView ivHeadPic;
        TextView tvNickName;
        TextView tvMessageNum;
        TextView tvLetter;
    }
}
