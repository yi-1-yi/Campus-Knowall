package com.example.campus_knowall.Bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

public class User extends BmobUser {
    private String avatar;
    private String nickname;
//现，该类已有属性：用户名name、密码、头像、昵称、电话号码、电话验证、邮箱、邮箱验证、唯一ID、创建日期、修改日期

    private String gender;
    //关注的人数
    private Integer focusId_sum = 0;

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

    public Integer getFocusId_sum() {
        return focusId_sum;
    }

    public void setFocusId_sum(Integer focusId_sum) {
        this.focusId_sum = focusId_sum;
    }

   /* public user_followers getFollower_id() {
        return follower_id;
    }

    public void setFollower_id(user_followers follower_id) {
        this.follower_id = follower_id;
    }*/
}
