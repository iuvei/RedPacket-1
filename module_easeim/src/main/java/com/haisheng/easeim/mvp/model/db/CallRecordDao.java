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


import com.haisheng.easeim.mvp.model.entity.CallRecordEntity;
import com.haisheng.easeim.mvp.model.entity.CallRecordEntity_Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.db.CommonDatabase;

public class CallRecordDao {

	private CallRecordDao(){
	}
	public static CallRecordDao getInstance(){
		return CallRecordDao.CallRecordDaoHoulder.CallRecordDaoInstance;
	}
	//静态内部类
	public static class CallRecordDaoHoulder {
		private static final CallRecordDao CallRecordDaoInstance = new CallRecordDao();
	}


	/**
	 * save CallRecordEntity list
	 * 
	 * @param entities
	 */
	public void saveCallRecordEntites(List<CallRecordEntity> entities) {
		FlowManager.getDatabase(IMDatabase.class)
				.executeTransaction(new ProcessModelTransaction.Builder<CallRecordEntity>(
						BaseModel::save
				).addAll(entities).build());
	}

	/**
	 * get contact list
	 * 
	 * @return
	 */
	public List<CallRecordEntity> getCallRecordEntites() {
		return SQLite.select().from(CallRecordEntity.class).orderBy(CallRecordEntity_Table.timestamp,true).queryList();
	}

	public List<CallRecordEntity> selectCallRecordEntitesOrderByTimestamp(long timestamp,int num){
        return SQLite.select().from(CallRecordEntity.class)
                .where(CallRecordEntity_Table.timestamp.lessThan(timestamp))
                .orderBy(CallRecordEntity_Table.timestamp,false)
                .limit(num)
                .queryList();
    }

	/**
	 * delete a contact
	 * @param username
	 */
	public void deleteCallRecordEntity(String username){
		SQLite.delete()
				.from(CallRecordEntity.class)
				.where(CallRecordEntity_Table.username.eq(username))
				.query();
	}
	
	/**
	 * @param entity
	 */
	public void saveCallRecordEntity(CallRecordEntity entity){
		entity.save();
	}
}
