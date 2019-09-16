package com.ooo.main.mvp.model.api.service;

import com.ooo.main.mvp.model.entity.LoginResultInfo;
import com.ooo.main.mvp.model.entity.MemberInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.entity.BannerEntity;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

public interface MemberService {

    @POST("index.php?i=96&c=entry&m=wx_shop&do=mobile&r=account_app.login")
    @FormUrlEncoded
    Observable<BaseResponse<List<BannerEntity>>> loginAd();

    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginResultInfo>> login(
            @Field("mobile") String phoneNumber,
            @Field("password") String password
    );

    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=forgetpwd")
    @FormUrlEncoded
    Observable<BaseResponse> forgotPassword(
            @Field("mobile") String phoneNumber,
            @Field("code") String verificationCode,
            @Field("password") String password

    );

    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=sendcode")
    @FormUrlEncoded
    Observable<BaseResponse> sendSms(
            @Field("mobile") String phoneNumber,
            @Field("isres") String isRegister
    );

    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=register")
    @FormUrlEncoded
    Observable<BaseResponse<LoginResultInfo>> register(
            @Field("mobile") String phoneNumber,
            @Field("password") String password,
            @Field("code") String verificationCode,
            @Field("incode") String invitationCode
    );

    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=getinfo")
    @FormUrlEncoded
    Observable<BaseResponse<MemberInfo>> getMemberInfo(
            @Field("token") String token
    );

    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=updateinfo")
    @FormUrlEncoded
    Observable<BaseResponse> updateMemberInfo(
            @Field("token") String token,
            @Field("nickname") String nickname,
            @Field("avatar") String avatarUrl,
            @Field("gender") int sex,
            @Field("password") String password,
            @Field("code") String authCode
    );

    @Multipart
    @POST("index.php?i=1&c=entry&p=uploader&do=Apis&m=sz_yi")
    Observable<BaseResponse<String>> updateAvatarUrl(
            @QueryMap Map<String,String> map, @Part MultipartBody.Part file
    );

    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=checkfriend")
    @FormUrlEncoded
    Observable<BaseResponse> searchFriend(
            @Field("token") String token,
            @Field("checkfriend") String keyword
    );

    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=addressbook")
    @FormUrlEncoded
    Observable<BaseResponse> getAddressBook(
            @Field("token") String token,
            @Field("page") int pageNumber
    );

    @POST("index.php?i=1&c=entry&p=uploader&do=Apis&m=sz_yi")
    @FormUrlEncoded
    Observable<BaseResponse> upLoadPic(
            @Field("token") String token,
            @Field("file") String file
    );

}
