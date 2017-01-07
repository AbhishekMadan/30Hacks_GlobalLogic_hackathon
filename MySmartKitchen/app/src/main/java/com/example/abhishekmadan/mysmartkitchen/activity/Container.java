package com.example.abhishekmadan.mysmartkitchen.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.abhishekmadan.mysmartkitchen.R;
import com.example.abhishekmadan.mysmartkitchen.fragments.BuyItemFragment;

/**
 * Created by abhishek.madan on 2/12/2016.
 */
public class Container extends AppCompatActivity
{
    FragmentManager manager;

    private Bundle bundle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        init();
        BuyItemFragment buyFragment = new BuyItemFragment();

        bundle = getIntent().getExtras();
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        buyFragment.setArguments(bundle);
        transaction.replace(R.id.Container, buyFragment);
        transaction.commit();
    }

    public void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

    }

}
