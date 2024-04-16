package com.example.campus_knowall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campus_knowall.Bean.User;
import com.example.campus_knowall.MainActivity;
import com.example.campus_knowall.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class Password extends AppCompatActivity {
    private EditText na,pw,npw,npw2;
    private Button epw;
    private ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_password);
        initView();
        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //修改监听
        epw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User current_user = BmobUser.getCurrentUser(User.class);
                String name=na.getText().toString().trim();
                String password=pw.getText().toString().trim();
                String newpass=npw.getText().toString().trim();
                String newpass2=npw2.getText().toString().trim();
                Log.d("Password", "用户名是"+name+"，旧密码是" +password+"，新密码是"+newpass);
                //验证原密码是否正确,借鉴登录
                User user = new User();
                user.setUsername(name);
                user.setPassword(password);
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            Log.d("Password", "原密码输入正确");
                            //验证新密码两次输入是否一致
                            if (newpass.equals(newpass2)){
                                Log.d("Password", "新密码两次输入一致");
                                BmobQuery<User> query = new BmobQuery<>();
                                query.addWhereEqualTo("objectId",current_user.getObjectId());
                                query.findObjects(new FindListener<User>() {
                                    @Override
                                    public void done(List<User> list, BmobException e) {
                                        if (e == null && list.size() > 0) {
                                            User object = list.get(0);
                                            object.setPassword(newpass);
                                            object.setPassword(newpass);
                                            Log.d("Password", "用户"+current_user.getObjectId()+"已经把密码改为"+newpass);
                                            Toast.makeText(Password.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                                            finish();
                                            object.update(new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if (e == null) {
                                                        // 更新成功
                                                        Log.d("Edit", "更新成功，请查看数据库");
                                                    } else {
                                                        // 更新失败
                                                    }
                                                }
                                            });
                                        } else {
                                            // 查询失败或未找到匹配记录
                                        }
                                    }
                                });
                            }else {
                                Log.d("Password", "两次输入不一致");
                                Toast.makeText(Password.this, "两次输入不一致", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("Password", "密码输入错误，不是本人");
                            Toast.makeText(Password.this, "密码输入错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    private void initView() {
        na=findViewById(R.id.na);
        pw= findViewById(R.id.pw);
        npw = findViewById(R.id.npw);
        npw2=findViewById(R.id.npw2);
        epw=findViewById(R.id.epw);
        back=findViewById(R.id.back);
    }
}
