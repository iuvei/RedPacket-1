package com.haisheng.easeim.mvp.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haisheng.easeim.R;

import java.util.List;

import me.jessyan.armscomponent.commonres.utils.ImageLoader;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;

public class UserGridAdapter extends BaseQuickAdapter <UserInfo, BaseViewHolder> {

    public UserGridAdapter(List<UserInfo> infos) {
        super( R.layout.item_user_grid, infos);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserInfo item) {
        helper.setText( R.id.tv_nickname,item.getNickname());
        ImageView imgHead = helper.getView( R.id.iv_avatar);
        ImageLoader.displayHeaderImage(mContext,item.getAvatarUrl(),imgHead);
    }

}
