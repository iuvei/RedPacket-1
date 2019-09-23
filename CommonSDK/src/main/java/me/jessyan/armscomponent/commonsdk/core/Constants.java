/*
 * Copyright 2018 JessYan
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
package me.jessyan.armscomponent.commonsdk.core;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * CommonSDK 的 Constants 可以定义公用的常量, 比如关于业务的常量或者正则表达式, 每个组件的 Constants 可以定义组件自己的私有常量
 * <p>
 * Created by JessYan on 30/03/2018 17:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface Constants {
    int NUMBER_OF_PAGE = 10;

    String ACCOUT_TOKEN_OVERDUE ="token_overdue";
    //电话号码正则
    String PHONE_REGULAR = "^1[3-9]\\d{9}$";

    /**
     * 文件类型
     */
    int IMAGE_CODE = 0;
    int VEDIO_CODE = 1;
    int WORD_CODE = 2;
    int EXCEL_CODE = 3;
    int PPT_CODE = 4;
    int PDF_CODE = 5;
    int OTHER_CODE = -1;

    /**
     * Activity 之间跳转常量
     */
    int REQUEST_CODE_CAMERA = 0x100; // 拍照
    int REQUEST_CODE_VIDEO = 0x101; // 录像
    int REQUEST_CODE_CLIP = 0x102; // 裁剪
    int REQUEST_CODE_SYSTEM_ALBUM = 0x103; // 系统相册
    int REQUEST_CODE_VIDEO_ALBUM = 0x104; // 录像集
    int REQUEST_CODE_RADIO_ALBUM = 0x105; // 单选相册
    int REQUEST_CODE_MULTI_ALBUM = 0x106; // 多选相册

    interface IM {
        String TYPE = "typeStatus";
        int TYPE_ROOM = 1;
        int TYPE_MESSAGE = 2;
        String SHARED_KEY_SETTING_NOTIFICATION = "shared_key_setting_notification";
        String SHARED_KEY_SETTING_SOUND = "shared_key_setting_sound";
        String SHARED_KEY_SETTING_VIBRATE = "shared_key_setting_vibrate";
        String SHARED_KEY_SETTING_SPEAKER = "shared_key_setting_speaker";
    }

    static ArrayList<String> getCoins(){
        ArrayList<String> coins=new ArrayList<>();
        coins.add("免费");
        for (int i = 5; i < 30; i+=5) {
            coins.add(String.valueOf(i));
        }
        return coins;
    }

}
