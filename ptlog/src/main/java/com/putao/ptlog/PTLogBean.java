package com.putao.ptlog;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import java.util.Date;

/**
 * Created by xiaopeng on 2017/7/28.
 */

@Entity
public class PTLogBean {
    @Id
    private Long id;

    @NotNull
    private int level;
    private String content;
    private java.util.Date date;
    @Generated(hash = 846259713)
    public PTLogBean(Long id, int level, String content, java.util.Date date) {
        this.id = id;
        this.level = level;
        this.content = content;
        this.date = date;
    }
    @Generated(hash = 935173162)
    public PTLogBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getLevel() {
        return this.level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public java.util.Date getDate() {
        return this.date;
    }
    public void setDate(java.util.Date date) {
        this.date = date;
    }
}
