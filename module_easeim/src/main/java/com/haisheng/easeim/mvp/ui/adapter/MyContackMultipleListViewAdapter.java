package com.haisheng.easeim.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haisheng.easeim.R;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonres.adapter.MyMultipleListViewBaseAdapter;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;

/**
 * @author lanjian
 * email 819715035@qq.com
 * creat at 2019/4/15 0015
 * description
 */
public class MyContackMultipleListViewAdapter extends MyMultipleListViewBaseAdapter <UserInfo> {

    private List<Integer> positions = new ArrayList <> (  );
    // 字母数组

    public static String[] bArray = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",

            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};



    public MyContackMultipleListViewAdapter(List<UserInfo> datas, Context context) {
        super ( datas, context );
        isFirstPosition();
    }

    public void isFirstPosition(){
        positions.clear ();
        for (int i=0;i<bArray.length;i++){
            positions.add ( getPositionForSection ( bArray[i] ) );
        }
    }

    public void setData(List <UserInfo> list){
        this.datas = list;
        isFirstPosition();
        notifyDataSetChanged ();
    }

    public int getPositionForSection(String charAt) {
        for (int i=0;i<datas.size();i++){

            if (datas.get(i).getSpelling().substring(1,2).toUpperCase ().contains(charAt)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public View getMyView(final int position, View convertView, ViewGroup parent) {
        final MyHolder holder;
        if (convertView==null){
            holder = new MyHolder ();
            convertView = View.inflate ( getContext (), R.layout.item_contact_list,null );
            holder.tvName = convertView.findViewById ( R.id.tv_nickname );
            holder.iv_avatar = convertView.findViewById ( R.id.iv_avatar );
            holder.checkBox = convertView.findViewById ( R.id.rb_contact_item);
            holder.ll_item = convertView.findViewById ( R.id.ll_item);
            convertView.setTag ( holder );
        }else{
            holder  = (MyHolder) convertView.getTag ();
        }
        holder.tvName.setText ( datas.get ( position ).getNickname ());
        Glide.with ( parent.getContext () ).load ( datas.get ( position ).getAvatarUrl () ).into ( holder.iv_avatar );
        holder.ll_item.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                setChooseForPosition ( position,!holder.checkBox.isChecked () );
            }
        } );
        holder.checkBox.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                setChooseForPosition ( position,holder.checkBox.isChecked () );
            }
        } );
        holder.checkBox.setChecked ( getChooseForPosition ( position ) );
        return convertView;
    }


    class MyHolder{
        TextView tvName;
        CheckBox checkBox;
        ImageView iv_avatar;
        LinearLayout ll_item;
    }

}
