package com.haisheng.easeim.mvp.model.entity;

import com.haisheng.easeim.mvp.model.db.IMDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

public class ChatExtendItemEntity implements Serializable {

    private int id;
    private String name;
    private int imgResId;

    public ChatExtendItemEntity(int id, String name, int imgResId) {
        this.id = id;
        this.name = name;
        this.imgResId = imgResId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }
}
