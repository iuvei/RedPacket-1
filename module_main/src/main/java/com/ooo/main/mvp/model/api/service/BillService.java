package com.ooo.main.mvp.model.api.service;

import com.ooo.main.mvp.model.entity.BillBean;
import com.ooo.main.mvp.model.entity.BillRecordInfo;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BillService {

    //所有记录
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=allrecord")
    @FormUrlEncoded
    Observable<BaseResponse<BillRecordInfo>> allRecord(
            @Field("token") String token,
            @Field("time1") String startTime,
            @Field("time2") String endTime,
            @Field("page") int pageNumber
    );

    //奖励记录
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=pricerecord")
    @FormUrlEncoded
    Observable<BaseResponse<BillRecordInfo>> rewardRecord(
            @Field("token") String token,
            @Field("paytype") String type,
            @Field("time1") String startTime,
            @Field("time2") String endTime,
            @Field("page") int pageNumber
    );

    //充值记录
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=payrecord")
    @FormUrlEncoded
    Observable<BaseResponse<BillRecordInfo>> rechargeRecord(
            @Field("token") String token,
            @Field("paytype") String type,
            @Field("time1") String startTime,
            @Field("time2") String endTime,
            @Field("page") int pageNumber
    );

    //提现记录
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=withrecord")
    @FormUrlEncoded
    Observable<BaseResponse<BillRecordInfo>> withdrawRecord(
            @Field("token") String token,
            @Field("paytype") String type,
            @Field("time1") String startTime,
            @Field("time2") String endTime,
            @Field("page") int pageNumber
    );

    //发包记录
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=setredrecord")
    @FormUrlEncoded
    Observable<BaseResponse<BillRecordInfo>> sendRedpacketRecord(
            @Field("token") String token,
            @Field("paytype") String type,
            @Field("time1") String startTime,
            @Field("time2") String endTime,
            @Field("page") int pageNumber
    );

    //抢包记录
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=getredrecord")
    @FormUrlEncoded
    Observable<BaseResponse<BillRecordInfo>> grabRedpacketRecord(
            @Field("token") String token,
            @Field("paytype") String type,
            @Field("time1") String startTime,
            @Field("time2") String endTime,
            @Field("page") int pageNumber
    );

    //盈亏记录
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=profit")
    @FormUrlEncoded
    Observable<BaseResponse<BillRecordInfo>> profitLossRecord(
            @Field("token") String token,
            @Field("paytype") String type,
            @Field("time1") String startTime,
            @Field("time2") String endTime,
            @Field("page") int pageNumber
    );

    //佣金收入
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=bounprice")
    @FormUrlEncoded
    Observable<BaseResponse<BillRecordInfo>> commissionIncomeRecord(
            @Field("token") String token,
            @Field("paytype") String type,
            @Field("time1") String startTime,
            @Field("time2") String endTime,
            @Field("page") int pageNumber
    );

    //水果机收入
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=fruit")
    @FormUrlEncoded
    Observable<BaseResponse<BillRecordInfo>> fruitMachineRecord(
            @Field("token") String token,
            @Field("paytype") String type,
            @Field("time1") String startTime,
            @Field("time2") String endTime,
            @Field("page") int pageNumber
    );


}
