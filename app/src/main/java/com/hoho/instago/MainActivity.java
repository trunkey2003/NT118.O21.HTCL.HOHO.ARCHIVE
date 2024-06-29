package com.hoho.instago;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.Constants;

public class MainActivity extends AppCompatActivity {

    ImageView insta_logo;
    TextView HOHO;
    FirebaseAuth Fauth;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissionNeeded = {
            "android.permission.CAMERA",
            "android.permission.RECORD_AUDIO"};
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissionNeeded, 101);
            }
        }

        if (isOnline()) {

            load();
        } else {
            try {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Error")
                        .setMessage("Internet not available, Cross check your internet connectivity")
                        .setCancelable(false)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        load();
                    }
                }).show();
            } catch (Exception e) {
                Log.d(Constants.TAG, "Show Dialog: " + e.getMessage());
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            return false;
        }
        return true;
    }

    public void load(){
        insta_logo = (ImageView)findViewById(R.id.insta_logo);
        HOHO = (TextView)findViewById(R.id.HOHO);

        insta_logo.animate().alpha(0f).setDuration(0);
        HOHO.animate().alpha(0f).setDuration(0);

        insta_logo.animate().alpha(1f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                HOHO.animate().alpha(1f).setDuration(800);

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Fauth = FirebaseAuth.getInstance();
                if (Fauth.getCurrentUser() != null) {
                    if (Fauth.getCurrentUser().isEmailVerified()) {
                        Intent n = new Intent(MainActivity.this, Home.class);
                        startActivity(n);
                        finish();
                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Check whether you have verified your Email, Otherwise please verify");
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                Intent intent = new Intent(MainActivity.this, Login.class);
                                startActivity(intent);
                                finish();

                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                        Fauth.signOut();
                    }
                } else {
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                    finish();

                }

            }
        },3000);
    }
}