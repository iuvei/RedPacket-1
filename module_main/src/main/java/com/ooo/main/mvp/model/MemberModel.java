package com.ooo.main.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.ooo.main.mvp.model.api.service.MemberService;
import com.ooo.main.mvp.model.entity.LoginResultInfo;
import com.ooo.main.mvp.model.entity.MemberInfo;

import java.io.File;
import java.util.HashMap;
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
public class MemberModel extends BaseModel{

    public MemberModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<BaseResponse<LoginResultInfo>> login(String phone, String password) {
        return mRepositoryManager.obtainRetrofitService(MemberService.class)
                .login(phone, password);
    }

    public Observable<BaseResponse> sendSms(String phone, boolean isRegister) {
        return mRepositoryManager.obtainRetrofitService(MemberService.class)
                .sendSms(phone, String.valueOf(isRegister));
    }

    public Observable<BaseResponse<LoginResultInfo>> register(String phoneNumber,String verificationCode,String password,String invitationCode) {
        return mRepositoryManager.obtainRetrofitService(MemberService.class)
                .register(phoneNumber, verificationCode, password, invitationCode);
    }

    public Observable<BaseResponse> forgotPassword(String phoneNumber,String verificationCode,String password) {
        return mRepositoryManager.obtainRetrofitService(MemberService.class)
                .forgotPassword(phoneNumber,verificationCode, password);
    }

    public Observable<BaseResponse<MemberInfo>> getMemberInfo() {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(MemberService.class)
                .getMemberInfo(token);
    }

    public Observable<BaseResponse> updateMemberInfo( String nickname,String avatarUrl,int sex,String password,String authCode) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(MemberService.class)
                .updateMemberInfo(token, nickname, avatarUrl, sex, password, authCode);
    }

    public Observable<BaseResponse<String>> updateAvatarUrl(File imgFile) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("file", "file");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), imgFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imgFile.getName(), requestBody);
        return mRepositoryManager.obtainRetrofitService(MemberService.class)
                .updateAvatarUrl(map,body);
    }

    public Observable<BaseResponse> searchFriend(String keyword) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(MemberService.class)
                .searchFriend(token, keyword);
    }

    public Observable<BaseResponse> getAddressBook(int pageNumber) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(MemberService.class)
                .getAddressBook(token, pageNumber);
    }
    public Observable<BaseResponse> upLoadPic(String picData) {
        String token = UserPreferenceManager.getInstance().getCurrentUserToken();
        return mRepositoryManager.obtainRetrofitService(MemberService.class)
                .upLoadPic (token, picData);
    }


    private RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), value);
        return requestBody;
    }
}