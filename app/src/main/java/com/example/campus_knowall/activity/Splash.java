package com.example.campus_knowall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campus_knowall.Bean.User;
import com.example.campus_knowall.BmobApp;
import com.example.campus_knowall.MainActivity;
import com.example.campus_knowall.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Timer timer=new Timer();
        timer.schedule(timetast,2000);
        Bmob.initialize(this, "417e88a4dde438927b0c164bb450d7e3");
    }
    TimerTask timetast=new TimerTask() {
        @Override
        public void run() {
            BmobUser bmobUser=BmobUser.getCurrentUser(User.class);
            if(bmobUser!=null){
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
            }else{
                startActivity(new Intent(Splash.this,Login.class));
                finish();
            }

        }
    };
}
