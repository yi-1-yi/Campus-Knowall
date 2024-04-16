package com.example.campus_knowall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campus_knowall.R;

import cn.bmob.v3.BmobUser;

public class Setting extends AppCompatActivity {

    private Button loginout;
    private ImageView back;
    private LinearLayout einfo,epass;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();

        loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                startActivity(new Intent(Setting.this, Login.class));
                //
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        einfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转修改信息界面
                Intent intent=new Intent(Setting.this,Information.class);
                startActivity(intent);
//                finish();
            }
        });
        epass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转修改密码界面
                startActivity(new Intent(Setting.this, Password.class));
            }
        });
    }

    private void initView() {
        back = findViewById(R.id.back);
        loginout = findViewById(R.id.loginout);
        einfo=findViewById(R.id.einfo);
        epass=findViewById(R.id.epass);
    }
}
