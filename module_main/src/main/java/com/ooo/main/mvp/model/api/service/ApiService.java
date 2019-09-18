package com.ooo.main.mvp.model.api.service;

import com.ooo.main.mvp.model.entity.AddBlankCardBean;
import com.ooo.main.mvp.model.entity.BillingDetailBean;
import com.ooo.main.mvp.model.entity.BlankCardBean;
import com.ooo.main.mvp.model.entity.RedPacketGameRomeBean;
import com.ooo.main.mvp.model.entity.UnderPayerBean;
import com.ooo.main.mvp.model.entity.WithRecordBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiService {

    /**
     * 获取下级列表
     * token	是	string	无
     * status	否	Int	用户级别	0全部1代理用户2会员用户
     * fuid	否	string	无	//查询账号
     * page	是	int	页数，默认1，针对返回list
     */
    @POST("index.php?i=1&c=entry&p=Agency&do=Apis&m=sz_yi&op=levellist")
    @FormUrlEncoded
    Observable<UnderPayerBean> getUnderLineList(
            @Field("token") String token,
            @Field("status") String status,
            @Field("fuid") String fuid,
            @Field("page") String page
            );

    /**
     *
     * 查看账单明细
     * token	是	string	无
     * time1	是	string	开始时间
     * time2	是	string	结束时间
     * page	是	Int	页数
     * @return
     */
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=allrecord")
    @FormUrlEncoded
    Observable<BillingDetailBean> getBillingDetails(
            @Field("token") String token,
            @Field("time1") String time1,
            @Field("time2") String time2,
            @Field("page") String page
            );


    /**
     *
     * 查看提现记录
     * token	是	string	无
     * time1	是	string	无
     * time2	是	string	无
     * page	是	string	无
     * paytype	是	string	无
     * @return
     */
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=withrecord")
    @FormUrlEncoded
    Observable<WithRecordBean> getWithRecord(
            @Field("token") String token,
            @Field("time1") String time1,
            @Field("time2") String time2,
            @Field("page") String page,
            @Field("paytype") String paytype
            );


    /**
     *
     * 查看银行卡列表
     * token	是	string	无
     * @return
     */
    @POST("index.php?i=1&c=entry&p=Cashvalue&do=Apis&m=sz_yi&op=cardlist")
    @FormUrlEncoded
    Observable<BlankCardBean> getBlankCardList(
            @Field("token") String token
            );
    /**
     *
     * 添加银行卡
     * token	是	string	无
     * cardname	是	String	持卡人
     * cardcode	是	String	卡号
     * cardopen	是	String	开户行
     * cardaddress	是	String	开户地址
     * type	是	int	1为借记卡，2为非借记卡
     * @return
     */
    @POST("index.php?i=1&c=entry&p=Cashvalue&do=Apis&m=sz_yi&op=addcard")
    @FormUrlEncoded
    Observable<AddBlankCardBean> addBlankCard(
            @Field("token") String token,
            @Field("cardname") String cardname,
            @Field("cardcode") String cardcode,
            @Field("cardopen") String cardopen,
            @Field("cardaddress") String cardaddress,
            @Field("type") String type
            );



}
