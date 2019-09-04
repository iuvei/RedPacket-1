package com.haisheng.easeim.mvp.model.api.service;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.haisheng.easeim.mvp.model.api.Api.IM_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;


public interface ApiIMService {


//    @Headers({DOMAIN_NAME_HEADER + IM_DOMAIN_NAME})
//    @FormUrlEncoded
//    @POST("?c=account&a=register")
//    Observable<BaseResponse<BaseUserEntity>> register(
//            @Field("name") String username,
//            @Field("pwd") String password,
//            @Field("mobile") String phone,
//            @Field("code") String smsCode
//    );


}
