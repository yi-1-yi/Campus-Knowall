package com.example.campus_knowall.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import cn.bmob.v3.listener.UpdateListener;

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
                Log.d("TestApp", "传后user(不显示): " + com.getUserid());
            }
            queryUserNameFromBmob(com1.getUserid(),new OnUserNameLoadedListener() {
                @Override
                public void onUserNameLoaded(String name) {
                    // 在这里处理查询到的用户名数据
                    recyclerViewHOlder.nickname.setText(name);
                    Log.d("TestApp", "传后name(显示): " + name);
                }
            });
            recyclerViewHOlder.com.setText(com1.getCom());
            Log.d("TestApp", "查找是否被点赞");
            Log.d("TestApp", "isLiked:"+com1.getIsLiked());
            if (com1.getIsLiked().equals("1")){
                //已被点赞
                recyclerViewHOlder.like.setImageResource(R.drawable.like_after);
            }
            recyclerViewHOlder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = recyclerViewHOlder.getAdapterPosition();
                    Log.d("TestApp", "comId：" +data.get(position).getObjectId()+"被点击了");
                    String comId=data.get(position).getObjectId();
                    //查找把数据库isliked设为1
                    BmobQuery<Com> query = new BmobQuery<>();
                    query.addWhereEqualTo("objectId",comId);
                    query.findObjects(new FindListener<Com>() {
                        @Override
                        public void done(List<Com> list, BmobException e) {
                            if (e == null && list.size() > 0) {
                                Com object = list.get(0);
                                object.setIsLiked("1"); // 将 isliked 属性值修改为 1
                                object.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            // 更新成功
                                            Log.d("TestApp", "isLiked被设置成1");
                                        } else {
                                            // 更新失败
                                        }
                                    }
                                });
                            } else {
                                // 查询失败或未找到匹配记录
                            }
                        }
                    });
                }
            });
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
        public ImageView like;
        public TextView loading;

        public RecyclerViewHOlder(View itemview, int view_type) {
            super(itemview);
            if (view_type == N_Type) {
                nickname = (TextView)itemview.findViewById(R.id.comname);
                com = (TextView)itemview.findViewById(R.id.comm);
                like=itemview.findViewById(R.id.like);
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
