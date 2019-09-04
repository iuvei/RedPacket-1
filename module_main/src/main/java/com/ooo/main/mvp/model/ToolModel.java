package com.ooo.main.mvp.model;

import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import me.jessyan.armscomponent.commonsdk.http.Api;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;
import me.jessyan.armscomponent.commonsdk.utils.MyFileUtils;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


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
public class ToolModel extends BaseModel{

    public ToolModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    public Observable<BaseResponse<File>> downloadImage(String imgUrl) {
        return Observable.create(new ObservableOnSubscribe<BaseResponse<File>>() {
            @Override
            public void subscribe(ObservableEmitter<BaseResponse<File>> emitter) {
                BaseResponse response = new BaseResponse();

                    Context context = Utils.getApp();
                    File file = null;
                    try {
                        file = Glide.with(context)
                                .load(imgUrl)
                                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                .get();
//                        FileUtils.
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (file != null) {
                            response.setStatus(Api.REQUEST_SUCCESS_CODE);
                            response.setMessage("下载成功...");
                            response.setResult(file);
                            emitter.onNext(response);
                            emitter.onComplete();
                        } else {
                            emitter.onError(new Throwable("下载失败..."));
                            emitter.onComplete();
                        }
                    }

            }
        });
    }

    public Observable<BaseResponse<List<String>>> imgsToBase64s(List<String> imgPaths) {
        return Observable.create(new ObservableOnSubscribe<BaseResponse<List<String>>>() {
            @Override
            public void subscribe(ObservableEmitter<BaseResponse<List<String>>> emitter) {
                BaseResponse<List<String>> response = new BaseResponse<>();
                try {
                    List<String> entities = new ArrayList<>();
                    if(null != imgPaths){
                        for (String imgPath : imgPaths) {
                            String base64 = MyFileUtils.imageToBase64(imgPath);
                            if(null == base64)
                                throw new Exception("找不到文件~~");
                            entities.add(base64);
                        }
                    }
                    response.setStatus(Api.REQUEST_SUCCESS_CODE);
                    response.setMessage("转换成功...");
                    response.setResult(entities);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(new Throwable("转换失败..."));
                    emitter.onComplete();
                }
            }
        });
    }

    public Observable<BaseResponse<List<String>>> compressImgsToBase64s(List<String> imgPaths) {
        return Observable.create(new ObservableOnSubscribe<BaseResponse<List<String>>>() {
            @Override
            public void subscribe(ObservableEmitter<BaseResponse<List<String>>> emitter) {
                BaseResponse<List<String>> response = new BaseResponse<>();
                List<String> entities = new ArrayList<>();
                try {
                    Context context =Utils.getApp();
                    List<File> imgFiles = Luban.with(context).load(imgPaths).ignoreBy(100).get();
                    for (File imgFile : imgFiles) {
                        String base64 = MyFileUtils.fileToBase64(imgFile);
                        if(null == base64)
                            throw new Exception("找不到文件~~");
                        entities.add(base64);
                    }
                    response.setStatus(Api.REQUEST_SUCCESS_CODE);
                    response.setMessage("转换成功...");
                    response.setResult(entities);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(new Throwable("转换失败..."));
                    emitter.onComplete();
                }
            }
        });
    }

    public Observable<BaseResponse<File>> compressImg(String imgPath) {
        return Observable.create(new ObservableOnSubscribe<BaseResponse<File>>() {
            @Override
            public void subscribe(ObservableEmitter<BaseResponse<File>> emitter) {
                Context context =Utils.getApp();
                Luban.with(context)
                        .load(imgPath)
                        .ignoreBy(100)//低于100的图片不用压缩
                        .putGear(3)//设置压缩级别 默认是3
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(File file) {
                                BaseResponse<File> response = new BaseResponse<>();
                                response.setStatus(Api.REQUEST_SUCCESS_CODE);
                                response.setMessage("压缩成功...");
                                response.setResult(file);
                                emitter.onNext(response);
                                emitter.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                emitter.onError(e);
                                emitter.onComplete();
                            }
                        }).launch();
            }
        });
    }
}