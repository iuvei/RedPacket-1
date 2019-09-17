package com.ooo.main.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.ooo.main.mvp.model.api.service.OtherService;
import com.ooo.main.mvp.model.api.service.RedPacketGameService;
import com.ooo.main.mvp.model.entity.RedPacketGameRomeBean;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.entity.AdBannerInfo;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;

/**
 * 红包游戏
 */
public class RedPacketRoomModel extends BaseModel{

    public RedPacketRoomModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<RedPacketGameRomeBean> redPacketGame(String type) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService( RedPacketGameService.class)
                .redPacketGameRoomList(type);
    }

}