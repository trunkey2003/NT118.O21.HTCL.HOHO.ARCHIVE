package com.hoho.instago;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.nostra13.universalimageloader.core.ImageLoader;

import com.hoho.instago.Like.LikeFragment;
import com.hoho.instago.Post.PostActivity;
import com.hoho.instago.Profile.ProfileFragment;
import com.hoho.instago.Search.SearchFragment;
import com.hoho.instago.Utils.UniversalImageLoader;
import com.hoho.instago.home.HomeFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    String myUserid;
    String myUserName;
    FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navigationView = findViewById(R.id.insta_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("PAGE");
        if (name != null){
            loadfragment(new HomeFragment());
        }else{
            loadfragment(new HomeFragment());
        }
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        myUserid = fuser.getUid();
        myUserName = fuser.getDisplayName();
        if (myUserName.isEmpty()) {
            myUserName = myUserid;
        }
        Toast.makeText(this, "Welcome to InstaGo " + myUserName, Toast.LENGTH_SHORT).show();
        if (myUserid.isEmpty() == false){
            startService(myUserid, myUserName);
            Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean loadfragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }

        return false;
    }

    void startService(String userID, String userName) {
        Application application = getApplication(); // Android's application context
        long appID = 1444059904;   // yourAppID
        String appSign = "e68d7a63da606db7bddeaceaf3c054c6b798244ca6a7be2c0d4c4331a0659e60";  // yourAppSign

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();

        ZegoUIKitPrebuiltCallService.init(getApplication(), appID, appSign, userID, userName, callInvitationConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.Home:
                fragment = new HomeFragment();
                break;

            case R.id.search:
                fragment = new SearchFragment();
                break;

            case R.id.post:
                fragment = null;
                startActivity(new Intent(Home.this, PostActivity.class));
                break;
            case R.id.likes:
                fragment = new LikeFragment();
                break;
            case R.id.profile:
                fragment = new ProfileFragment();
                break;
        }
        return loadfragment(fragment);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


}