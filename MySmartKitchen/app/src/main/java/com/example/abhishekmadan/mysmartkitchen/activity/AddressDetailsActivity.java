package com.example.abhishekmadan.mysmartkitchen.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abhishekmadan.mysmartkitchen.R;

public class AddressDetailsActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText mEditTextName;
    EditText mEditTextStreet;
    EditText mEditTextCity;
    EditText mEditTextPinCode;
    Button mButton;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);

        init();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String street, city, pincode, name;
                name = mEditTextName.getText().toString();
                street = mEditTextStreet.getText().toString();
                city = mEditTextCity.getText().toString();
                pincode = mEditTextPinCode.getText().toString();

                preferences = getApplicationContext().getSharedPreferences("address", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("name", name);
                editor.putString("street", street);
                editor.putString("city", city);
                editor.putString("pincode", pincode);
                editor.commit();
                Toast.makeText(getApplication(), "Data Saved", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        });
    }

    public void init(){
        mEditTextName = (EditText) findViewById(R.id.et_name);
        mEditTextStreet = (EditText) findViewById(R.id.et_street);
        mEditTextCity = (EditText) findViewById(R.id.et_City);
        mEditTextPinCode = (EditText) findViewById(R.id.et_pincode);
        mButton = (Button) findViewById(R.id.btn_send_address);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        preferences = getApplicationContext().getSharedPreferences("address", MODE_PRIVATE);
        mEditTextName.setText(preferences.getString("name", ""));
        mEditTextStreet.setText(preferences.getString("street", ""));
        mEditTextCity.setText(preferences.getString("city", ""));
        mEditTextPinCode.setText(preferences.getString("pincode", ""));
    }


}
