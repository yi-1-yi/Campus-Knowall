package com.example.campus_knowall;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.campus_knowall.Adapter.ComAdapter;
import com.example.campus_knowall.Adapter.ForumAdapter;
import com.example.campus_knowall.Bean.Com;
import com.example.campus_knowall.Bean.Post;
import com.example.campus_knowall.activity.PushContent;
import com.example.campus_knowall.activity.Search;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ComFragment extends Fragment {
    private RecyclerView rv;
    private SwipeRefreshLayout swipeRefreshLayout;
    List<Com> data;
    private ComAdapter comAdapter;
    private String id;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        if(args != null){
            id = args.getString("id"); // 获取参数值，替换"key"为你设置的参数名
        }
        return inflater.inflate(R.layout.fragment_recieve, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //逻辑处理
        initView();
        Bmob.initialize(getActivity(), "417e88a4dde438927b0c164bb450d7e3");

        //初始刷新
        Refresh();

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }


    private void Refresh() {
        BmobQuery<Com> Po=new BmobQuery<Com>();
        Po.order("-createdAt");
        Po.setLimit(1000);
        Po.findObjects(new FindListener<Com>() {
            @Override
            public void done(List<Com> list, BmobException e) {
                swipeRefreshLayout.setRefreshing(false);
                if(e==null){
                    data=list;
                    comAdapter=new ComAdapter(getActivity(),data);
//                    comAdapter.setPostId(id);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setAdapter(comAdapter);
                }else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(),"获取数据失败"+e,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        rv=getActivity().findViewById(R.id.recyclerview1);
        swipeRefreshLayout=getActivity().findViewById(R.id.swipe1);
    }
}