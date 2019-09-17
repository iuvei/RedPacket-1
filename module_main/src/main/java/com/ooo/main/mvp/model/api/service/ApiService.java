package com.ooo.main.mvp.model.api.service;

import com.ooo.main.mvp.model.entity.BillingDetailBean;
import com.ooo.main.mvp.model.entity.RedPacketGameRomeBean;
import com.ooo.main.mvp.model.entity.UnderPayerBean;

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

}
