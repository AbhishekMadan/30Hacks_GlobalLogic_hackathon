package com.example.bhushan.smartkittest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends Activity {


    String id;
    String name;
    EditText et_id;
    EditText et_name;
    InputStream is = null;
    String result = null;
    String line = null;
    int code;
    String date;
    String url = "http://vandroid.web44.net/smart_insert.php";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();



        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        date = c.getTime().toString();
        et_id = (EditText) findViewById(R.id.id);

        et_name = (EditText) findViewById(R.id.name);

        Button btn = (Button) findViewById(R.id.btn);
        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsynTaskUpdate().execute();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsynTaskFetch().execute();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                id = et_id.getText().toString();
                name = et_name.getText().toString();

                new AsynTask().execute();


            }
        });


    }

    class AsynTaskUpdate extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            String url = "http://vandroid.web44.net/update.php";

            nameValuePairs.add(new BasicNameValuePair("item_id", "2"));
            nameValuePairs.add(new BasicNameValuePair("item_type", "solid"));
            nameValuePairs.add(new BasicNameValuePair("rdate", date));

            nameValuePairs.add(new BasicNameValuePair("con_wei", "15.00"));

            nameValuePairs.add(new BasicNameValuePair("con_hei", "55.00"));


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
    }

    class AsynTaskFetch extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {

            try {
                String url = "http://vandroid.web44.net/select.php";

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);

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
                JSONArray json_data = new JSONArray(result);
                for (int i = 0; i < json_data.length(); i++) {
                    Log.e("pass 1", json_data.length() + "");
                    JSONObject object = json_data.getJSONObject(i);

                    Log.e("pass 1", "Inserted unSuccessfully");

                }
            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }


            return null;
        }
    }

    class AsynTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("item_id", "2"));
            nameValuePairs.add(new BasicNameValuePair("item_name", "salt"));

            nameValuePairs.add(new BasicNameValuePair("item_type", "tata"));
            nameValuePairs.add(new BasicNameValuePair("rdate", date));
            nameValuePairs.add(new BasicNameValuePair("con_wei", "637.00"));
            nameValuePairs.add(new BasicNameValuePair("con_hei", "637.00"));
            nameValuePairs.add(new BasicNameValuePair("con_dai", "637.00"));


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://vandroid.web44.net/smart_insert.php");
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
    }


}
