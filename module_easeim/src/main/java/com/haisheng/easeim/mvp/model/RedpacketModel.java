package com.haisheng.easeim.mvp.model;

import com.haisheng.easeim.mvp.model.api.service.ChatRoomService;
import com.haisheng.easeim.mvp.model.api.service.RedpacketService;
import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.haisheng.easeim.mvp.model.entity.CheckRedpacketInfo;
import com.haisheng.easeim.mvp.model.entity.RedpacketBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;
import retrofit2.http.Field;


public class RedpacketModel extends BaseModel{

    public RedpacketModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<BaseResponse<RedpacketBean>> sendRedpacket(Long roomId, String booms, int redpacketNumber, double money, int welfareStatus,String password) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(RedpacketService.class)
                .sendRedpacket(token,roomId,booms,redpacketNumber,money,welfareStatus,password);
    }

    public Observable<BaseResponse<CheckRedpacketInfo>> checkRedpacket(Long roomId, Long redpacketId, int welfareStatus) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(RedpacketService.class)
                .checkRedpacket(token,roomId,redpacketId,welfareStatus);
    }

    public Observable<BaseResponse> grabRedpacket(Long roomId, Long redpacketId, int welfareStatus) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(RedpacketService.class)
                .grabRedpacket(token,roomId,redpacketId,welfareStatus);
    }

    public Observable<BaseResponse<RedpacketBean>> redpacketDetail(Long roomId, Long redpacketId, int welfareStatus) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(RedpacketService.class)
                .redpacketDetail(token,roomId,redpacketId,welfareStatus);
    }


}
