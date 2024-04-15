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

import com.example.campus_knowall.Adapter.MyFollowerAdapter;
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

public class MyFollower extends AppCompatActivity {

    private SwipeMenuRecyclerView myfollowerrv;
    private TextView myfollower_error;
    private SwipeRefreshLayout myfollower_swipe;

    private ImageView back;

    List<User> data;
    MyFollowerAdapter myFollowerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfollower);

        Bmob.initialize(MyFollower.this,"ab34b8bf6de6d97060af91d796f2b9e1");

        initView();

        //初始刷新
        Refresh();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myfollower_swipe.setColorSchemeResources(R.color.orange,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        myfollower_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }

    private void Refresh() {

        //获取我的粉丝(关注我的人)  一对多
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("focusId",user);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                myfollower_swipe.setRefreshing(false);
                if (e==null){
                    data = list;
                    if (data.size()>0){
                        myfollower_swipe.setVisibility(View.VISIBLE);

                        myfollowerrv.addItemDecoration(new DefaultItemDecoration(Color.GRAY));
                        myfollowerrv.setSwipeMenuCreator(swipeMenuCreator);
                        myfollowerrv.setSwipeMenuItemClickListener(swipeMenuItemClickListener);



                        myFollowerAdapter = new MyFollowerAdapter(MyFollower.this,data);
                        myfollowerrv.setLayoutManager(new LinearLayoutManager(MyFollower.this));
                        myfollowerrv.setAdapter(myFollowerAdapter);

                    }else {
                        myfollower_error.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(MyFollower.this, "加载失败"+e, Toast.LENGTH_SHORT).show();
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
            SwipeMenuItem deleteItem = new SwipeMenuItem(MyFollower.this)
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

                User myf = new User();
                myf.setObjectId(data.get(adapterPosition).getObjectId());
                myf.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){
                            data.remove(adapterPosition);//删除item
                            myFollowerAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(MyFollower.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        }
    };


    private void initView() {
        myfollowerrv = findViewById(R.id.myfollow_rv);
        myfollower_error = findViewById(R.id.myfollow_error);
        myfollower_swipe = findViewById(R.id.myfollow_swipe);
        back = findViewById(R.id.back);
    }
}