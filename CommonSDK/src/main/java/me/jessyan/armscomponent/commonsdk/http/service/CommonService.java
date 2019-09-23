/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.jessyan.armscomponent.commonsdk.http.service;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.entity.VersionEntity;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于 共用 的一些 API
 * ================================================
 */
public interface CommonService {

    @POST("index.php?i=1&c=entry&p=Other&do=Apis&m=sz_yi&op=android")
    Observable <BaseResponse <VersionEntity>> checkVersion();

    @FormUrlEncoded
    @POST("index.php?i=1&c=entry&p=Game&do=Apis&m=sz_yi&op=goldmoney")
    Observable<BaseResponse<Double>> getBalance(
            @Field("token") String token
    );
}
