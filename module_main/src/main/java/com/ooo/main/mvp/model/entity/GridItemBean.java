package com.ooo.main.mvp.model.entity;

import java.io.Serializable;

public class GridItemBean implements Serializable {

    private int textResId;
    private int iconResId;

    public GridItemBean(int textResId, int iconResId) {
        this.textResId = textResId;
        this.iconResId = iconResId;
    }

    public int getTextResId() {
        return textResId;
    }

    public void setTextResId(int textResId) {
        this.textResId = textResId;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
}
