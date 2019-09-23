package com.haisheng.easeim.mvp.model.api.service;

import com.haisheng.easeim.mvp.model.entity.ContactInfo;
import com.haisheng.easeim.mvp.model.entity.PublicResponseBean;
import com.haisheng.easeim.mvp.model.entity.RoomBean;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
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

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=checkfriend")
    Observable<BaseResponse<List<UserInfo>>> searchContact(
            @Field("token") String token,
            @Field("checkfriend") String field
    );

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=serverlist")
    Observable<BaseResponse<List<UserInfo>>> serverList(
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=Other&do=Apis&m=sz_yi&op=serverurl")
    Observable<BaseResponse<String>> serverUrl(
            @Field("token") String token
    );


    /**
     *
     * 删除好友
     * token	是	string	无
     * fuid	是	String	好友id
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=delfriend")
    Observable<PublicResponseBean> delFriend(
            @Field("token") String token,
            @Field("fuid") String fuid
    );

    /**
     * 设置备注
     * token	是	string	无
     * remark	是	string	备注
     * fuid	否	string	好友id
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=setremark")
    Observable<PublicResponseBean> setRemark(
            @Field("token") String token,
            @Field("remark") String remark,
            @Field("fuid") String fuid
    );




}
