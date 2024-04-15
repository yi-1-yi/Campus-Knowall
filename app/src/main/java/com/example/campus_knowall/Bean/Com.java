package com.example.campus_knowall.Bean;

import cn.bmob.v3.BmobObject;

public class Com extends BmobObject {
    private User userid;
    private String postid;
    private String com;

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }
}
