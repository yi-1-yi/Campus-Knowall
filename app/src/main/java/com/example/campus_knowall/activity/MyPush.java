package com.example.campus_knowall.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.campus_knowall.Adapter.MyPushAdapter;
import com.example.campus_knowall.Bean.Post;
import com.example.campus_knowall.Bean.User;
import com.example.campus_knowall.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MyPush extends AppCompatActivity {

    private SwipeMenuRecyclerView mypushrv;
    private TextView mypush_error;
    private SwipeRefreshLayout mypush_swipe;

    private ImageView back;

     List<Post> data;
     MyPushAdapter myPushAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypush);

        Bmob.initialize(MyPush.this,"ab34b8bf6de6d97060af91d796f2b9e1");

        initView();

        //初始刷新
        Refresh();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mypush_swipe.setColorSchemeResources(R.color.orange,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        mypush_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }

    private void Refresh() {

        //获取我发布的帖子  一对多
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Post> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("author",user);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                mypush_swipe.setRefreshing(false);
                if (e==null){
                    data = list;
                    if (data.size()>0){
                        mypush_swipe.setVisibility(View.VISIBLE);

                        mypushrv.addItemDecoration(new DefaultItemDecoration(Color.GRAY));
                        mypushrv.setSwipeMenuCreator(swipeMenuCreator);
                        mypushrv.setSwipeMenuItemClickListener(swipeMenuItemClickListener);



                        myPushAdapter = new MyPushAdapter(MyPush.this,data);
                        mypushrv.setLayoutManager(new LinearLayoutManager(MyPush.this));
                        mypushrv.setAdapter(myPushAdapter);

                    }else {
                        mypush_error.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(MyPush.this, "加载失败"+e, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // 设置菜单监听器。
    SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        // 创建菜单：
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(MyPush.this)
                    .setTextColor(Color.WHITE)
                    .setBackgroundColor(Color.RED)
                    .setText("删除")
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);
        }
    };

    // 菜单点击监听。
    SwipeMenuItemClickListener swipeMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection();//左边还是右边菜单
            final int adapterPosition = menuBridge.getAdapterPosition();//    recyclerView的Item的position。
            int position = menuBridge.getPosition();// 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {

                Post post = new Post();
                post.setObjectId(data.get(adapterPosition).getObjectId());
                post.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){
                            data.remove(adapterPosition);//删除item
                            myPushAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(MyPush.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        }
    };


    private void initView() {
        mypushrv = findViewById(R.id.mypushrv);
        mypush_error = findViewById(R.id.mypush_error);
        mypush_swipe = findViewById(R.id.mupush_swipe);
        back = findViewById(R.id.back);
    }
}
