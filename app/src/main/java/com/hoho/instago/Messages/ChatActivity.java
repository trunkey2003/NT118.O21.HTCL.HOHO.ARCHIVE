package com.hoho.instago.Messages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import com.hoho.instago.Messages.Adapter.PageAdapter;
import com.hoho.instago.R;

public class ChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = findViewById(R.id.ChatActivity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("InstaGo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.ChatActivity_mainTabPager);
        pageAdapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);

        tabLayout = findViewById(R.id.ChatActivity_maintabs);
        tabLayout.setupWithViewPager(viewPager);

    }
}