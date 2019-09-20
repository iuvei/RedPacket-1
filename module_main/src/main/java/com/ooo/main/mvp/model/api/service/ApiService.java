package com.ooo.main.mvp.model.api.service;

import com.ooo.main.mvp.model.entity.AddBlankCardBean;
import com.ooo.main.mvp.model.entity.AdvertisingBean;
import com.ooo.main.mvp.model.entity.AppVersionBean;
import com.ooo.main.mvp.model.entity.BillingDetailBean;
import com.ooo.main.mvp.model.entity.BlankCardBean;
import com.ooo.main.mvp.model.entity.CertificationBean;
import com.ooo.main.mvp.model.entity.DelectBlankCardBean;
import com.ooo.main.mvp.model.entity.PostersBean;
import com.ooo.main.mvp.model.entity.PublicBean;
import com.ooo.main.mvp.model.entity.RedPacketGameRomeBean;
import com.ooo.main.mvp.model.entity.TakeMoneyBean;
import com.ooo.main.mvp.model.entity.UnderPayerBean;
import com.ooo.main.mvp.model.entity.UpdatePasswordBean;
import com.ooo.main.mvp.model.entity.UserInfoFromIdBean;
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

    /**
     * 实名认证
     * token	是	string	无
     * realname	是	string	姓名
     * idnumber	否	string	身份证号
     * @return
     */
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=set_name")
    @FormUrlEncoded
    Observable<CertificationBean> setCertification(
            @Field("token") String token,
            @Field("realname") String realname,
            @Field("idnumber") String idnumber
            );

    /**
     * 解绑银行卡
     * @param token
     * @param id 银行卡数据表id
     * @return
     */
    @POST("index.php?i=1&c=entry&p=Cashvalue&do=Apis&m=sz_yi&op=del_cards")
    @FormUrlEncoded
    Observable<DelectBlankCardBean> delBlankCard(
            @Field("token") String token,
            @Field("id") String id
            );

    /**
     * 提现
     * token	是	string	无
     * goldmMoney	是	String	金额
     * cardid	是	String	银行卡id
     * @return
     */
    @POST("index.php?i=1&c=entry&p=Cashvalue&do=Apis&m=sz_yi&op=withdrawal")
    @FormUrlEncoded
    Observable<TakeMoneyBean> takeMoney(
            @Field("token") String token,
            @Field("goldmoney") String goldmoney,
            @Field("cardid") String cardid
            );

    /**
     * 修改密码
     * token	是	string	无
     * old_password	是	string	旧密码
     * password	是	string	密码
     * password_new	否	string	重复密码
     * @param token
     */
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=revise_psd")
    @FormUrlEncoded
    Observable<UpdatePasswordBean> updatePassword(
            @Field("token") String token,
            @Field("old_password") String old_password,
            @Field("password") String password,
            @Field("password_new") String password_new
            );

    /**
     * 设置支付密码
     * token	是	string	无
     * pwd2	是	string	二级密码
     * pwd22	是	string	pwd22重复
     * @return
     */
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=level_2_password")
    @FormUrlEncoded
    Observable<PublicBean> setPayPassword(
            @Field("token") String token,
            @Field("pwd2") String pwd2,
            @Field("pwd22") String pwd22
            );

    /**
     * mobile	是	string	用户名
     * pwd2	是	string	支付密码
     * code	否	string	验证码
     * @return
     */
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=forgetpwd2")
    @FormUrlEncoded
    Observable<PublicBean> findPayPassword(
            @Field("mobile") String mobile,
            @Field("pwd2") String pwd2,
            @Field("code") String code
            );


    /**
     * 获取安卓版本信息
     * @return
     */
    @POST("index.php?i=1&c=entry&p=Other&do=Apis&m=sz_yi&op=android")
    Observable<AppVersionBean> getAppVersion();


    /**
     * 获取弹出公告
     * @return
     */
    @POST("index.php?i=1&c=entry&p=Other&do=Apis&m=sz_yi&op=sysno")
    Observable<AdvertisingBean> getAdvertising();


    /**
     * 根据id获取用户信息
     * @param id 用户id
     * @return
     */
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=getuser")
    @FormUrlEncoded
    Observable<UserInfoFromIdBean> getUserInfoFromId(@Field("id") String id);


    /**
     * 佣金列表
     * page	是	int	分页
     * token	是	string	无
     * @return
     */
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=commission_list")
    @FormUrlEncoded
    Observable<UserInfoFromIdBean> getCommissionList(
            @Field("token") String token,
            @Field("page") String page
            );


    /**
     * 获取佣金排行榜
     * type	是	string	1昨天 2今天 3本周
     * @param type
     * @return
     */
    @POST("index.php?i=1&c=entry&p=UserInfo&do=Apis&m=sz_yi&op=ranking_list")
    @FormUrlEncoded
    Observable<UserInfoFromIdBean> getRankingList(
            @Field("type") String type
            );


    /**
     * 获取推广海报
     * token	是	string	无
     * @param token
     * @return
     */
    @POST("index.php?i=1&c=entry&p=Agency&do=Apis&m=sz_yi&op=sharelist")
    @FormUrlEncoded
    Observable<PostersBean> getSharelist(
            @Field("token") String token
            );



}
