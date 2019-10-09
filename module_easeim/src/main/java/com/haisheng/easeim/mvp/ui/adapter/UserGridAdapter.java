package com.haisheng.easeim.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haisheng.easeim.R;
import com.haisheng.easeim.mvp.model.entity.GroupListBean;

import java.util.List;

import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;

public class UserGridAdapter extends BaseQuickAdapter <GroupListBean.ResultBean, BaseViewHolder> {

    public UserGridAdapter(List<GroupListBean.ResultBean> infos) {
        super( R.layout.item_user_grid, infos);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupListBean.ResultBean item) {
        if (TextUtils.isEmpty ( item.getNickname () )&& TextUtils.isEmpty ( item.getAvatar () )){
            ImageView imgHead = helper.getView ( R.id.iv_avatar );
            Glide.with ( mContext ).load ( R.drawable.ic_talk_add ).into ( imgHead );
            imgHead.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    if (addGroupContactListener!=null){
                        addGroupContactListener.onAddGroupContact ();
                    }
                }
            } );

        }else {
            helper.setText ( R.id.tv_nickname, item.getNickname () );
            ImageView imgHead = helper.getView ( R.id.iv_avatar );
            ImageLoader.displayHeaderImage ( mContext, item.getAvatar (), imgHead );
        }
    }

    public interface AddGroupContactListener{
        public void onAddGroupContact();
    }

    private AddGroupContactListener addGroupContactListener;

    public void setAddGroupContactListener(AddGroupContactListener addGroupContactListener) {
        this.addGroupContactListener = addGroupContactListener;
    }
}
