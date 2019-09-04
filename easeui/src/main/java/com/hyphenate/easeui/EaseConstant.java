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
package com.hyphenate.easeui;

public class EaseConstant {
    public static final String MESSAGE_ATTR_IS_VOICE_CALL = "is_voice_call";
    public static final String MESSAGE_ATTR_IS_VIDEO_CALL = "is_video_call";
    
    public static final String MESSAGE_TYPE_RECALL = "message_recall";
    
    public static final String MESSAGE_ATTR_IS_BIG_EXPRESSION = "em_is_big_expression";
    public static final String MESSAGE_ATTR_EXPRESSION_ID = "em_expression_id";
    
    public static final String MESSAGE_ATTR_AT_MSG = "em_at_list";
    public static final String MESSAGE_ATTR_VALUE_AT_MSG_ALL = "ALL";

    
    
	public static final int CHATTYPE_SINGLE = 1;
    public static final int CHATTYPE_GROUP = 2;
    public static final int CHATTYPE_CHATROOM = 3;
    
    public static final String EXTRA_CHAT_TYPE = "chatType";
    public static final String EXTRA_USER_ID = "userId";

    //收费
    public static final String MESSAGE_ATTR_CHARGE_COINS = "charge_coins";
    public static final String MESSAGE_ATTR_IS_PAID = "is_paid";
    //会话邀请
    public static final String MESSAGE_ATTR_IS_CALL_INVITE = "is_call_invite";
    public static final String MESSAGE_ATTR_IS_CALL_ALREADY = "is_call_already";
    public static final String MESSAGE_ATTR_CALL_INVITE_TYPE = "invite_type";
    public static final int CALL_TYPE_VOICE = 0;
    public static final int CALL_TYPE_VIDEO = 1;
    //礼物
    public static final String MESSAGE_ATTR_IS_GIFT = "is_gift";
    public static final String MESSAGE_ATTR_IS_GIFT_ASK = "is_gift_ask";
    public static final String MESSAGE_ATTR_GIFT_ID = "gift_id";
    public static final String MESSAGE_ATTR_GIFT_NAME = "gift_name";
    public static final String MESSAGE_ATTR_GIFT_ICON_URL = "gift_icon_url";
//    public static final String MESSAGE_ATTR_GIFT_NEED_COINS = "gift_need_coins";
}
