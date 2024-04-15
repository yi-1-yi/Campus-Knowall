package com.example.campus_knowall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.campus_knowall.Adapter.MycollectpushAdapter;
import com.example.campus_knowall.Bean.Comunity;
import com.example.campus_knowall.Bean.Post;
import com.example.campus_knowall.Bean.User;
import com.example.campus_knowall.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class MyInfo extends AppCompatActivity {
    private ImageView back;
    private Button focus;
    private TextView my_pushnum,my_comnum,my_nickname,usercreattime;

    private TextView info_title;
    private ImageView my_gender;

//    private String user_onlyid;
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;
    private FragmentStatePagerItemAdapter fragadapter;

    String user_onlyid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        Intent intent = getIntent();
        user_onlyid = intent.getStringExtra("user_onlyid");
        initView();
        getotherInfo();


        //收藏监听
        focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = getIntent();
                String Id = in.getStringExtra("user_onlyid");
                if(Id==null)
                {
                    Toast.makeText(MyInfo.this, "参数呢？", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MyInfo.this, "参数是"+Id, Toast.LENGTH_SHORT).show();
                }

                BmobQuery<User> bmobQuery = new BmobQuery<>();
                bmobQuery.getObject(Id, new QueryListener<User>()
                {
                    @Override
                    public void done(User worker, BmobException e) {
                        if(worker.getIsrelated()==null)
                        {
                            Toast.makeText(MyInfo.this, "查了个寂寞", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MyInfo.this, "你是dd", Toast.LENGTH_SHORT).show();
                        }
                        if (worker.getIsrelated().equals("0")) {




                            Intent in = getIntent();
                            String Id = in.getStringExtra("user_onlyid");
                            User user = BmobUser.getCurrentUser(User.class);
                            User woq = new User();
                            woq.setObjectId(Id);
                            woq.setIsrelated("1");

                            BmobRelation relation = new BmobRelation();
                            relation.add(user);
                            woq.setFollowerId(relation);
                            woq.increment("followerIdsum");

                            BmobRelation focusrelation = new BmobRelation();
                            focusrelation.add(woq);
                            user.setFocuId(focusrelation);
                            user.increment("focusIdsum");


                            woq.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        focus.setText("已关注");
                                        Toast.makeText(MyInfo.this, "关注成功", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(MyInfo.this, "关注失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else {
                            Intent in = getIntent();
                            String Id = in.getStringExtra("user_onlyid");
                            User user = BmobUser.getCurrentUser(User.class);
                            User woq = new User();
                            woq.setObjectId(Id);
                            woq.setIsrelated("0");

                            BmobRelation relation = new BmobRelation();
                            relation.remove(user);
                            woq.removeFollower(relation);

                            BmobRelation focusrelation =new BmobRelation();
                            focusrelation.remove(woq);
                            user.removeFocus(focusrelation);

                            woq.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        focus.setText("关注");
                                        Toast.makeText(MyInfo.this, "取消关注成功", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(MyInfo.this, "取消关注失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

            }
        });


        //配置veiwpager
        viewPager.setOffscreenPageLimit(3);

        initTab();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }



    private void initTab() {

        String[] tabs = new String[]{"动态","话题"};
        FragmentPagerItems pages = new FragmentPagerItems(MyInfo.this);
        for (int i = 0 ; i<tabs.length ; i++){
            Bundle args = new Bundle();
            args.putString("name",tabs[i]);
        }
        pages.add(FragmentPagerItem.of(tabs[0],Fragment_MyPush.class));
        pages.add(FragmentPagerItem.of(tabs[1],Fragment_MyComunity.class));
        viewPager.removeAllViews();
        fragadapter = new FragmentStatePagerItemAdapter(getSupportFragmentManager(),pages);
        viewPager.setAdapter(fragadapter);
        smartTabLayout.setViewPager(viewPager);

    }

    private void getothercomunitynum() {

        BmobQuery<Comunity> query = new BmobQuery<>();
        query.addWhereEqualTo("user_onlyid",user_onlyid);
        query.include("user");
        query.order("-createdAt");
        query.findObjects(new FindListener<Comunity>() {
            @Override
            public void done(List<Comunity> list, BmobException e) {
                if (e == null){
                    String size = String.valueOf(list.size());
                    my_comnum.setText(size);
                }else {
                    Toast.makeText(MyInfo.this, "查询帖数失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getotherpushnum() {

        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("user_onlyid",user_onlyid);
        query.include("author");
        query.order("-createdAt");
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if (e == null){
                    String size = String.valueOf(list.size());
                    my_pushnum.setText(size);
                }else {
                    Toast.makeText(MyInfo.this, "查询帖数失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getotherInfo() {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.include("follower_id");
        bmobQuery.getObject(user_onlyid, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e==null){
                    /* info_title.setText(user.getUsername());*/
                    my_nickname.setText(user.getNickname());
                    // followeList_id = user.getFollower_id().getObjectId();
                    if (user.getGender().equals("man")){
                        my_gender.setImageResource(R.drawable.man);
                    }else {
                        my_gender.setImageResource(R.drawable.gril);
                    }
                }else {
                    Toast.makeText(MyInfo.this, "加载失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getmycomumitynum() {
        //获取我创建的论坛数
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Comunity> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("user",user);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Comunity>() {
            @Override
            public void done(List<Comunity> list, BmobException e) {
                String size = String.valueOf(list.size());
                my_comnum.setText(size);
            }
        });
    }

    private void getmypushnum() {
        //获取我发布的帖子数
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Post> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("author",user);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                String size = String.valueOf(list.size());
                my_pushnum.setText(size);
            }
        });
    }

    private void getmyInfo() {
        //获取我的个人信息(名字和签名)
        User auser = BmobUser.getCurrentUser(User.class);
        String Id = auser.getObjectId();
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(Id, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e==null){
                   /* info_title.setText(user.getUsername());*/
                    my_nickname.setText(user.getNickname());
                    //followeList_id = user.getFollower_id().getObjectId();
                    if (user.getGender().equals("man")){
                        my_gender.setImageResource(R.drawable.man);
                    }else {
                        my_gender.setImageResource(R.drawable.gril);
                    }
                } //Toast.makeText(MyInfo.this, "加载失败", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initView() {
        back = findViewById(R.id.back);
//        my_pushnum = findViewById(R.id.my_pushnum);
//        my_comnum = findViewById(R.id.my_comnum);
        my_nickname = findViewById(R.id.nickname001);
        my_gender = findViewById(R.id.my_gender);
        info_title = findViewById(R.id.info_title);
       // editmyinfo = findViewById(R.id.editmyinfo);
        smartTabLayout = findViewById(R.id.myinfotab);
        viewPager = findViewById(R.id.myinfovp);
        focus = findViewById(R.id.focus);

     //   focus_or_not = findViewById(R.id.focus);
    }



}

