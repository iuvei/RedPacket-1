package me.jessyan.armscomponent.commonsdk.db;

import com.raizlabs.android.dbflow.annotation.Database;

import me.jessyan.armscomponent.commonsdk.utils.UserPreferenceManager;

/**
 * Created by Administrator on 2018/3/26.
 */
@Database(name = CommonDatabase.NAME,version = CommonDatabase.VERSION)
public class CommonDatabase {
    public static final String NAME = "CommonDatabase";

    public static final int VERSION = 1;

    String getDbName(){
        return "CommonDatabase"+ UserPreferenceManager.getInstance().getCurrentUserId();
    }

}
