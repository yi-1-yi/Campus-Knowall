package com.example.campus_knowall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.campus_knowall.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

public class MyCollect extends AppCompatActivity {

    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;

    private ImageView back;


    private FragmentStatePagerItemAdapter fragadapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollect);

        initView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager.setOffscreenPageLimit(3);
        initTab();

    }

    private void initTab() {

        String[] tabs = new String[]{"帖子","论坛"};

        FragmentPagerItems pages = new FragmentPagerItems(MyCollect.this);
        for (int i = 0 ; i<tabs.length ; i++){
            Bundle args = new Bundle();
            args.putString("name",tabs[i]);
        }

        pages.add(FragmentPagerItem.of(tabs[0],Fragment_Push.class));
        pages.add(FragmentPagerItem.of(tabs[1],Fragment_Comunity.class));

        viewPager.removeAllViews();
        fragadapter = new FragmentStatePagerItemAdapter(getSupportFragmentManager(),pages);
        viewPager.setAdapter(fragadapter);
        smartTabLayout.setViewPager(viewPager);

    }

    private void initView() {
        smartTabLayout = findViewById(R.id.mycollecttab);
        viewPager = findViewById(R.id.mycollectvp);
        back = findViewById(R.id.back);
    }
}
