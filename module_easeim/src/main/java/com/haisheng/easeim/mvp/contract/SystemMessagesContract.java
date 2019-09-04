package com.haisheng.easeim.mvp.contract;

import android.app.Activity;

import com.haisheng.easeim.mvp.model.entity.SystemMessage;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;


public interface SystemMessagesContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Activity getActivity();
        void showLoadView();
        void showContent();
        void showEmptyView();
        void showErrorView();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<List<SystemMessage>>> getSystemMessages();
    }
}
