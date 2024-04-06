package com.example.campus_knowall.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campus_knowall.Bean.Post;
import com.example.campus_knowall.R;
import com.example.campus_knowall.activity.Login;
import com.example.campus_knowall.activity.Recive;

import java.util.List;
import java.util.logging.LogRecord;

import cn.bmob.v3.BmobUser;

public class ForumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Post> data;

    private final int N_Type = 0;
    private final int F_Type = 1;

    private int MAX_num = 15;
    private Boolean isfootview = true;

    public ForumAdapter(Context context, List<Post> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ord_item, viewGroup, false);
        View footview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item, viewGroup, false);
        if (i == F_Type) {
            return new RecyclerViewHOlder(footview, F_Type);
        } else {
            return new RecyclerViewHOlder(view, N_Type);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==MAX_num-1){
            return F_Type;
        }else{
            return F_Type;
        }
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (isfootview && (getItemViewType(i)) == F_Type) {
            final RecyclerViewHOlder recyclerViewHOlder = (RecyclerViewHOlder) viewHolder;
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
            final RecyclerViewHOlder recyclerViewHOlder = (RecyclerViewHOlder) viewHolder;
            Post post = data.get(i);
            recyclerViewHOlder.nickname.setText(post.getNickname());
            recyclerViewHOlder.content.setText(post.getContent());
            recyclerViewHOlder.time.setText(post.getCreatedAt());


            recyclerViewHOlder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = recyclerViewHOlder.getAdapterPosition();
                    if (BmobUser.getCurrentUser(BmobUser.class) != null) {
                        Intent in = new Intent(context, Recive.class);
                        in.putExtra("id", data.get(position).getObjectId());
                        context.startActivity(in);
                    } else {
                        Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, Login.class));
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(data.size()<MAX_num){
            return data.size();
        }else {
            return MAX_num;
        }
    }

    private class RecyclerViewHOlder extends RecyclerView.ViewHolder {

        public TextView nickname, content, time;
        public TextView loading;

        public RecyclerViewHOlder(View itemview, int view_type) {
            super(itemview);
            if (view_type == N_Type) {
                nickname = itemview.findViewById(R.id.nickname);
                content = itemview.findViewById(R.id.content);
                time = itemview.findViewById(R.id.time);
            } else if (view_type == F_Type) {
                loading = itemview.findViewById(R.id.footText);
            }
        }
    }
}
