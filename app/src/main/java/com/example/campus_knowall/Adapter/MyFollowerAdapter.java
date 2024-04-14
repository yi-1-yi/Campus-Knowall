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

import com.example.campus_knowall.Bean.User;
import com.example.campus_knowall.R;
import com.example.campus_knowall.activity.Login;
import com.example.campus_knowall.activity.MyFollower;

import java.util.List;

import cn.bmob.v3.BmobUser;

public class MyFollowerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<User> data;

    private final int N_TYPE = 0;
    private final int F_TYPE = 1;

    private int Max_num = 15;  //预加载的数据  一共15条

    private Boolean isfootview = true;  //是否有footview

    public MyFollowerAdapter(Context context, List<User> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myfollower_item,viewGroup,false);
        View footview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item,viewGroup,false);
        if (i == F_TYPE){
            return new MyFollowerAdapter.RecyclerViewHolder(footview,F_TYPE);
        }else {
            return new MyFollowerAdapter.RecyclerViewHolder(view,N_TYPE);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (isfootview&&(getItemViewType(i)) == F_TYPE){
            //底部加载提示
            final MyFollowerAdapter.RecyclerViewHolder recyclerViewHolder = (MyFollowerAdapter.RecyclerViewHolder) viewHolder;
            recyclerViewHolder.Loading.setText("加载中...");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Max_num += 8;
                    notifyDataSetChanged();
                }
            },2000);
        }else {
            //这是ord_item的内容
            final MyFollowerAdapter.RecyclerViewHolder recyclerViewHolder = (MyFollowerAdapter.RecyclerViewHolder) viewHolder;
            final User myf = data.get(i);
            recyclerViewHolder.avatar.setText(myf.getAvatar());
            recyclerViewHolder.nickname.setText(myf.getNickname());
            recyclerViewHolder.gender.setText(myf.getGender());

            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = recyclerViewHolder.getAdapterPosition();
                    if (BmobUser.getCurrentUser(BmobUser.class) != null){

                        //需要改动
                        Intent in = new Intent(context, MyFollower.class);
                        in.putExtra("avatar",myf.getAvatar());
                        in.putExtra("nickname",myf.getNickname());
                        in.putExtra("gender",myf.getGender());
                        in.putExtra("id",data.get(position).getObjectId());
                        context.startActivity(in);
                    }else {
                        Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, Login.class));
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == Max_num - 1){
            return F_TYPE;  //底部type
        }else {
            return N_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        if (data.size() < Max_num){
            return data.size();
        }
        return Max_num;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView avatar,nickname,gender; //ord_item的TextView
        public TextView Loading;


        public RecyclerViewHolder(View itemview, int view_type) {
            super(itemview);
            if (view_type == N_TYPE){
                avatar=itemview.findViewById(R.id.myfollower_avatar);
                nickname = itemview.findViewById(R.id.myfollower_nickname);
                gender = itemview.findViewById(R.id.myfollower_gender);
            }else if(view_type == F_TYPE){
                Loading = itemview.findViewById(R.id.footText);
            }
        }
    }
}
