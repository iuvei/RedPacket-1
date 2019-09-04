package com.ooo.main.mvp.model.api.service;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.entity.AdBannerInfo;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import retrofit2.http.POST;


public interface OtherService {

    @POST("index.php?i=1&c=entry&p=Other&do=Apis&m=sz_yi&op=shuffling")
    Observable<BaseResponse<AdBannerInfo>> adBannerInfo();

}
