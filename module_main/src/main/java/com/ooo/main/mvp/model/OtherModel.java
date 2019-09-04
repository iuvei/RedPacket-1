package com.ooo.main.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.ooo.main.mvp.model.api.service.OtherService;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.entity.AdBannerInfo;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;


public class OtherModel extends BaseModel{

    public OtherModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<BaseResponse<AdBannerInfo>> adBannerInfo() {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(OtherService.class)
                .adBannerInfo();
    }

}