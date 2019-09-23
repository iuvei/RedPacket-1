package com.haisheng.easeim.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.haisheng.easeim.mvp.model.api.service.ContactService;
import com.haisheng.easeim.mvp.model.entity.ContactInfo;
import com.haisheng.easeim.mvp.model.entity.PublicResponseBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.haisheng.easeim.mvp.contract.ContactContract;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/02/2019 18:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ContactModel extends BaseModel{

    @Inject
    public ContactModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<BaseResponse<ContactInfo>> contactList(int pageNumber) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(ContactService.class)
                .contactList(token,pageNumber);
    }

    public Observable<BaseResponse<List<UserInfo>>> searchContact(String field) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(ContactService.class)
                .searchContact(token,field);
    }

    public Observable<BaseResponse<List <UserInfo>>> serverList() {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(ContactService.class)
                .serverList(token);
    }

    public Observable<BaseResponse<String>> serverUrl() {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(ContactService.class)
                .serverUrl(token);
    }


    public Observable<PublicResponseBean> delFriend(String fuid) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(ContactService.class)
                .delFriend(token,fuid);
    }

    public Observable<PublicResponseBean> setRemark(String remark, String fuid) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(ContactService.class)
                .setRemark (token,remark,fuid);
    }
}