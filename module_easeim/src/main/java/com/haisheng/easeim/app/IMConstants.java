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
package com.haisheng.easeim.app;

import com.hyphenate.easeui.EaseConstant;

/**
 * ================================================
 * Created by JessYan on 25/04/2018 16:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class IMConstants extends EaseConstant {

    public static final String IS_CONFLICT = "isConflict";
    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final String GROUP_USERNAME = "item_groups";
    public static final String CHAT_ROOM = "item_chatroom";
    public static final String ACCOUNT_REMOVED = "account_removed";
    public static final String ACCOUNT_CONFLICT = "conflict";
    public static final String ACCOUNT_FORBIDDEN = "user_forbidden";
    public static final String ACCOUNT_KICKED_BY_CHANGE_PASSWORD = "kicked_by_change_password";
    public static final String ACCOUNT_KICKED_BY_OTHER_DEVICE = "kicked_by_another_device";
    public static final String CHAT_ROBOT = "item_robots";
    public static final String MESSAGE_ATTR_ROBOT_MSGTYPE = "msgtype";
    public static final String ACTION_GROUP_CHANAGED = "action_group_changed";
    public static final String ACTION_CONTACT_CHANAGED = "action_contact_changed";

    public static final String EXTRA_CONFERENCE_ID = "confId";
    public static final String EXTRA_CONFERENCE_PASS = "password";
    public static final String EXTRA_CONFERENCE_INVITER = "inviter";
    public static final String EXTRA_CONFERENCE_IS_CREATOR = "is_creator";
    public static final String EXTRA_CONFERENCE_GROUP_ID = "group_id";

    public static final String MSG_ATTR_CONF_ID = "conferenceId";
    public static final String MSG_ATTR_CONF_PASS = EXTRA_CONFERENCE_PASS;
    public static final String MSG_ATTR_EXTENSION = "msg_extension";

    public static final String EM_CONFERENCE_OP = "em_conference_op";
    public static final String EM_CONFERENCE_ID = "em_conference_id";
    public static final String EM_CONFERENCE_PASSWORD = "em_conference_password";
    public static final String EM_CONFERENCE_TYPE = "em_conference_type";
    public static final String EM_MEMBER_NAME = "em_member_name";

    public static final String OP_INVITE = "invite";
    public static final String OP_REQUEST_TOBE_SPEAKER = "request_tobe_speaker";
    public static final String OP_REQUEST_TOBE_AUDIENCE = "request_tobe_audience";


    //红包
    public static final String MESSAGE_ATTR_TYPE = "isforb";
    public static final String MESSAGE_ATTR_CONENT= "content";
    public static final String MESSAGE_ATTR_REDPACKET_STATUS= "redpacketStatus";
    //加入房间的昵称
    public static final String MESSAGE_ATTR_JOIN_ROOM_NICKNAME= "nickname";

    public static final int ROOM_TYPE_MINE_REDPACKET = 0; //扫雷房间
    public static final int ROOM_TYPE_GUN_CONTROL_REDPACKET = 1; //禁抢房间
    public static final int ROOM_TYPE_NIUNIU_REDPACKET = 2; //牛牛房间
    public static final int ROOM_TYPE_NIUNIU_DOUBLE_REDPACKET = 3; //牛牛翻倍房间
    public static final int ROOM_TYPE_WELFARE_REDPACKET = 4; //福利房间

    public static final int MSG_TYPE_MINE_REDPACKET = 0;
    public static final int MSG_TYPE_GUN_CONTROL_REDPACKET = 1;
    public static final int MSG_TYPE_NIUNIU_REDPACKET = 2;
    public static final int MSG_TYPE_WELFARE_REDPACKET = 3;
    public static final int MSG_TYPE_GUN_CONTROL_SETTLEMENT  = 5; //禁抢房结算消息
    public static final int MSG_TYPE_NIUNIU_SETTLEMENT  = 6;  //牛牛房结算消息
    //获取红包状态
    public static final int MSG_TYPE_GET_REDPACKET  = 7;
    //客服帮助信息
    public static final int MSG_TYPE_HELP_MESSAGE  = 8;
    //奖励提醒消息
    public static final int MSG_TYPE_REWARD_MESSAGE  = 9;
    //加入房间提醒消息
    public static final int MSG_TYPE_JOINROOM_MESSAGE  = 11;


    //
    public static final int CONVERSATION_ALL = 0;
    public static final int CONVERSATION_FRIEND = 1;
    public static final int CONVERSATION_GROUP = 2;

    public static final int REQUEST_CODE_SEND_REDPACKET = 0x301;

    //领取红包字段
    public static final String REDPACKET_MSG_REDID = "msg_redid"; //红包id
    public static final String GET_REDPACKET_MSG_SENDNAME = "msg_sendname"; //发包人昵称
    public static final String GET_REDPACKET_MSG_SENDHXID = "msg_send_hxid"; //发包人hxid
    public static final String GET_REDPACKET_MSG_GETNAME = "msg_getname"; //领包人昵称
    public static final String GET_REDPACKET_MSG_GETHXID = "msg_get_hxid"; //领包人hxid
    public static final String GET_REDPACKET_MSG_TYPE = "msg_type";   //领取红包信息类型
    public static final String GET_REDPACKET_MSG_CLUES = "msg_clues"; //领取红包标识

}
