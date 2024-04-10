package com.example.campus_knowall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.campus_knowall.Adapter.MycollectpushAdapter;
import com.example.campus_knowall.Bean.Post;
import com.example.campus_knowall.Bean.User;
import com.example.campus_knowall.R;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Fragment_MyPush extends Fragment {

    private SwipeRefreshLayout swipe_mypush;
    private RecyclerView rv_mypush;

    private TextView error_mypush;

    private String user_onlyid;

    private List<Post> data;

    private MycollectpushAdapter mycollectpushAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mypush,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        Intent intent = requireActivity().getIntent();
        user_onlyid = intent.getStringExtra("user_onlyid");
        User user = BmobUser.getCurrentUser(User.class);
        if (user_onlyid.equals(user.getObjectId())){
            RefreshMy();
        }else {
            RefreshOth();
        }


        swipe_mypush.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipe_mypush.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = requireActivity().getIntent();
                user_onlyid = intent.getStringExtra("user_onlyid");
                User user = BmobUser.getCurrentUser(User.class);
                if (user_onlyid.equals(user.getObjectId())){
                    RefreshMy();
                }else {
                    RefreshOth();
                }
            }
        });
    }

    private void RefreshOth() {
//
//        BmobQuery<Post> query = new BmobQuery<>();
//        query.addWhereEqualTo("user_onlyid",user_onlyid);
//        query.include("author");
//        query.order("-createdAt");
//        query.findObjects(new FindListener<Post>() {
//            @Override
//            public void done(List<Post> list, BmobException e) {
//                if (e == null){
//                    String size = String.valueOf(list.size());
//                    my_pushnum.setText(size);
//                }else {
//                    Toast.makeText(MyInfo.this, "查询帖数失败", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("userOnlyId",user_onlyid);
        query.include("author");
        query.order("-createdAt");
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                swipe_mypush.setRefreshing(false);
                if (e==null){
                    data = list;
                    if (data.size()>0){
                        swipe_mypush.setVisibility(View.VISIBLE);
                        mycollectpushAdapter = new MycollectpushAdapter(data,getActivity());
                        rv_mypush.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv_mypush.setAdapter(mycollectpushAdapter);

                    }else {
                        error_mypush.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(getActivity(), "加载失败"+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void RefreshMy() {

        //获取我发布的帖子  一对多
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Post> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("author",user);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                swipe_mypush.setRefreshing(false);
                if (e==null){
                    data = list;
                    if (data.size()>0){
                        swipe_mypush.setVisibility(View.VISIBLE);

                        mycollectpushAdapter = new MycollectpushAdapter(data,getActivity());
                        rv_mypush.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv_mypush.setAdapter(mycollectpushAdapter);

                    }else {
                        error_mypush.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(getActivity(), "加载失败"+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        error_mypush = getActivity().findViewById(R.id.error_mypush);
        rv_mypush = getActivity().findViewById(R.id.rv_mypush);
        swipe_mypush = getActivity().findViewById(R.id.swipe_mypush);
    }
}
