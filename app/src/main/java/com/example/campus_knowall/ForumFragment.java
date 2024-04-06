package com.example.campus_knowall;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/*import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;*/
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.campus_knowall.Adapter.ForumAdapter;
import com.example.campus_knowall.Bean.Post;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ForumFragment extends Fragment {
    private RecyclerView rv;
    private SwipeRefreshLayout swipeRefreshLayout;
    List<Post> data;
    private ForumAdapter forumAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable  Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_forum, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        //逻辑处理
        initView();
        Bmob.initialize(getActivity(), "417e88a4dde438927b0c164bb450d7e3");

        //初始刷新
        Refresh();

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_bright);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }

    private void Refresh() {
        BmobQuery<Post> Po=new BmobQuery<Post>();
        Po.order("-createdAt");
        Po.setLimit(1000);
        Po.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                swipeRefreshLayout.setRefreshing(false);
                if(e==null){
                    data=list;
                    forumAdapter=new ForumAdapter(getActivity(),data);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setAdapter(forumAdapter);
                }else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(),"获取数据失败",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initView() {
        rv=getActivity().findViewById(R.id.recyclerview);
        swipeRefreshLayout=getActivity().findViewById(R.id.swipe);

    }

}