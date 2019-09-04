package com.ooo.main.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.ooo.main.mvp.model.api.service.BillService;
import com.ooo.main.mvp.model.api.service.MemberService;
import com.ooo.main.mvp.model.entity.BillBean;
import com.ooo.main.mvp.model.entity.BillRecordInfo;
import com.ooo.main.mvp.model.entity.LoginResultInfo;
import com.ooo.main.mvp.model.entity.MemberInfo;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/27/2019 14:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class BillModel extends BaseModel{

    public BillModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<BaseResponse<BillRecordInfo>> allRecord(String startTime, String endTime, int pageNumber) {
        String token =UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(BillService.class)
                .allRecord(token, startTime, endTime, pageNumber);
    }

    public Observable<BaseResponse<BillRecordInfo>> rewardRecord(String type, String startTime, String endTime, int pageNumber) {
        String token =UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(BillService.class)
                .rewardRecord(token, type, startTime, endTime, pageNumber);
    }

    public Observable<BaseResponse<BillRecordInfo>> rechargeRecord(String type, String startTime, String endTime, int pageNumber) {
        String token =UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(BillService.class)
                .rechargeRecord(token, type, startTime, endTime, pageNumber);
    }

    public Observable<BaseResponse<BillRecordInfo>> withdrawRecord(String type, String startTime, String endTime, int pageNumber) {
        String token =UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(BillService.class)
                .withdrawRecord(token, type, startTime, endTime, pageNumber);
    }

    public Observable<BaseResponse<BillRecordInfo>> sendRedpacketRecord(String type, String startTime, String endTime, int pageNumber) {
        String token =UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(BillService.class)
                .sendRedpacketRecord(token, type, startTime, endTime, pageNumber);
    }

    public Observable<BaseResponse<BillRecordInfo>> grabRedpacketRecord(String type, String startTime, String endTime, int pageNumber) {
        String token =UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(BillService.class)
                .grabRedpacketRecord(token, type, startTime, endTime, pageNumber);
    }

    public Observable<BaseResponse<BillRecordInfo>> profitLossRecord(String type, String startTime, String endTime, int pageNumber) {
        String token =UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(BillService.class)
                .profitLossRecord(token, type, startTime, endTime, pageNumber);
    }

    public Observable<BaseResponse<BillRecordInfo>> commissionIncomeRecord(String type, String startTime, String endTime, int pageNumber) {
        String token =UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(BillService.class)
                .commissionIncomeRecord(token, type, startTime, endTime, pageNumber);
    }

    public Observable<BaseResponse<BillRecordInfo>> fruitMachineRecord(String type, String startTime, String endTime, int pageNumber) {
        String token =UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(BillService.class)
                .fruitMachineRecord(token, type, startTime, endTime, pageNumber);
    }

}