package com.haisheng.easeim.mvp.model.api.service;

import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.haisheng.easeim.mvp.model.entity.CheckPayPasswordBean;
import com.haisheng.easeim.mvp.model.entity.CheckRedpacketInfo;
import com.haisheng.easeim.mvp.model.entity.RedpacketBean;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface RedpacketService {

    /**
     * 发包
     * token	是	string	无
     * gold	是	int	发包金额
     * roomid	否	int	房间ID
     * boom	否	int	中雷尾数	如1 如果是多雷 1,2 /福利包或牛牛包可不传
     * welfare	否	int	是否福利包默认0	是否福利包0否1是
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=Game&do=Apis&m=sz_yi&op=setred")
    Observable<BaseResponse<RedpacketBean>> sendRedpacket(
            @Field("token") String token,
            @Field("roomid") Long roomId,
            @Field("boom") String booms,
            @Field("nums") int redpacketNumber,
            @Field("gold") double money,
            @Field("welfare") int welfareStatus,
            @Field("passpwd") String password
    );

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=Game&do=Apis&m=sz_yi&op=gorobcheck")
    Observable<BaseResponse<CheckRedpacketInfo>> checkRedpacket(
            @Field("token") String token,
            @Field("roomid") Long roomId,
            @Field("setid") Long redpacketId,
            @Field("welfare") int welfareStatus
    );

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=Game&do=Apis&m=sz_yi&op=gorobnow")
    Observable<BaseResponse> grabRedpacket(
            @Field("token") String token,
            @Field("roomid") Long roomId,
            @Field("setid") Long redpacketId,
            @Field("welfare") int welfareStatus
    );

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=Game&do=Apis&m=sz_yi&op=redinfodetail")
    Observable<BaseResponse<RedpacketBean>> redpacketDetail(
            @Field("token") String token,
            @Field("roomid") Long roomId,
            @Field("setid") Long redpacketId,
            @Field("welfare") int welfareStatus
    );


    /**
     * 检测是否设置支付密码
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=Game&do=Apis&m=sz_yi&op=pwd2")
    Observable<CheckPayPasswordBean> checkPayPasswrod(
            @Field("token") String token
    );

}
