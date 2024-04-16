package com.example.campus_knowall;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ForumFragment forumFragment;
    private MyFragment myFragment;
    private ComFragment comFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        bottomNavigationView=findViewById(R.id.bottom);
        //默认首页
        selectedFragment(0);
        //bottomNavigationView点击切换事件
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()== R.id.navigation_forum){
                    selectedFragment(0);
                }
                else {
                    selectedFragment(2);
                }
                return  true;
            }
        });

    }

    private void selectedFragment(int position){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        if(position==0){
            if(forumFragment==null){
                forumFragment=new ForumFragment();
                fragmentTransaction.add(R.id.content,forumFragment);
            }else{
                fragmentTransaction.show(forumFragment);
            }
        }
//        else if(position==7){
//            if(comFragment==null){
//                comFragment=new ComFragment();
//                Bundle args = new Bundle();
//                args.putString("id", "value"); // 替换"key"和"value"为你要传递的参数名和值
//                comFragment.setArguments(args);
//                fragmentTransaction.add(R.id.content,comFragment);
//            }else{
//                fragmentTransaction.show(comFragment);
//            }
//        }
        else{
            if(myFragment==null){
                myFragment=new MyFragment();
                fragmentTransaction.add(R.id.content,myFragment);
            }else{
                fragmentTransaction.show(myFragment);
            }
        }
        //提交
        fragmentTransaction.commit();
    }

    private  void  hideFragment(FragmentTransaction fragmentTransaction){
        if(forumFragment!=null){
            fragmentTransaction.hide(forumFragment);
        }

        if(myFragment!=null){
            fragmentTransaction.hide(myFragment);
        }

//        if(comFragment!=null){
//            fragmentTransaction.hide(comFragment);
//        }
    }
}