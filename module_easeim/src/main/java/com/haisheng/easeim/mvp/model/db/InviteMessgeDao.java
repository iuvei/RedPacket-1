package com.haisheng.easeim.mvp.model.db;

import android.content.ContentValues;

import com.haisheng.easeim.mvp.model.entity.InviteMessage;
import com.haisheng.easeim.mvp.model.entity.InviteMessage_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class InviteMessgeDao {

    private InviteMessgeDao(){
    }
    public static InviteMessgeDao getInstance(){
        return InviteMessgeDaoHoulder.inviteMessgeDaoInstance;
    }
    //静态内部类
    public static class InviteMessgeDaoHoulder {
        private static final InviteMessgeDao inviteMessgeDaoInstance = new InviteMessgeDao();
    }

    /**
     * save message
     * @param message
     * @return  return cursor of the message
     */
    public boolean saveMessage(InviteMessage message){
        return message.save();
    }

    /**
     * update message
     * @param msgId
     * @param messageStatus
     */
    public void updateMessageStatus(long msgId,InviteMessage.InviteMessageStatus messageStatus){
        SQLite.update(InviteMessage.class)
                .set(InviteMessage_Table.status.eq(messageStatus.ordinal()))
                .where(InviteMessage_Table.id.eq(msgId))
                .execute();
    }

    /**
     * get messges
     * @return
     */
    public List<InviteMessage> getMessagesList(){
        return SQLite.select().from(InviteMessage.class).queryList();
    }

    public void deleteMessage(String from){
        SQLite.delete(InviteMessage.class)
                .where(InviteMessage_Table.from.eq(from))
                .execute();
    }

    public void deleteGroupMessage(String groupId) {
        SQLite.delete(InviteMessage.class)
                .where(InviteMessage_Table.groupId.eq(groupId))
                .execute();
    }

    public void deleteGroupMessage(String groupId, String from) {
        SQLite.delete(InviteMessage.class)
                .where(InviteMessage_Table.groupId.eq(groupId),InviteMessage_Table.from.eq(from))
                .execute();
    }

    public int getUnreadMessagesCount(){
        // 第一种 先查后改
        InviteMessage message = SQLite.select()
                .from(InviteMessage.class)
                .querySingle();// 区别与 queryList()
        if (message != null) {
            return message.getUnReadMsgCount();
        }
        return 0;

    }

    public void saveUnreadMessageCount(int count){
        // 第一种 先查后改
        InviteMessage message = SQLite.select()
                .from(InviteMessage.class)
                .querySingle();// 区别与 queryList()
        if (message != null) {
            message.setUnReadMsgCount(count);
            message.update();
        }
    }

}
