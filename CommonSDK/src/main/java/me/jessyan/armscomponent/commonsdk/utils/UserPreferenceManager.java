/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.jessyan.armscomponent.commonsdk.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.entity.UserInfo;


public class UserPreferenceManager {
	/**
	 * name of preference
	 */
	private static final String PREFERENCE_NAME = "UserInfo";

	private static final String SHARED_KEY_CURRENTUSER_TOKEN = "SHARED_KEY_CURRENTUSER_TOKEN";
	private static final String SHARED_KEY_CURRENTUSER_ID = "SHARED_KEY_CURRENTUSER_ID";
	private static final String SHARED_KEY_CURRENTUSER_HXID = "SHARED_KEY_CURRENTUSER_HXID";
	private static final String SHARED_KEY_CURRENTUSER_NICKNAME = "SHARED_KEY_CURRENTUSER_NICKNAME";
	private static final String SHARED_KEY_CURRENTUSER_AVATARURL = "SHARED_KEY_CURRENTUSER_AVATARURL";
	private static final String SHARED_KEY_CURRENTUSER_VIP = "SHARED_KEY_CURRENTUSER_VIP";
	private static final String SHARED_KEY_CURRENTUSER_PHONE = "SHARED_KEY_CURRENTUSER_PHONE";
	private static final String SHARED_KEY_CURRENTUSER_SEX = "SHARED_KEY_CURRENTUSER_SEX";

	private static SharedPreferences mSharedPreferences;
	private static UserPreferenceManager mPreferencemManager;
	private static SharedPreferences.Editor editor;


	@SuppressLint("CommitPrefEdits")
	private UserPreferenceManager(Context cxt) {
		mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		editor = mSharedPreferences.edit();
	}

	public static synchronized void init(Context cxt){
	    if(mPreferencemManager == null){
	        mPreferencemManager = new UserPreferenceManager(cxt);
	    }
	}

	/**
	 * get instance of PreferenceManager
	 * @param
	 * @return
	 */
	public synchronized static UserPreferenceManager getInstance() {
		if (mPreferencemManager == null) {
			throw new RuntimeException("please init first!");
		}
		return mPreferencemManager;
	}

    private UserInfo mCurrUser;

    public UserInfo getCurrentUser(){
        if(null == mCurrUser){
        	mCurrUser = new UserInfo();
        	mCurrUser.setToken( mSharedPreferences.getString(SHARED_KEY_CURRENTUSER_TOKEN, ""));
        	mCurrUser.setId( mSharedPreferences.getLong(SHARED_KEY_CURRENTUSER_ID, -1l));
        	mCurrUser.setHxId(mSharedPreferences.getString(SHARED_KEY_CURRENTUSER_HXID, null));
        	mCurrUser.setNickname(mSharedPreferences.getString(SHARED_KEY_CURRENTUSER_NICKNAME, null));
        	mCurrUser.setAvatarUrl(mSharedPreferences.getString(SHARED_KEY_CURRENTUSER_AVATARURL, null));
        	mCurrUser.setSexStatus(mSharedPreferences.getInt(SHARED_KEY_CURRENTUSER_SEX, 0  ));
        	mCurrUser.setPhoneNumber(mSharedPreferences.getString(SHARED_KEY_CURRENTUSER_PHONE, null));
        }
        return mCurrUser;
    }

    public void setCurrentUser(UserInfo userEntity){
        mCurrUser = userEntity;
        if(null != userEntity){
        	setCurrentUserToken(userEntity.getToken());
        	setCurrentUserId(userEntity.getId());
        	setCurrentUserHxId(userEntity.getHxId());
        	setCurrentUserNick(userEntity.getNickname());
        	setCurrentUserAvatarUrl(userEntity.getAvatarUrl());
			setCurrentUserSexStatus(userEntity.getSexStatus());
        	setCurrentUserPhone(userEntity.getPhoneNumber());

        }else{
			removeCurrentUserInfo();
        }
    }

	public String getCurrentUserToken(){
		return getCurrentUser().getToken();
	}
	public Long getCurrentUserId(){
		return getCurrentUser().getId();
	}
	public String getCurrentUserHxId(){
		return getCurrentUser().getHxId();
	}
	public String getCurrentUserNick() {
		return  getCurrentUser().getNickname();
	}
	public String getCurrentUserAvatarUrl() {
		return getCurrentUser().getAvatarUrl();
	}
	public Integer getCurrentUserSexStatus(){
		return getCurrentUser().getSexStatus();
	}
	public String getCurrentUserPhoneNumber(){
		return getCurrentUser().getPhoneNumber();
	}

	public void setCurrentUserToken(String token){
		if(null != mCurrUser)
			mCurrUser.setToken(token);

		editor.putString(SHARED_KEY_CURRENTUSER_TOKEN, token);
		editor.apply();
	}

	public void setCurrentUserId(Long userId){
		if(null != mCurrUser)
			mCurrUser.setId(userId);

		editor.putLong(SHARED_KEY_CURRENTUSER_ID, userId);
		editor.apply();
	}

	public void setCurrentUserHxId(String hxId){
		if(null != mCurrUser)
			mCurrUser.setHxId(hxId);

		editor.putString(SHARED_KEY_CURRENTUSER_HXID, hxId);
		editor.apply();
	}

	public void setCurrentUserNick(String nickname) {
		if(null != mCurrUser)
			mCurrUser.setNickname(nickname);

		editor.putString(SHARED_KEY_CURRENTUSER_NICKNAME, nickname);
		editor.apply();
	}

	public void setCurrentUserAvatarUrl(String avatarUrl) {
		if(null != mCurrUser)
			mCurrUser.setAvatarUrl(avatarUrl);

		editor.putString(SHARED_KEY_CURRENTUSER_AVATARURL, avatarUrl);
		editor.apply();
	}

	public void setCurrentUserPhone(String phone){
		if(null != mCurrUser)
			mCurrUser.setPhoneNumber(phone);

		editor.putString(SHARED_KEY_CURRENTUSER_PHONE, phone);
		editor.apply();
	}

	public void setCurrentUserSexStatus(Integer sex){
		if(null != mCurrUser)
			mCurrUser.setSexStatus(sex);

		editor.putInt(SHARED_KEY_CURRENTUSER_SEX, sex);
		editor.apply();
	}


	public void removeCurrentUserInfo() {
		editor.remove(SHARED_KEY_CURRENTUSER_ID);
		editor.remove(SHARED_KEY_CURRENTUSER_HXID);
		editor.remove(SHARED_KEY_CURRENTUSER_NICKNAME);
		editor.remove(SHARED_KEY_CURRENTUSER_AVATARURL);
		editor.remove(SHARED_KEY_CURRENTUSER_SEX);
		editor.remove(SHARED_KEY_CURRENTUSER_PHONE);
		editor.remove(SHARED_KEY_CURRENTUSER_TOKEN);
		editor.apply();
	}
}
