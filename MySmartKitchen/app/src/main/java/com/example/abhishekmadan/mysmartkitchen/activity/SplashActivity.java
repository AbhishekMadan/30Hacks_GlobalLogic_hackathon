package com.example.abhishekmadan.mysmartkitchen.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;


import com.example.abhishekmadan.mysmartkitchen.R;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
                finish();
            }
        }, 4000);

    }
}
