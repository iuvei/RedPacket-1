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
package com.haisheng.easeim.mvp.model.db;


import com.haisheng.easeim.mvp.model.entity.ChatRoomBean;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class ChatRoomDao {

	private ChatRoomDao(){
	}
	public static ChatRoomDao getInstance(){
		return ChatRoomDao.ChatRoomDaoHoulder.userDaoInstance;
	}
	//静态内部类
	public static class ChatRoomDaoHoulder {
		private static final ChatRoomDao userDaoInstance = new ChatRoomDao();
	}


	public boolean saveChatRoom(ChatRoomBean roomBean){
		return roomBean.save();
	}

/*	*//**
	 * save room list
	 *
	 * @param roomList
	 *//*
	public void saveRoomList(List<ChatRoomBean> roomList) {
		Delete.table(ChatRoomBean_Table.class);

		FlowManager.getDatabase(IMDatabase.class)
				.executeTransaction(new ProcessModelTransaction.Builder<ChatRoomBean>(
						BaseModel::save
				).addAll(roomList).build());
	}*/

	/**
	 * get room list
	 *
	 * @return
	 */
	public List<ChatRoomBean> getRoomList() {
		return SQLite.select().from(ChatRoomBean.class).queryList();
	}

/*	*//**
	 * delete a room
	 * @param id
	 *//*
	public void deleteRoom(Long id){
		SQLite.delete()
				.from(ChatRoomBean.class)
				.where(ChatRoomBean_Table.id.eq(id))
				.query();
	}*/
}
