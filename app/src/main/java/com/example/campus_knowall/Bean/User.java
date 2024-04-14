package com.example.campus_knowall.Bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

public class User extends BmobUser {
    private String avatar;
    private String nickname;
//现，该类已有属性：用户名name、密码、头像、昵称、电话号码、电话验证、邮箱、邮箱验证、唯一ID、创建日期、修改日期

    private String gender;
    //关注的人数
    private Integer focusIdsum = 0;

  //  private user_followers follower_id;

    private BmobRelation focuId;

    public BmobRelation getFocuId() {
        return focuId;
    }

    public void setFocuId(BmobRelation focuId) {
        this.focuId = focuId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getFocusIdsum() {
        return focusIdsum;
    }

    public void setFocusIdsum(Integer focusIdsum) {
        this.focusIdsum = focusIdsum;
    }

    private Integer followerIdsum=0;
    private BmobRelation followerId;

    public BmobRelation getFollowerId() {
        return followerId;
    }
    public void setFollowerId(BmobRelation followerId) {
        this.followerId = followerId;
    }

    public Integer getFollowerIdsum() {
        return followerIdsum;
    }
    public void setFollowerIdsum(Integer followerIdsum) {
        this.followerIdsum = followerIdsum;
    }

   /* public user_followers getFollower_id() {
        return follower_id;
    }

    public void setFollower_id(user_followers follower_id) {
        this.follower_id = follower_id;
    }*/
}
