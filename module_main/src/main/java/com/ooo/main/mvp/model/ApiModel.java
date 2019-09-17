package com.ooo.main.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.ooo.main.mvp.model.api.Api;
import com.ooo.main.mvp.model.api.service.ApiService;
import com.ooo.main.mvp.model.api.service.RedPacketGameService;
import com.ooo.main.mvp.model.entity.RedPacketGameRomeBean;
import com.ooo.main.mvp.model.entity.UnderPayerBean;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;

/**
 * 网络请求model
 */
public class ApiModel extends BaseModel{

    public ApiModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<UnderPayerBean> getUnderLineList(String status, String fuid, String page) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .getUnderLineList (token,status,fuid,page);
    }

}