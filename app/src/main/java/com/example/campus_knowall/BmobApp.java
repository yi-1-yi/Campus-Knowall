package com.example.campus_knowall;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class BmobApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "417e88a4dde438927b0c164bb450d7e3");
    }
}