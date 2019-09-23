package com.haisheng.easeim.mvp.model.entity;

import com.haisheng.easeim.mvp.model.db.IMDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.io.Serializable;

@Table(database = IMDatabase.class)
public class SystemMessage implements Serializable {
    @PrimaryKey(autoincrement = true)
    private Long id;
    @Column
    private long time;
    @Column
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SystemMessage{" +
                "id=" + id +
                ", time=" + time +
                ", message='" + message + '\'' +
                '}';
    }
}
