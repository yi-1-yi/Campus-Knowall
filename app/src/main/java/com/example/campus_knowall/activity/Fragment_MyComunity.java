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

import com.example.campus_knowall.Adapter.MycollectcomAdapter;
import com.example.campus_knowall.Bean.Comunity;
import com.example.campus_knowall.Bean.User;
import com.example.campus_knowall.R;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class Fragment_MyComunity extends Fragment {


    private SwipeRefreshLayout swipe_mycom;
    private RecyclerView rv_mycom;

    private TextView error_mycom;

    private String user_onlyid;

    private List<Comunity> data;
    private MycollectcomAdapter mycollectcomAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mycom,container,false);
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


        swipe_mycom.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipe_mycom.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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

    private void RefreshMy() {
        //加载我的论坛
        //获取我发布的帖子  一对多
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Comunity> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("user",user);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Comunity>() {
            @Override
            public void done(List<Comunity> list, BmobException e) {
                swipe_mycom.setRefreshing(false);
                if (e==null){
                    data = list;
                    if (data.size()>0){
                        swipe_mycom.setVisibility(View.VISIBLE);

                        mycollectcomAdapter = new MycollectcomAdapter(getActivity(),data);
                        rv_mycom.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv_mycom.setAdapter(mycollectcomAdapter);

                    }else {
                        error_mycom.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(getActivity(), "加载失败"+e, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void RefreshOth() {
        //加载别人的论坛

        BmobQuery<Comunity> query = new BmobQuery<>();
        query.addWhereEqualTo("userOnlyId",user_onlyid);
        query.include("user");
        query.order("-createdAt");
        query.findObjects(new FindListener<Comunity>() {
            @Override
            public void done(List<Comunity> list, BmobException e) {
                swipe_mycom.setRefreshing(false);
                if (e==null){
                    data = list;
                    if (data.size()>0){
                        swipe_mycom.setVisibility(View.VISIBLE);
                        mycollectcomAdapter = new MycollectcomAdapter(getActivity(),data);
                        rv_mycom.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv_mycom.setAdapter(mycollectcomAdapter);

                    }else {
                        BmobQuery<User> bmobQuery = new BmobQuery<>();
                        bmobQuery.getObject(user_onlyid, new QueryListener<User>() {
                            @Override
                            public void done(User user, BmobException e) {
                                if (user.getGender().equals("man")){
                                    error_mycom.setText("他还没有话题");
                                    error_mycom.setVisibility(View.VISIBLE);
                                }else {
                                    error_mycom.setText("她还没有话题");
                                    error_mycom.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                }else {
                    Toast.makeText(getActivity(), "加载失败"+e, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void initView() {
        swipe_mycom = getActivity().findViewById(R.id.swipe_mycom);
        rv_mycom = getActivity().findViewById(R.id.rv_mycom);
        error_mycom = getActivity().findViewById(R.id.error_mycom);
    }
}
