package com.ooo.main.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.ooo.main.mvp.model.ApiModel;
import com.ooo.main.mvp.model.MemberModel;
import com.ooo.main.mvp.model.api.Api;

import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2019 10:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class ForgetPayPasswordModule {

    @ActivityScope
    @Provides
    public MemberModel provideMemberModel(IRepositoryManager iRepositoryManager) {
        return new MemberModel(iRepositoryManager);
    }
    @ActivityScope
    @Provides
    public ApiModel provideApiModel(IRepositoryManager iRepositoryManager) {
        return new ApiModel(iRepositoryManager);
    }
}