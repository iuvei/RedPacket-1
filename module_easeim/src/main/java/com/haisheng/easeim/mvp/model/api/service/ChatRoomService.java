package com.haisheng.easeim.mvp.model.api.service;

import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ChatRoomService {

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=Game&do=Apis&m=sz_yi&op=messlist")
    Observable<BaseResponse<List<ChatRoomBean>>> messageList(
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=Game&do=Apis&m=sz_yi&op=grouplist")
    Observable<BaseResponse<List<ChatRoomBean>>> roomList(
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=Game&do=Apis&m=sz_yi&op=getgrouplist")
    Observable<BaseResponse<List<UserInfo>>> roomUserList(
            @Field("token") String token,
            @Field("roomid") Long roomId,
            @Field("page") int pageNumber

    );

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=Game&do=Apis&m=sz_yi&op=groupdetail")
    Observable<BaseResponse<ChatRoomBean>> roomDetail(
            @Field("token") String token,
            @Field("roomid") String roomId,
            @Field("hxgroupid") String hxgroupid
    );


    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=Game&do=Apis&m=sz_yi&op=logoutgroup")
    Observable<BaseResponse> quitRoom(
            @Field("token") String token,
            @Field("roomid") Long roomId
    );


}
