package com.haisheng.easeim.mvp.contract;

import android.app.Activity;

import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/01/2019 16:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface GroupInfoContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setChatRoomInfo(ChatRoomBean chatRoomInfo);
        void quitSuccessful();
        Activity getActivity();

        void setRoomNickNameSuccess(String nickname);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

    }
}
