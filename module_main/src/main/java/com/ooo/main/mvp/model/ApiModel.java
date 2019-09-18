package com.ooo.main.mvp.model;

import android.support.v7.widget.CardView;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.ooo.main.app.AppLifecyclesImpl;
import com.ooo.main.mvp.model.api.Api;
import com.ooo.main.mvp.model.api.service.ApiService;
import com.ooo.main.mvp.model.api.service.RedPacketGameService;
import com.ooo.main.mvp.model.entity.AddBlankCardBean;
import com.ooo.main.mvp.model.entity.BillingDetailBean;
import com.ooo.main.mvp.model.entity.BlankCardBean;
import com.ooo.main.mvp.model.entity.CertificationBean;
import com.ooo.main.mvp.model.entity.DelectBlankCardBean;
import com.ooo.main.mvp.model.entity.RedPacketGameRomeBean;
import com.ooo.main.mvp.model.entity.TakeMoneyBean;
import com.ooo.main.mvp.model.entity.UnderPayerBean;
import com.ooo.main.mvp.model.entity.WithRecordBean;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;

/**
 * 网络请求model
 */
public class ApiModel extends BaseModel{

    public ApiModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /**
     * 获取下线列表
     * @param status
     * @param fuid
     * @param page
     * @return
     */
    public Observable<UnderPayerBean> getUnderLineList(String status, String fuid, String page) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .getUnderLineList (token,status,fuid,page);
    }


    /**
     * 获取账单明细
     * @param time1 开始时间
     * @param time2 结束时间
     * @param page
     * @return
     */
    public Observable<BillingDetailBean> getBillingDetails(String time1, String time2, String page) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .getBillingDetails (token,time1,time2,page);
    }

    /**
     * 获取账单明细
     * @param time1 开始时间
     * @param time2 结束时间
     * @param page
     * @return
     */
    public Observable<WithRecordBean> getWithRecord(String time1, String time2, String page, String payType) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .getWithRecord (token,time1,time2,page,payType);
    }
    /**
     * 获取银行卡列表
     * @return
     */
    public Observable<BlankCardBean> getBlankCardList() {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .getBlankCardList (token);
    }
    /**
     * 添加银行卡
     * cardcode	是	String	卡号
     * cardopen	是	String	开户行
     * cardaddress	是	String	开户地址
     * type	是	String	1借记卡，2非借记卡
     * @return
     */
    public Observable<AddBlankCardBean> addBlankCard(String cardcode, String cardopen, String cardaddress, String type) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        String cardname = AppLifecyclesImpl.getUserinfo ().getRealname ();
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .addBlankCard (token,cardname,cardcode,cardopen,cardaddress,type);
    }

    /**
     * 实名认证
     * realname	是	string	姓名
     * idnumber	否	string	身份证号
     * @return
     */
    public Observable<CertificationBean> setCertification(String realname, String idnumber) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .setCertification (token,realname,idnumber);
    }

    /**
     * 解绑银行卡
     * id	否	string	银行卡数据表id
     * @return
     */
    public Observable<DelectBlankCardBean> delBlankCard(String id) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .delBlankCard (token,id);
    }

    /**
     * 提现
     * @param goldmoney  金额
     * @param cardid 银行卡id
     * @return
     */
    public Observable<TakeMoneyBean> takeMoney(String goldmoney, String cardid) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .takeMoney (token,goldmoney, cardid);
    }

}