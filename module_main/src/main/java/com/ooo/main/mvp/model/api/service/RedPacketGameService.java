package com.ooo.main.mvp.model.api.service;

import com.ooo.main.mvp.model.entity.RedPacketGameRomeBean;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface RedPacketGameService {

    /**
     * 获取游戏房间列表
     * @param type
     * @return
     */
    @POST("index.php?i=1&c=entry&p=Other&do=Apis&m=sz_yi&op=room")
    @FormUrlEncoded
    Observable<RedPacketGameRomeBean> redPacketGameRoomList(@Field("type") String type);

}
