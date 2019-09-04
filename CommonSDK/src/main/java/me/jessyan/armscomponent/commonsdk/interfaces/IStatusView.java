package me.jessyan.armscomponent.commonsdk.interfaces;

import com.jess.arms.mvp.IView;

public interface IStatusView{

    /**
     * 显示加载
     */
    default void showLoadView() {

    }

    /**
     * 显示空提示
     */
    default void showEmptyView() {

    }

    /**
     * 显示
     */
    default void showContent() {

    }

    /**
     * 隐藏加载
     */
    default void showErrorView() {

    }
}
