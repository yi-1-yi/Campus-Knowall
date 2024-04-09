package com.example.campus_knowall.activity;

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

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class Fragment_Comunity extends Fragment {

    private SwipeRefreshLayout swipe_com;
    private RecyclerView rv_com;
    private TextView error_com;

    private List<Comunity> data;

    private MycollectcomAdapter mycollectcomAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comunity,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        Refresh();

        swipe_com.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipe_com.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });

    }

    private void Refresh() {

        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Comunity> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("relation",user);
        bmobQuery.findObjects(new FindListener<Comunity>() {
            @Override
            public void done(List<Comunity> list, BmobException e) {
                swipe_com.setRefreshing(false);
                if (e==null){
                    if (list.size()>0){
                        swipe_com.setVisibility(View.VISIBLE);
                        data = list;
                        mycollectcomAdapter = new MycollectcomAdapter(getActivity(),data);
                        rv_com.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv_com.setAdapter(mycollectcomAdapter);
                    }else {
                        error_com.setVisibility(View.VISIBLE);
                        swipe_com.setVisibility(View.GONE);
                    }
                }else {
                    swipe_com.setRefreshing(false);
                    Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initView() {
        swipe_com = getActivity().findViewById(R.id.swipe_com);
        rv_com = getActivity().findViewById(R.id.rv_com);
        error_com = getActivity().findViewById(R.id.error_com);
    }
}
