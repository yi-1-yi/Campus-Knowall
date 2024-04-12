package com.example.campus_knowall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campus_knowall.Bean.Post;
import com.example.campus_knowall.Bean.User;
import com.example.campus_knowall.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class PushContent extends AppCompatActivity {

    private EditText  pushtitle,pushcontent;
    private ImageView back;
    private Button push;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushcontent);

        initView();



        //发帖操作
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pushcontent.getText().toString().isEmpty()){
                    Toast.makeText(PushContent.this, "请输入内容", Toast.LENGTH_SHORT).show();
                }else {
                    User user = BmobUser.getCurrentUser(User.class);
                    Post po = new Post();
                    po.setNickname(user.getUsername());
                    po.setTitle(pushtitle.getText().toString());
                    po.setContent(pushcontent.getText().toString());
                    po.setIsrelated("0");
                    po.setAuthor(user);
                    po.setUserOnlyId(user.getObjectId());
                    po.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                pushtitle.setText("");
                                pushcontent.setText("");
                                Toast.makeText(PushContent.this, "发布成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(PushContent.this, "发布失败", Toast.LENGTH_SHORT).show();
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
        pushcontent = findViewById(R.id.pushcontent);
        pushtitle=findViewById(R.id.pushtitle);
        push = findViewById(R.id.push);
        back = findViewById(R.id.back);
    }
}
