package com.ooo.main.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ooo.main.R;
import com.ooo.main.mvp.model.entity.PhoneContacts;

import java.util.List;

/**
 * @author lanjian
 * email 819715035@qq.com
 * creat at 2019/4/15 0015
 * description
 */
public class MyMultipleListViewAdapter extends MyMultipleListViewBaseAdapter<PhoneContacts> {



    public MyMultipleListViewAdapter(List<PhoneContacts> datas, Context context) {
        super ( datas, context );
    }

    @Override
    public View getMyView(final int position, View convertView, ViewGroup parent) {
        final MyHolder holder;
        if (convertView==null){
            holder = new MyHolder ();
            convertView = View.inflate ( getContext (), R.layout.item_phone_contact,null );
            holder.tvName = convertView.findViewById ( R.id.tv_contact_name );
            holder.tvPhone = convertView.findViewById ( R.id.tv_contact_phonenum );
            holder.checkBox = convertView.findViewById ( R.id.rb_contact_item);
            holder.rlItem = convertView.findViewById ( R.id.rl_item);
            convertView.setTag ( holder );
        }else{
            holder  = (MyHolder) convertView.getTag ();
        }
        holder.tvName.setText ( datas.get ( position ).getName());
        holder.tvPhone.setText ( datas.get ( position ).getTeleNumber ());
        holder.rlItem.setOnClickListener ( new View.OnClickListener () {
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

    public int getPositionForSection(String charAt) {
        for (int i=0;i<datas.size();i++){

            if (datas.get(i).getSpelling().substring(0,1).toUpperCase().contains(charAt)){
                return i;
            }
        }
        return -1;
    }

    class MyHolder{
        TextView tvName;
        TextView tvPhone;
        CheckBox checkBox;
        RelativeLayout rlItem;
    }

}
