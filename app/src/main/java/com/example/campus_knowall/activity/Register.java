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
import com.example.campus_knowall.Bean.fans;
import com.example.campus_knowall.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Register extends AppCompatActivity {
    private EditText username, password, password2;
    private Button button_register;
    private TextView login_prompt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        button_register = findViewById(R.id.button_register);
        login_prompt = findViewById(R.id.login_prompt);

        @SuppressLint("UseCompatLoadingForDrawables") Drawable account_2 = getResources().getDrawable(R.drawable.account);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable password_2 = getResources().getDrawable(R.drawable.password);
        account_2.setBounds(0, 0, 101, 92);
        password_2.setBounds(0, 0, 101, 92);
        username.setCompoundDrawables(account_2, null, null, null);
        password.setCompoundDrawables(password_2, null, null, null);
        password2.setCompoundDrawables(password_2, null, null, null);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setUsername(username.getText().toString().trim());
                if (password.getText().toString().equals(password2.getText().toString())) {
                    user.setPassword(password.getText().toString().trim());
                } else {
                    Toast.makeText(Register.this, "两次密码输入不同，请重新输入", Toast.LENGTH_SHORT).show();
                }
                if (username.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "用户名没有输入", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "密码没有输入", Toast.LENGTH_SHORT).show();
                } else {
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null) {
                                fans myfan=new fans();
                                myfan.setFromUser(BmobUser.getCurrentUser(User.class).getObjectId());
                                Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();

                                finish();
                            } else {
                                Toast.makeText(Register.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        login_prompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });
    }
}
