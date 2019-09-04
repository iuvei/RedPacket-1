package me.jessyan.armscomponent.commonsdk.component.im.service;

import com.alibaba.android.arouter.facade.template.IProvider;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;

public interface IMService extends IProvider {

    /**
     * 登录即时通讯服务
     * @param username
     * @param password
     * @return
     */
    Observable<BaseResponse> loginIM(String username,String password);

    /**
     * 退出即时通讯服务
     * @return
     */
    Observable<BaseResponse> logoutIM();

    /**
     * 监测是否已登录
     * @return
     */
    boolean isLoggedIn();

    /**
     * 获取即时通讯未读信息的数量
     * @return
     */
    int getUnreadIMMsgCountTotal();

}
