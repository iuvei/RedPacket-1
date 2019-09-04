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

package com.haisheng.easeim.app.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.haisheng.easeim.app.IMHelper;
import com.haisheng.easeim.mvp.ui.activity.VideoCallActivity;
import com.haisheng.easeim.mvp.ui.activity.VoiceCallActivity;
import com.hyphenate.util.EMLog;

public class CallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(!IMHelper.getInstance().isLoggedIn())
		    return;
		//username
		String from = intent.getStringExtra("from");
		//call type
		String type = intent.getStringExtra("type");
		if("video".equals(type)){ //video call
			Intent videoCallIntent = new Intent(context, VideoCallActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("username", from);
			bundle.putBoolean("isComingCall", true);
			videoCallIntent.putExtras(bundle);
			videoCallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(videoCallIntent);
		}else{ //voice call

			Intent voiceCallIntent = new Intent(context, VoiceCallActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("username", from);
			bundle.putBoolean("isComingCall", true);
			voiceCallIntent.putExtras(bundle);
			voiceCallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(voiceCallIntent);
		}
		EMLog.d("CallReceiver", "app received a incoming call");
	}

}
