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
package com.haisheng.easeim.mvp.ui.widget.sidelist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.haisheng.easeim.R;

import me.jessyan.armscomponent.commonres.view.recyclerview.TopLayoutManager;

public class Sidebar extends View{
	private Paint paint;
	private TextView header;
	private float itemHeight;
	private RecyclerView mListView;
	private Context context;

	private SectionIndexer sectionIndex = null;

	public void setListView(RecyclerView listView){
		mListView = listView;
	}


	public Sidebar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private String[] sections;

	private void init(){
		String st = "搜";
		sections= new String[]{st,"A","B","C","D","E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","#"};
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.parseColor("#8C8C8C"));
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(SizeUtils.sp2px(10));

	}

	public void setSections(String[] sections){
		this.sections = sections;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		float center = getWidth() / 2;
		itemHeight = getHeight() / sections.length;
		for (int i = sections.length - 1; i > -1; i--) {
			canvas.drawText(sections[i], center, itemHeight * (i+1), paint);
		}
	}


	private int sectionForPoint(float y) {
		int index = (int) (y / itemHeight);
		if(index < 0) {
			index = 0;
		}
		if(index > sections.length - 1){
			index = sections.length - 1;
		}
		return index;
	}

	private void setHeaderTextAndscroll(MotionEvent event){
		if (mListView == null) {
			//check the mListView to avoid NPE. but the mListView shouldn't be null
			//need to check the call stack later
			return;
		}
		String headerString = sections[sectionForPoint(event.getY())];
		header.setText(headerString);
		RecyclerView.Adapter adapter = mListView.getAdapter();
		if(sectionIndex == null){
			if(adapter instanceof SectionIndexer){
				sectionIndex = (SectionIndexer)adapter;
			}else{
				throw new RuntimeException("listview sets adapter does not implement SectionIndexer interface");
			}
		}
		String[] adapterSections = (String[]) sectionIndex.getSections();
		try {
			for (int i = adapterSections.length - 1; i > -1; i--) {
				if(adapterSections[i].equals(headerString)){
					int pos = sectionIndex.getPositionForSection(i);
//					TopLayoutManager layoutManager = (TopLayoutManager) mListView.getLayoutManager();
					mListView.smoothScrollToPosition(pos);
					break;
				}
			}
		} catch (Exception e) {
			Log.e("setHeaderTextAndScroll", e.getMessage());
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:{
				if(header == null){
					//header = (TextView) ((View)getParent()).findViewById(R.id.tv_floating_header);
				}
				setHeaderTextAndscroll(event);
				header.setVisibility(View.VISIBLE);
				setBackgroundResource(R.drawable.sidebar_background_pressed);
				return true;
			}
			case MotionEvent.ACTION_MOVE:{
				setHeaderTextAndscroll(event);
				return true;
			}
			case MotionEvent.ACTION_UP:
				header.setVisibility(View.INVISIBLE);
				setBackgroundColor(Color.TRANSPARENT);
				return true;
			case MotionEvent.ACTION_CANCEL:
				header.setVisibility(View.INVISIBLE);
				setBackgroundColor(Color.TRANSPARENT);
				return true;
		}
		return super.onTouchEvent(event);
	}

}
