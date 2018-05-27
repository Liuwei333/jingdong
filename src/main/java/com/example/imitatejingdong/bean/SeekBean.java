package com.example.imitatejingdong.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/5/21.
 */

@Entity
public class SeekBean {

    @Id
    Long id;
    String name;
    @Generated(hash = 734330410)
    public SeekBean(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 177163970)
    public SeekBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
