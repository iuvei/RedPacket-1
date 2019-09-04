package com.haisheng.easeim.mvp.model.api.service;

import com.haisheng.easeim.mvp.model.entity.RoomBean;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface RedpacketService {

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=Game&do=Apis&m=sz_yi&op=grouplist")
    Observable<BaseResponse<List<RoomBean>>> roomList(
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=Game&do=Apis&m=sz_yi&op=groupdetail")
    Observable<BaseResponse> roomDetail(
            @Field("token") String token,
            @Field("roomid") Long roomId
    );


}
