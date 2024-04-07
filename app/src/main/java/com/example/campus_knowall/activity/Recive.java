package com.example.campus_knowall.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campus_knowall.R;

public class Recive extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recive);

    }

    private void initData(){
        Intent in = getIntent();
        String id = in.getStringExtra(name:"id");

        BmobQuery<Post> query = new BmoQuery<>();
        query.getObject(id,new QueryListener<Post>() {
            @Override
            public void done(Post post,BmobException e) {
                if(e==null){
                    username.setText(post.getName());
                    context.setText(post.getContext());
                    time.setText(post.getCreatedAt());
                }else{
                    Toast.makeText(context:Recive.this,text:"获取失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView(){
        username=findViewById(R.id.username);
        content=findViewById(R.id.content);
        time=findViewById(R.id.time);
    }
}

