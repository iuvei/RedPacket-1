package com.haisheng.easeim.app;

import android.content.Context;

import com.haisheng.easeim.mvp.model.db.UserDao;
import com.haisheng.easeim.mvp.utils.PreferenceManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;

public class IMModel {
    protected Context context = null;
    protected Map<Key,Object> valueCache = new HashMap<Key,Object>();


    public IMModel(Context ctx){
        context = ctx;
        PreferenceManager.init(context);
    }

    public UserInfo getUserEntityByUsername(String username){
        UserInfo userEntity =null;
        if(username.equals(EMClient.getInstance().getCurrentUser())){
            userEntity = UserPreferenceManager.getInstance().getCurrentUser();
        }else{
            userEntity = UserDao.getInstance().getUserEntityByHxId(username);
        }
        if(null == userEntity){
            userEntity = new UserInfo();
            userEntity.setHxId(username);
        }
        return userEntity;
    }

    public EaseUser getEaseUserByUsername(String username){
        EaseUser easeUser = new EaseUser(username);
        UserInfo userEntity = null;
        if(username.equals(EMClient.getInstance().getCurrentUser())){
            userEntity = UserPreferenceManager.getInstance().getCurrentUser();
        }else{
            userEntity = UserDao.getInstance().getUserEntityByHxId(username);
        }
        if(null != userEntity){
            easeUser.setNickname(userEntity.getNickname());
            easeUser.setAvatar(userEntity.getAvatarUrl());
        }
        return easeUser;
    }
    
    public void setSettingMsgNotification(boolean paramBoolean) {
        PreferenceManager.getInstance().setSettingMsgNotification(paramBoolean);
        valueCache.put(Key.VibrateAndPlayToneOn, paramBoolean);
    }

    public boolean getSettingMsgNotification() {
        Object val = valueCache.get(Key.VibrateAndPlayToneOn);

        if(val == null){
            val = PreferenceManager.getInstance().getSettingMsgNotification();
            valueCache.put(Key.VibrateAndPlayToneOn, val);
        }
       
        return (Boolean) (val != null?val:true);
    }

    public void setSettingMsgSound(boolean paramBoolean) {
        PreferenceManager.getInstance().setSettingMsgSound(paramBoolean);
        valueCache.put(Key.PlayToneOn, paramBoolean);
    }

    public boolean getSettingMsgSound() {
        Object val = valueCache.get(Key.PlayToneOn);

        if(val == null){
            val = PreferenceManager.getInstance().getSettingMsgSound();
            valueCache.put(Key.PlayToneOn, val);
        }
       
        return (Boolean) (val != null?val:true);
    }

    public void setSettingMsgVibrate(boolean paramBoolean) {
        PreferenceManager.getInstance().setSettingMsgVibrate(paramBoolean);
        valueCache.put(Key.VibrateOn, paramBoolean);
    }

    public boolean getSettingMsgVibrate() {
        Object val = valueCache.get(Key.VibrateOn);

        if(val == null){
            val = PreferenceManager.getInstance().getSettingMsgVibrate();
            valueCache.put(Key.VibrateOn, val);
        }
       
        return (Boolean) (val != null?val:true);
    }

    public void setSettingMsgSpeaker(boolean paramBoolean) {
        PreferenceManager.getInstance().setSettingMsgSpeaker(paramBoolean);
        valueCache.put(Key.SpakerOn, paramBoolean);
    }

    public boolean getSettingMsgSpeaker() {        
        Object val = valueCache.get(Key.SpakerOn);

        if(val == null){
            val = PreferenceManager.getInstance().getSettingMsgSpeaker();
            valueCache.put(Key.SpakerOn, val);
        }
       
        return (Boolean) (val != null?val:true);
    }


    public void setDisabledGroups(List<String> groups){

        List<String> list = new ArrayList<String>();
        list.addAll(groups);
        for(int i = 0; i < list.size(); i++){
            if(EaseAtMessageHelper.get().getAtMeGroups().contains(list.get(i))){
                list.remove(i);
                i--;
            }
        }
        PreferenceManager.getInstance().setDisableGroups(groups);
        valueCache.put(Key.DisabledGroups, list);
    }

    public List<String> getDisabledGroups(){
        Object val = valueCache.get(Key.DisabledGroups);
        if(val == null){
            val =  PreferenceManager.getInstance().getDisabledGroups();
            valueCache.put(Key.DisabledGroups, val);
        }

        //noinspection unchecked
        return (List<String>) val;
    }

    public void setDisabledIds(List<String> ids){
       PreferenceManager.getInstance().setDisabledIds(ids);
        valueCache.put(Key.DisabledIds, ids);
    }

    public List<String> getDisabledIds(){
        Object val = valueCache.get(Key.DisabledIds);

        if(val == null){
            PreferenceManager.getInstance().getDisabledIds();
            valueCache.put(Key.DisabledIds, val);
        }

        //noinspection unchecked
        return (List<String>) val;
    }
    
    public void setGroupsSynced(boolean synced){
        PreferenceManager.getInstance().setGroupsSynced(synced);
    }
    
    public boolean isGroupsSynced(){
        return PreferenceManager.getInstance().isGroupsSynced();
    }
    
    public void setContactSynced(boolean synced){
        PreferenceManager.getInstance().setContactSynced(synced);
    }
    
    public boolean isContactSynced(){
        return PreferenceManager.getInstance().isContactSynced();
    }
    
    public void setBlacklistSynced(boolean synced){
        PreferenceManager.getInstance().setBlacklistSynced(synced);
    }
    
    public boolean isBacklistSynced(){
        return PreferenceManager.getInstance().isBacklistSynced();
    }
    
    public void allowChatroomOwnerLeave(boolean value){
        PreferenceManager.getInstance().setSettingAllowChatroomOwnerLeave(value);
    }
    
    public boolean isChatroomOwnerLeaveAllowed(){
        return PreferenceManager.getInstance().getSettingAllowChatroomOwnerLeave();
    }
   
    public void setDeleteMessagesAsExitGroup(boolean value) {
        PreferenceManager.getInstance().setDeleteMessagesAsExitGroup(value);
    }
    
    public boolean isDeleteMessagesAsExitGroup() {
        return PreferenceManager.getInstance().isDeleteMessagesAsExitGroup();
    }

    public void setTransfeFileByUser(boolean value) {
        PreferenceManager.getInstance().setTransferFileByUser(value);
    }

    public boolean isSetTransferFileByUser() {
        return PreferenceManager.getInstance().isSetTransferFileByUser();
    }

    public void setAutodownloadThumbnail(boolean autodownload) {
        PreferenceManager.getInstance().setAudodownloadThumbnail(autodownload);
    }

    public boolean isSetAutodownloadThumbnail() {
        return PreferenceManager.getInstance().isSetAutodownloadThumbnail();
    }

    public void setAutoAcceptGroupInvitation(boolean value) {
        PreferenceManager.getInstance().setAutoAcceptGroupInvitation(value);
    }
    
    public boolean isAutoAcceptGroupInvitation() {
        return PreferenceManager.getInstance().isAutoAcceptGroupInvitation();
    }
    

    public void setAdaptiveVideoEncode(boolean value) {
        PreferenceManager.getInstance().setAdaptiveVideoEncode(value);
    }
    
    public boolean isAdaptiveVideoEncode() {
        return PreferenceManager.getInstance().isAdaptiveVideoEncode();
    }

    public void setPushCall(boolean value) {
        PreferenceManager.getInstance().setPushCall(value);
    }

    public boolean isPushCall() {
        return PreferenceManager.getInstance().isPushCall();
    }

    public boolean isMsgRoaming() {
        return PreferenceManager.getInstance().isMsgRoaming();
    }

    public void setMsgRoaming(boolean roaming) {
        PreferenceManager.getInstance().setMsgRoaming(roaming);
    }

    public boolean isShowMsgTyping() {
        return PreferenceManager.getInstance().isShowMsgTyping();
    }

    public void showMsgTyping(boolean show) {
        PreferenceManager.getInstance().showMsgTyping(show);
    }


    public void setUseFCM(boolean useFCM) {
        PreferenceManager.getInstance().setUseFCM(useFCM);
    }

    public boolean isUseFCM() {
        return PreferenceManager.getInstance().isUseFCM();
    }

    enum Key{
        VibrateAndPlayToneOn,
        VibrateOn,
        PlayToneOn,
        SpakerOn,
        DisabledGroups,
        DisabledIds
    }
}
