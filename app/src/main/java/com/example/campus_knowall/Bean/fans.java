package com.example.campus_knowall.Bean;

import android.provider.ContactsContract;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class fans extends BmobObject {
    private String FromUser;
    private int followerIdsum=0;
    private BmobRelation followerId;

    public void setFromUser(String fromUser) {
        FromUser = fromUser;
    }

    public String getFromUser() {
        return FromUser;
    }

    public void setFollowerId(BmobRelation followerId) {
        this.followerId = followerId;
    }

    public BmobRelation getFollowerId() {
        return followerId;
    }

    public void setFollowerIdsum(int followerIdsum) {
        this.followerIdsum = followerIdsum;
    }

    public int getFollowerIdsum() {
        return followerIdsum;
    }

    public void removeFan(BmobRelation hook)
    {
        this.setFollowerId(hook);
        this.followerIdsum--;
    }

    public void addFan(BmobRelation hook)
    {
        this.setFollowerId(hook);
        this.followerIdsum++;
    }


}
