package com.haisheng.easeim.mvp.model;

import android.content.Context;
import android.util.Pair;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.Utils;
import com.haisheng.easeim.R;
import com.haisheng.easeim.app.IMConstants;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.http.Api;
import me.jessyan.armscomponent.commonsdk.http.BaseResponse;


public class ConversationModel extends BaseModel{

    public ConversationModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<BaseResponse> clearAllConversations() {
        return  Observable.create(emitter -> {

            Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
            if(conversations.size()>0){
                for (String username : conversations.keySet()) {
                    EMClient.getInstance().chatManager().deleteConversation(username, true);
                }
            }

            BaseResponse response = new BaseResponse();
            response.setStatus(Api.REQUEST_SUCCESS_CODE);
            Context context = Utils.getApp();
            response.setMessage(context.getString(R.string.public_load_success));
            emitter.onNext(response);
            emitter.onComplete();
        });
    }

    public List<EMConversation> loadConversationListByTag(int tag) {
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if(tag ==  IMConstants.CONVERSATION_FRIEND){
                    if(conversation.isGroup()) continue;

                }else if(tag ==  IMConstants.CONVERSATION_GROUP){
                    if(!conversation.isGroup()) continue;
                }
                if (conversation.getLastMessage () == null){
                    sortList.add(new Pair<Long, EMConversation>(System.currentTimeMillis (), conversation));
                }else{
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }

    /**
     * sort conversations according time stamp of last message
     *
     * @param conversationList
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }
}