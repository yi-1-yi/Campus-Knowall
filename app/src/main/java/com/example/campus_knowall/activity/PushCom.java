package com.example.campus_knowall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campus_knowall.Bean.Com;
import com.example.campus_knowall.Bean.Post;
import com.example.campus_knowall.Bean.User;
import com.example.campus_knowall.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class PushCom extends AppCompatActivity {
    private EditText com;
    private ImageView back;
    private Button pushc;

    Intent intent = getIntent();
    String id = intent.getStringExtra("id");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushcom);
        initView();
        pushc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (com.getText().toString().isEmpty()){
                    Toast.makeText(PushCom.this, "请输入内容", Toast.LENGTH_SHORT).show();
                }else {
                    User user = BmobUser.getCurrentUser(User.class);
                    Com c=new Com();
                    //获取帖子编号，不能直接得到，点击时传递
                    c.setPostid(id);
                    c.setUserid(user);
                    c.setCom(com.getText().toString());
                    c.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                com.setText("");
                                Toast.makeText(PushCom.this, "回复成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(PushCom.this, "回复失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initView() {
        pushc = findViewById(R.id.pushc);
        com=findViewById(R.id.com);
        back = findViewById(R.id.back);
    }
}
