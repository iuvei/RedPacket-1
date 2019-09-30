package com.ooo.main.mvp.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ooo.main.R;

import java.util.List;

import me.jessyan.armscomponent.commonres.adapter.BaseListAdapter;

/**
 * 0
 * creat at 2019/9/28
 * description
 */
public class ChooseBlankAdapter extends BaseListAdapter<String> {


    public ChooseBlankAdapter(List <String> list) {
        super ( list );
    }

    public void setData(List<String> list){
        this.list = list;
        notifyDataSetChanged ();
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder ();
            convertView = View.inflate ( parent.getContext (), R.layout.item_choose_bank,null );
            viewHolder.textView = convertView.findViewById ( R.id.tv_blank );
            convertView.setTag ( viewHolder );
        }else{
            viewHolder = (ViewHolder) convertView.getTag ();
        }
        viewHolder.textView.setText ( list.get ( position ) );
        return convertView;
    }

    class ViewHolder{
        private TextView textView;
    }
}
