package com.example.bhushan.smartkittest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddressDetailsActivity extends AppCompatActivity {
    EditText mEditTextName;
    EditText mEditTextStreet;
    EditText mEditTextCity;
    EditText mEditTextPinCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);

        mEditTextName = (EditText) findViewById(R.id.et_name);
        mEditTextStreet = (EditText) findViewById(R.id.et_street);
        mEditTextCity = (EditText) findViewById(R.id.et_City);
        mEditTextPinCode = (EditText) findViewById(R.id.et_pincode);
        Button button = (Button) findViewById(R.id.btn_send_address);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String street, city, pincode, name;
                name = mEditTextName.getText().toString();
                street = mEditTextStreet.getText().toString();
                city = mEditTextCity.getText().toString();
                pincode = mEditTextPinCode.getText().toString();

                SharedPreferences preferences = getApplicationContext().getSharedPreferences("address", MODE_PRIVATE);
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


}
