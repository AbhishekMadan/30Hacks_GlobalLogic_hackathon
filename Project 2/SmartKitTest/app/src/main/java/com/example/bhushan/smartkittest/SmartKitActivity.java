package com.example.bhushan.smartkittest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by bhushan.raut on 2/12/2016.
 */
public class SmartKitActivity extends AppCompatActivity {

    Spinner spinner;
    String mFoodItemIdList[] = {"1000", "1001", "1002", "1003", "1004", "1005", "1006", "1008"};
    String mFoodItemTypeList[] = {"solid", "solid", "solid", "solid", "solid", "liquid", "liquid", "solid"};
    String mItemName;
    String mItemId;
    String mtype;
    Button button;
    EditText mEditTextWeight;
    EditText mEditTextHeight;
    InputStream is = null;
    String result = null;
    String line = null;
    int code;
    String date;
    String weigh = "-1";
    String height = "-1";
    double mMaxWeight = 2000.0;
    double mMaxHeight = 15.0;
    Calendar c;


    /* @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.menu_main, menu);
         return true;
     }
 */
    @Override
   /* public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, AddressDetailsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        // date = c.getTime().toString();

        button = (Button) findViewById(R.id.btn_send);
        spinner = (Spinner) findViewById(R.id.spinner);


        mEditTextWeight = (EditText)

                findViewById(R.id.et_weigt);

        mEditTextHeight = (EditText)

                findViewById(R.id.et_height);


        button.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {
                                          boolean flag = true;

                                          if (mtype.equals("solid")) {
                                              weigh = mEditTextWeight.getText().toString();
                                              if (weigh.equals(""))
                                                  weigh = "20001";
                                              if (weigh.equals(".") || Double.parseDouble(weigh) > mMaxWeight || Double.parseDouble(weigh) == 0)
                                                  flag = false;
                                              mEditTextWeight.setText("");
                                          } else {
                                              height = mEditTextHeight.getText().toString();
                                              if (height.equals(""))
                                                  height = "16";
                                              if (height.equals(".") || Double.parseDouble(height) > mMaxHeight || Double.parseDouble(height) == 0)
                                                  flag = false;
                                              mEditTextHeight.setText("");
                                          }

                                          if ((weigh.equals("")) || (height.equals(""))) {
                                              Toast.makeText(getApplication(), "enter data", Toast.LENGTH_SHORT).show();
                                          } else if (!flag) {
                                              Toast.makeText(getApplication(), "entered value invalid", Toast.LENGTH_SHORT).show();
                                          } else {
                                              new AsynTaskUpdate().execute();
                                          }

                                      }

                                  }

        );
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {

                mEditTextHeight.setVisibility(View.VISIBLE);
                mEditTextWeight.setVisibility(View.VISIBLE);
                mItemName = parent.getItemAtPosition(position).toString();
                mItemId = mFoodItemIdList[position];
                mtype = mFoodItemTypeList[position];
                if (mtype.equals("solid")) {
                    mEditTextHeight.setVisibility(View.GONE);

                    //  mEditTextHeight.setFocusable(false);
                } else {
                    mEditTextWeight.setVisibility(View.GONE);
                    // mEditTextWeight.setFocusable(false);
                }

                Toast.makeText(parent.getContext(), "Selected: " + mItemName, Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }

        });


    }

    class AsynTaskUpdate extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            String url = "http://vandroid.web44.net/update.php";

            nameValuePairs.add(new BasicNameValuePair("item_id", mItemId));
            nameValuePairs.add(new BasicNameValuePair("item_type", mtype));
            c = Calendar.getInstance();
            nameValuePairs.add(new BasicNameValuePair("rdate", c.getTime().toString()));

            nameValuePairs.add(new BasicNameValuePair("con_wei", weigh));

            nameValuePairs.add(new BasicNameValuePair("con_hei", height));


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                Log.e("pass 1", "connection success ");
            } catch (Exception e) {
                Log.e("Fail 1", e.toString());
                Log.e("inside", "connection failed ");
                //Toast.makeText(getApplicationContext(), "Invalid IP Address",
                //  Toast.LENGTH_LONG).show();
            }

            try {
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                Log.e("pass 2", "connection success ");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Fail 2", e.toString());
            }

            try {
                JSONObject json_data = new JSONObject(result);
                code = (json_data.getInt("code"));

                if (code == 1) {
                    Log.e("pass 1", "Inserted Successfully");

                    //  Toast.makeText(getBaseContext(), "Inserted Successfully",
                    // Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("pass 1", "Inserted unSuccessfully");
                    // Toast.makeText(getBaseContext(), "Sorry, Try Again",
                    //       Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplication(), "Updated: " + mItemName, Toast.LENGTH_LONG).show();
        }
    }
}




