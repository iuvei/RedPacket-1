package com.ooo.main.mvp.model;

import android.support.v7.widget.CardView;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.ooo.main.app.AppLifecyclesImpl;
import com.ooo.main.mvp.model.api.Api;
import com.ooo.main.mvp.model.api.service.ApiService;
import com.ooo.main.mvp.model.api.service.RedPacketGameService;
import com.ooo.main.mvp.model.entity.AddBlankCardBean;
import com.ooo.main.mvp.model.entity.AdvertisingBean;
import com.ooo.main.mvp.model.entity.AppVersionBean;
import com.ooo.main.mvp.model.entity.BillingDetailBean;
import com.ooo.main.mvp.model.entity.BlankCardBean;
import com.ooo.main.mvp.model.entity.CertificationBean;
import com.ooo.main.mvp.model.entity.CommisonListBean;
import com.ooo.main.mvp.model.entity.DelectBlankCardBean;
import com.ooo.main.mvp.model.entity.GameRuleBean;
import com.ooo.main.mvp.model.entity.PostersBean;
import com.ooo.main.mvp.model.entity.PublicBean;
import com.ooo.main.mvp.model.entity.RankingBean;
import com.ooo.main.mvp.model.entity.RechargeRecordBean;
import com.ooo.main.mvp.model.entity.RedPacketGameRomeBean;
import com.ooo.main.mvp.model.entity.TakeMoneyBean;
import com.ooo.main.mvp.model.entity.UnderPayerBean;
import com.ooo.main.mvp.model.entity.UpdatePasswordBean;
import com.ooo.main.mvp.model.entity.UserInfoFromIdBean;
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


    /**
     * 修改密码
     * token	是	string	无
     * old_password	是	string	旧密码
     * password	是	string	密码
     * password_new	否	string	重复密码
     * @return
     */
    public Observable<UpdatePasswordBean> updatePassword(String old_password, String password, String password_new) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .updatePassword (token,old_password, password,password_new);
    }


    /**
     * 设置支付密码
     * @param password  支付密码
     * @param confirmPassword 确认支付密码
     * @return
     */
    public Observable<PublicBean> setPayPassword(String password, String confirmPassword) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .setPayPassword (token,password, confirmPassword);
    }

    /**
     * 找回支付密码
     * @param mobile  手机号
     * @param password 密码
     * @param code 验证号
     * @return
     */
    public Observable<PublicBean> findPayPassword(String mobile,String password, String code) {
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .findPayPassword (mobile,password, code);
    }

    /**
     * 获取app版本信息
     * @return
     */
    public Observable<AppVersionBean> getAppVersion() {
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .getAppVersion ();
    }

    /**
     * 获取弹出公告
     * @return
     */
    public Observable<AdvertisingBean> getAdvertising() {
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .getAdvertising ();
    }


    /**
     * 根据用户id获取用户信息
     * @return
     */
    public Observable<UserInfoFromIdBean> getUserInfoFromId(String id) {
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .getUserInfoFromId (id);
    }


    /**
     * 获取佣金列表
     * @param page
     * @return
     */
    public Observable<CommisonListBean> getCommissionList(int page) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .getCommissionList (token,page);
    }

    /**
     * 获取佣金排行榜
     * type	是	string	1昨天 2今天 3本周
     * @param type
     * @return
     */
    public Observable<RankingBean> getRankingList(String type) {
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .getRankingList (type);
    }

    /**
     *获取推广海报
     * @return
     */
    public Observable<PostersBean> getSharelist() {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .getSharelist (token);
    }

    /**
     * 游戏规则说明
     * @return
     */
    public Observable<GameRuleBean> getGameRule() {
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .gameRule ();
    }


    /**
     * 邀请手机联系人
     * @param jsonString
     * @return
     */
    public Observable<PublicBean> inviteContact(String jsonString) {
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .inviteContact (jsonString);
    }


    /**
     * 获取充值记录
     * @param page   页数
     * @param paytype
     * @return
     */
    public Observable<RechargeRecordBean> getRechargeRecord(int page, String paytype) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService( ApiService.class)
                .getRechargeRecord (token,"2019-09-01","2100-01-01",page,paytype);
    }
}