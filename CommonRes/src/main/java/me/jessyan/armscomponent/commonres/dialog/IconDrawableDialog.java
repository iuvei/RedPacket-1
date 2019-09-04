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
package me.jessyan.armscomponent.commonres.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.DeviceUtils;

import me.jessyan.armscomponent.commonres.R;


public class IconDrawableDialog extends ProgressDialog {

    private Context mContext;
    private TextView mTvMsg;

    private String mMsg;
    public IconDrawableDialog(Context context) {
        this(context,R.style.public_dialog_progress);
    }

    public IconDrawableDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View containerView = LayoutInflater.from(mContext).inflate(R.layout.public_dialog_porgress,null);
        mTvMsg = containerView.findViewById(R.id.tv_load);
        setContentView(containerView);
        if(null == mMsg){
            mMsg = getContext().getString(R.string.public_loading);
        }
        mTvMsg.setText(mMsg);
    }

    @Override
    public void setMessage(CharSequence message) {
        super.setMessage(message);
        mMsg = message.toString();
        if(null != mTvMsg)
            mTvMsg.setText(message);
    }

    @Override
    public void show() {
        if(null != mTvMsg)
            mTvMsg.setText(mMsg);
        super.show();
    }
}
