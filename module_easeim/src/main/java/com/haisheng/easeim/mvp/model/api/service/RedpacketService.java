package com.haisheng.easeim.mvp.model.api.service;

import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.haisheng.easeim.mvp.model.entity.CheckPayPasswordBean;
import com.haisheng.easeim.mvp.model.entity.CheckRedpacketInfo;
import com.haisheng.easeim.mvp.model.entity.ProfitRecordBean;
import com.haisheng.easeim.mvp.model.entity.PublicResponseBean;
import com.haisheng.easeim.mvp.model.entity.RedPacketRecordBean;
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


    /**
     * 发包记录
     * token	是	string	无
     * page	是	int	无
     * paytype	是	string	无
     */
    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=setredrecord")
    Observable<RedPacketRecordBean> getRedPacketRecord(
            @Field("token") String token,
            @Field("paytype") String paytype,
            @Field("page") int page
            );
    /**
     * 收到红包记录
     * token	是	string	无
     * page	是	int	无
     * paytype	是	string	无
     */
    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=getredrecord")
    Observable<RedPacketRecordBean> getGrapRedPacketRecord(
            @Field("token") String token,
            @Field("paytype") String paytype,
            @Field("page") int page
            );

    /**
     * 盈亏记录
     * token	是	string	无
     * time1	是	string	无
     * time2	是	string	无
     * page	是	int	无
     * paytype	是	string	无
     */
    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=profit")
    Observable<ProfitRecordBean> getProfitRecord(
            @Field("token") String token,
            @Field("time1") String time1,
            @Field("time2") String time2,
            @Field("page") int page,
            @Field("paytype") String paytype
            );

}
