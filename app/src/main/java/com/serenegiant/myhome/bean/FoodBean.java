package com.serenegiant.myhome.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity
public class FoodBean {
    @Id(autoincrement = true)
    public Long id;
    @Property
    public String picUrl;
    @Property
    public String name;
    @Property
    public String introduce;

    @Generated(hash = 1181425923)
    public FoodBean(Long id, String picUrl, String name, String introduce) {
        this.id = id;
        this.picUrl = picUrl;
        this.name = name;
        this.introduce = introduce;
    }

    @Generated(hash = 895705851)
    public FoodBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicUrl() {
        return this.picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return this.introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
