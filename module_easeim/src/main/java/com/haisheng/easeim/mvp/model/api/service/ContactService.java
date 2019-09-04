package com.haisheng.easeim.mvp.model.api.service;

import com.haisheng.easeim.mvp.model.entity.ContactInfo;
import com.haisheng.easeim.mvp.model.entity.RoomBean;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ContactService {

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=addressbook")
    Observable<BaseResponse<ContactInfo>> contactList(
            @Field("token") String token,
            @Field("page") int pageNumber
    );


}
