package com.example.campus_knowall.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campus_knowall.Bean.User;
import com.example.campus_knowall.MainActivity;
import com.example.campus_knowall.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Login extends AppCompatActivity {
    private EditText username, password;
    private Button button;
    private TextView forgot, register_prompt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        button = findViewById(R.id.button_login);
        forgot = findViewById(R.id.forgot);
        register_prompt = findViewById(R.id.register_prompt);

        @SuppressLint("UseCompatLoadingForDrawables") Drawable account_1 = getResources().getDrawable(R.drawable.account);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable password_1 = getResources().getDrawable(R.drawable.password);
        account_1.setBounds(0, 0, 101, 92);
        password_1.setBounds(0, 0, 101, 92);
        username.setCompoundDrawables(account_1, null, null, null);
        password.setCompoundDrawables(password_1, null, null, null);

        //登录监听
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setUsername(username.getText().toString().trim());
                user.setPassword(password.getText().toString().trim());
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            Toast.makeText(Login.this, "登陆成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, MainActivity.class));
                        } else {
                            Toast.makeText(Login.this, "登陆失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //注册监听
        register_prompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
    }
}
