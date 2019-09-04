package me.jessyan.armscomponent.commonsdk.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MyMultiItemEntity implements MultiItemEntity {
    public final static int TYPE_CUSTOM_MULTIITEM = 0x000999;

    private String name;

    private int imgResId;

    public MyMultiItemEntity(String name,int imgResId){
        this.name  = name;
        this.imgResId = imgResId;
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

    @Override
    public int getItemType() {
        return TYPE_CUSTOM_MULTIITEM;
    }
}
