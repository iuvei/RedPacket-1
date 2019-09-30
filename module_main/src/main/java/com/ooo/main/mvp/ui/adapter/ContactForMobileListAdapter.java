package com.ooo.main.mvp.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haisheng.easeim.R;
import com.ooo.main.mvp.model.entity.ContactForMobileBean;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonres.adapter.BaseListAdapter;

/**
 * creat at 2019/9/19
 * description
 */
public class ContactForMobileListAdapter extends BaseListAdapter <ContactForMobileBean.ResultBean> {

    private List<Integer> positions = new ArrayList <> (  );
    // 字母数组

    public static String[] bArray = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",

            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    public ContactForMobileListAdapter(List <ContactForMobileBean.ResultBean> list) {
        super ( list );
        isFirstPosition();
    }

    public void setData(List <ContactForMobileBean.ResultBean> list){
        this.list = list;
        isFirstPosition();
        notifyDataSetChanged ();
    }

    public void addData(List <ContactForMobileBean.ResultBean> list){
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
        for (int i=0;i<list.size();i++){

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
            convertView = View.inflate ( parent.getContext (), R.layout.item_contact_for_mobile,null );
            viewHolder.ivHeadPic = convertView.findViewById ( R.id.iv_avatar );
            viewHolder.tvNickName = convertView.findViewById ( R.id.tv_nickname );
            viewHolder.tvPhone = convertView.findViewById ( R.id.tv_phone );
            viewHolder.tvLetter = convertView.findViewById ( R.id.tv_header_letter );
            convertView.setTag ( viewHolder );
        }else{
            viewHolder = (ViewHolder) convertView.getTag ();
        }
        Glide.with ( parent.getContext () ).load ( list.get ( position ).getAvatar () ).into ( viewHolder.ivHeadPic );
        if(positions.contains ( position )){
            viewHolder.tvLetter.setText ( bArray[positions.indexOf ( position )] );
            viewHolder.tvLetter.setVisibility ( View.VISIBLE );
        }else{
            viewHolder.tvLetter.setText ( "" );
            viewHolder.tvLetter.setVisibility ( View.GONE );
        }
        viewHolder.tvNickName.setText ( list.get ( position ).getNickname () );
        viewHolder.tvPhone.setText ( list.get ( position ).getMobile () );
        return convertView;
    }

    class ViewHolder{
        ImageView ivHeadPic;
        TextView tvNickName;
        TextView tvPhone;
        TextView tvLetter;
    }
}
