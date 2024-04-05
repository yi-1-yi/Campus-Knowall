package com.example.campus_knowall.Bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser {
    private BmobFile avatar;
//现，该类已有属性：昵称、密码、头像、电话号码、电话验证、邮箱、邮箱验证、
    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }
}
