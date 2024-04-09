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
    private TeamFragment teamFragment;
    private MessageFragment messageFragment;
    private MyFragment myFragment;


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
                }else if(item.getItemId()== R.id.navigation_team){
                    selectedFragment(1);
                }else if(item.getItemId()== R.id.navigation_message){
                    selectedFragment(2);
                }else {
                    selectedFragment(3);
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
        }else if(position==1){
            if(teamFragment==null){
                teamFragment=new TeamFragment();
                fragmentTransaction.add(R.id.content,teamFragment);
            }else{
                fragmentTransaction.show(teamFragment);
            }
        }else if(position==2){
            if(messageFragment==null){
                messageFragment=new MessageFragment();
                fragmentTransaction.add(R.id.content,messageFragment);
            }else{
                fragmentTransaction.show(messageFragment);
            }
        }else{
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

        if(teamFragment!=null){
            fragmentTransaction.hide(teamFragment);
        }

        if(messageFragment!=null){
            fragmentTransaction.hide(messageFragment);
        }

        if(myFragment!=null){
            fragmentTransaction.hide(myFragment);
        }

    }
}