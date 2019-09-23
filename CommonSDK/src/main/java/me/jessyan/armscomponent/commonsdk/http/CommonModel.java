package me.jessyan.armscomponent.commonsdk.http;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.entity.VersionEntity;
import me.jessyan.armscomponent.commonsdk.http.service.CommonService;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/12/2019 20:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CommonModel extends BaseModel {

    public CommonModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<BaseResponse <VersionEntity>> checkVersion(){
        return mRepositoryManager.obtainRetrofitService( CommonService.class)
                .checkVersion();
    }

    public Observable<BaseResponse <Double>> getBalance(){
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService( CommonService.class)
                .getBalance(token);
    }

//    public O
}