package com.example.campus_knowall.Bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Comunity extends BmobObject {

    private User user;

    private String name,info,onwer;

    private BmobRelation relation;

    private String userOnlyId;


    public String getUserOnlyId() {
        return userOnlyId;
    }

    public void setUserOnlyId(String userOnlyId) {
        this.userOnlyId = userOnlyId;
    }

    public BmobRelation getRelation() {
        return relation;
    }

    public void setRelation(BmobRelation relation) {
        this.relation = relation;
    }

    private String isrelated;  //标识值

    public String getIsrelated() {   //ALT + INS
        return isrelated;
    }

    public void setIsrelated(String isrelated) {
        this.isrelated = isrelated;
    }

    public String getOnwer() {
        return onwer;
    }

    public void setOnwer(String onwer) {
        this.onwer = onwer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
