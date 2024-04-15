package com.example.campus_knowall.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campus_knowall.Bean.Com;
import com.example.campus_knowall.Bean.Post;
import com.example.campus_knowall.Bean.User;
import com.example.campus_knowall.R;
import com.example.campus_knowall.activity.Login;
import com.example.campus_knowall.activity.PushCom;
import com.example.campus_knowall.activity.Recieve;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class ComAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int N_Type = 0;
    private final int F_Type = 1;

    private int MAX_num = 15;
    private Boolean isfootview = true;

    private Context context;
    private List<Com> data;


    public ComAdapter(Context context, List<Com> data) {
        this.context = context;
        this.data = data;
        Log.d("TestApp","建立comAdapter了");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.com_item, viewGroup, false);
        View footview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item, viewGroup, false);
        if (i == F_Type) {
            return new ComAdapter.RecyclerViewHOlder(footview, F_Type);
        } else {
            return new ComAdapter.RecyclerViewHOlder(view, N_Type);
        }
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (isfootview && (getItemViewType(i)) == F_Type) {
            final ComAdapter.RecyclerViewHOlder recyclerViewHOlder = (ComAdapter.RecyclerViewHOlder) viewHolder;
            recyclerViewHOlder.loading.setText("加载中...");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MAX_num += 8;
                    notifyDataSetChanged();
                }
            }, 2000);
        } else {
            //COM_ITEM内容
            final ComAdapter.RecyclerViewHOlder recyclerViewHOlder = (ComAdapter.RecyclerViewHOlder) viewHolder;
            Com com1 = data.get(i);
            for (Com com : data) {
                Log.d("TestApp", "传后objectId(不显示): " + com.getObjectId());
                Log.d("TestApp", "传后com(显示): " + com.getCom());
                Log.d("TestApp", "传后postid(不显示): " + com.getPostid());
                Log.d("TestApp", "传后name(显示): " + com.getUserid());
            }

            queryUserNameFromBmob(com1.getUserid(),new OnUserNameLoadedListener() {
                @Override
                public void onUserNameLoaded(String name) {
                    // 在这里处理查询到的用户名数据
                    recyclerViewHOlder.nickname.setText(name);
                }
            });

//            recyclerViewHOlder.nickname.setText(com1.getUserid().getNickname());
//            recyclerViewHOlder.postid.setText(com1.getPostid());
            recyclerViewHOlder.com.setText(com1.getCom());

//            recyclerViewHOlder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = recyclerViewHOlder.getAdapterPosition();
//
//                    if (BmobUser.getCurrentUser(BmobUser.class) != null) {
//                        Intent in = new Intent(context, PushCom.class);
//                        in.putExtra("nickname", com1.getUserid().getNickname());
//                        in.putExtra("postid", com1.getPostid());
//                        in.putExtra("com", com1.getCom());
////                        in.putExtra("user_onlyid", post.getUserOnlyId());
////                        in.putExtra("id", data.get(position).getObjectId());
//                        context.startActivity(in);
//                    } else {
//                        Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
//                        context.startActivity(new Intent(context, Login.class));
//                    }
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        Log.d("TestApp", "size:"+String.valueOf(data.size()));
        if(data.size()<MAX_num){
            return data.size();
        }else {
            return MAX_num;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==MAX_num-1){
            return F_Type;
        }else{
            return N_Type;
        }
    }

    private class RecyclerViewHOlder extends RecyclerView.ViewHolder {

        public TextView nickname, com;//com_item的textview
        public TextView loading;

        public RecyclerViewHOlder(View itemview, int view_type) {
            super(itemview);
            if (view_type == N_Type) {
                nickname = (TextView)itemview.findViewById(R.id.comname);
                com = (TextView)itemview.findViewById(R.id.comm);
            } else if (view_type == F_Type) {
                loading = (TextView)itemview.findViewById(R.id.footText);
            }
        }
    }

    public void queryUserNameFromBmob(User user,final OnUserNameLoadedListener listener) {
        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(user.getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null && user != null) {
                    String name = user.getNickname();
                    listener.onUserNameLoaded(name);
                } else {
                    // 处理查询失败的情况
                    Log.e("TestApp", "查询用户名失败：" + e.getMessage());
                }
            }
        });
    }

    public interface OnUserNameLoadedListener {
        void onUserNameLoaded(String name);
    }
}
