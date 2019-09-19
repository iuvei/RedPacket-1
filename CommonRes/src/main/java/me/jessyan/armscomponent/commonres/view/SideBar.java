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
package me.jessyan.armscomponent.commonres.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;

import me.jessyan.armscomponent.commonres.R;

public class SideBar extends View{
	// 字母变化监听事件

	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

	// 字母数组

	public static String[] bArray = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",

			"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

	// 选中

	private int choose = -1;

	private Paint paint = new Paint();

	// 点击后提示当前选中字母

	private TextView mTextDialog;

	public void setTextView(TextView mTextDialog) {

		this.mTextDialog = mTextDialog;

	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);

	}

	public SideBar(Context context, AttributeSet attrs) {

		super(context, attrs);

	}

	public SideBar(Context context) {

		super(context);

	}

	@Override

	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);

		// 获取屏幕高度和宽度

		int height = getHeight();

		int width = getWidth();

		// 设置字母高度

		float letterHeight = (height * 1f) / bArray.length;
		float margin = (letterHeight-ConvertUtils.sp2px (12))/2;
		for (int i = 0; i < bArray.length; i++) {

//            paint.setColor(Color.rgb(23, 122, 126));

			paint.setColor(getResources().getColor( R.color.public_black));

			paint.setTypeface( Typeface.DEFAULT_BOLD);

			// 抗锯齿

			paint.setAntiAlias(true);

			paint.setTextSize( ConvertUtils.sp2px (12));

			if (i == choose) {

				paint.setColor(Color.BLUE);

				// 设置为加粗字体

				paint.setFakeBoldText(true);

			}

			// x坐标等于中间-字符串宽度的一半.

			float xPos = width / 2 - paint.measureText( bArray[i]) / 2;

			float yPos = letterHeight * i + letterHeight;

			canvas.drawText( bArray[i], xPos, yPos-margin, paint);

			// 重置画笔

			paint.reset();

		}

	}

	@Override

	public boolean dispatchTouchEvent(MotionEvent event) {

		final int action = event.getAction();

		final float touch_y = event.getY();

		final int oldChoose = choose;

		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;

		// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

		final int c = (int) (touch_y / getHeight() * bArray.length);

		switch (action) {

			case MotionEvent.ACTION_UP:

				setBackgroundColor(Color.TRANSPARENT);

				choose = -1;

				invalidate();

				if (mTextDialog != null) {

					mTextDialog.setVisibility(View.INVISIBLE);

				}

				break;

			default:

				setBackgroundResource(R.color.public_color_CDD3D7);

				if (oldChoose != c) {

					if (c >= 0 && c < bArray.length) {

						if (listener != null) {

							listener.onTouchingLetterChanged( bArray[c]);

						}

						if (mTextDialog != null) {

							mTextDialog.setText( bArray[c]);

							mTextDialog.setVisibility(View.VISIBLE);

						}

						choose = c;

						invalidate();

					}

				}

				break;

		}

		return true;

	}

	/**

	 * 向外公开的方法

	 *

	 * @param onTouchingLetterChangedListener

	 */

	public void setOnTouchingLetterChangedListener(

			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {

		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;

	}

	/**

	 * 接口

	 *

	 * @author coder

	 */

	public interface OnTouchingLetterChangedListener {

		public void onTouchingLetterChanged(String s);

	}

}
