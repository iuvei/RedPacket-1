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


import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.db.CommonDatabase;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo;
import me.jessyan.armscomponent.commonsdk.entity.UserInfo_Table;

public class UserDao {

	private UserDao(){
	}
	public static UserDao getInstance(){
		return UserDao.UserDaoHoulder.userDaoInstance;
	}
	//静态内部类
	public static class UserDaoHoulder {
		private static final UserDao userDaoInstance = new UserDao();
	}

	/**
	 * save contact list
	 * 
	 * @param contactList
	 */
	public void saveContactList(List<UserInfo> contactList) {
		FlowManager.getDatabase(CommonDatabase.class)
				.executeTransaction(new ProcessModelTransaction.Builder<UserInfo>(
						BaseModel::save
				).addAll(contactList).build());
	}

	/**
	 * get contact list
	 * 
	 * @return
	 */
	public List<UserInfo> getContactList() {
		return SQLite.select().from(UserInfo.class).queryList();
	}

	public UserInfo getUserEntityByHxId(String hxId){
		return SQLite.select().from(UserInfo.class).where(UserInfo_Table.hxId.eq(hxId)).querySingle();
	}
	
	/**
	 * delete a contact
	 * @param hxId
	 */
	public void deleteContact(String hxId){
		SQLite.delete()
				.from(UserInfo.class)
				.where(UserInfo_Table.hxId.eq(hxId))
				.query();
	}
	
	/**
	 * save a contact
	 * @param user
	 */
	public void saveContact(UserInfo user){
		user.save();
	}
}
