package com.haisheng.easeim.mvp.contract;

import android.app.Activity;

import com.haisheng.easeim.mvp.model.entity.CallRecordEntity;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;


public interface ChatContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setBarTitle(String title);
        void refreshSelectLast();
        void refreshList();

        Activity getActivity();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse> fetchHistoryMessages(String toChatUsername, int chatType, int pagesize, String msgId);
        Observable<BaseResponse<List<EMMessage>>> loadMoreMsgFromDB(EMConversation conversation, String msgId, int pagesize);
    }
}
