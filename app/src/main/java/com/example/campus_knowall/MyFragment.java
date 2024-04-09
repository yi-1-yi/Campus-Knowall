package com.example.campus_knowall;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.campus_knowall.Bean.User;
import com.example.campus_knowall.activity.MyCollect;
import com.example.campus_knowall.activity.MyComunity;
import com.example.campus_knowall.activity.MyFocus;
import com.example.campus_knowall.activity.MyFollower;
import com.example.campus_knowall.activity.MyInfo;
import com.example.campus_knowall.activity.MyPush;
import com.example.campus_knowall.activity.Setting;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.QueryListener;

public class MyFragment extends Fragment {
    private TextView username;

    private LinearLayout myinfo;
    private LinearLayout mypush;
    private LinearLayout mycomunity;
    private LinearLayout mycollect;
    private LinearLayout setting;

    private LinearLayout followactivity;
    private LinearLayout focusactivity;

    private TextView myfocusnum;

    private TextView fansnum;

    private ImageView mine_gender;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_my,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        //加载我的信息
        getMyinfo();

        //设置粉丝和关注数的字体
        setnumfont();

        //获取我的关注别人的数量
        getMyfocusnum();

        //获取我的粉丝数
        getMyfansnum();


        //监听followactivity
        followactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), MyFollower.class);
                it.putExtra("objectId", BmobUser.getCurrentUser(User.class).getObjectId());
                startActivity(it);
            }
        });

        //监听跳转到我关注的人的界面
        focusactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyFocus.class));
            }
        });


        myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到我的信息界面
                User user = BmobUser.getCurrentUser(User.class);
                Intent intent = new Intent(getActivity(), MyInfo.class);
                intent.putExtra("user_onlyid",user.getObjectId());
                startActivity(intent);
            }
        });

        mypush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyPush.class));
            }
        });

        mycomunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyComunity.class));
            }
        });

        mycollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyCollect.class));
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Setting.class));
            }
        });

    }

    private void getMyfansnum() {
//        final User us = BmobUser.getCurrentUser(User.class);
        BmobQuery<User> query = new BmobQuery<>();
        query.include("follower_id");
        query.getObject(BmobUser.getCurrentUser(User.class).getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                //fansnum.setText(Integer.toString(user.getFollower_id()));
            }
        });
    }

    private void getMyfocusnum() {
        User c_user = BmobUser.getCurrentUser(User.class);
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereRelatedTo("focuId",new BmobPointer(c_user));
        query.count(User.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e==null){
                    myfocusnum.setText(Integer.toString(integer));
                }else {
                    Toast.makeText(getActivity(), "获取关注人数失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setnumfont() { //Gorlock
        Typeface fans = Typeface.createFromAsset(getActivity().getAssets(),"Headache.ttf");
        Typeface focus = Typeface.createFromAsset(getActivity().getAssets(),"Headache.ttf");
        fansnum.setTypeface(fans);
        myfocusnum.setTypeface(focus);
    }

    private void getMyinfo() {
        //加载个人信息
        BmobUser bu = BmobUser.getCurrentUser(BmobUser.class);
        String Id = bu.getObjectId();
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(Id, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e==null){
                    username.setText(user.getUsername());
//                    nickname.setText(user.getNickname());
                    if (user.getGender().equals("man")){
                        mine_gender.setImageResource(R.drawable.man);
                    }else {
                        mine_gender.setImageResource(R.drawable.gril);
                    }
                }else {
                    Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        username = getActivity().findViewById(R.id.username);
//      nickname = getActivity().findViewById(R.id.nickname);
//      loginout = getActivity().findViewById(R.id.loginout);
        myinfo = getActivity().findViewById(R.id.myinfo);
        mypush = getActivity().findViewById(R.id.mypush);
        mycomunity = getActivity().findViewById(R.id.mycomunity);
        mycollect = getActivity().findViewById(R.id.mycollect);
        mine_gender = getActivity().findViewById(R.id.mine_gender);
        fansnum = getActivity().findViewById(R.id.fansnum);
        setting = getActivity().findViewById(R.id.setting);
        myfocusnum = getActivity().findViewById(R.id.myfocusnum);
        followactivity = getActivity().findViewById(R.id.followactivity);
        focusactivity = getActivity().findViewById(R.id.focusactivity);
    }


    //再次来到这个界面
    @Override
    public void onResume() {
        super.onResume();
        getMyfocusnum();
        getMyfansnum();
    }
}