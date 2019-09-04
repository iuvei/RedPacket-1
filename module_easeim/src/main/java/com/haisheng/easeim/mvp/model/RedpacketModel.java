package com.haisheng.easeim.mvp.model;

import com.haisheng.easeim.mvp.model.api.service.RedpacketService;
import com.haisheng.easeim.mvp.model.entity.RoomBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;


public class RedpacketModel extends BaseModel{

    public RedpacketModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<BaseResponse<List<RoomBean>>> roomList() {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(RedpacketService.class)
                .roomList(token);
    }

    public Observable<BaseResponse> roomDetail(Long roomId) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(RedpacketService.class)
                .roomDetail(token,roomId);
    }
}