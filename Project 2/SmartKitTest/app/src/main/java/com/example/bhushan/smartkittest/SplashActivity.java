package com.example.bhushan.smartkittest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();

       /* ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.getWindow().setGravity(Gravity.CENTER);
        progressDialog.show();*/

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), SmartKitActivity.class);
                startActivity(intent);
            }
        }, 4000);


    }
}
