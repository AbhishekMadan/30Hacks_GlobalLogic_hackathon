package com.example.abhishekmadan.mysmartkitchen.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.abhishekmadan.mysmartkitchen.R;
import com.example.abhishekmadan.mysmartkitchen.activity.MainActivity;
import com.example.abhishekmadan.mysmartkitchen.database.Database;
import com.example.abhishekmadan.mysmartkitchen.database.DatabaseProperties;
import com.example.abhishekmadan.mysmartkitchen.modal.ClassConstant;
import com.example.abhishekmadan.mysmartkitchen.modal.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by abhishek.madan on 2/12/2016.
 */
public class GetDataService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        (new GetServerListTask()).execute("http://vandroid.web44.net/select.php");

       return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public class GetServerListTask extends AsyncTask<String,Void,Void> {

        ArrayList<Item> itemList;

        public GetServerListTask(){
            itemList = new ArrayList<Item>();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            NotificationManager mang = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mang.cancel(ClassConstant.APP_NOTIFICATION_ID);
            // Toast.makeText(getBaseContext(),"Data Download Service Started",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(String... params) {

            URL url;
            HttpURLConnection urlConnection;
            String response;
            Item itemData = null;
            ArrayList<String> notificationString = new ArrayList<String>();

            Log.d("ABHISHEK","in Background service");
            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                response = convertInputStreamToString(inputStream);

                //Parsing Json object

                JSONArray itemArray = new JSONArray(response);
                Log.d("ABHISHEK","Fetching Data and inserting in DB");
                for (int i = 0; i < itemArray.length(); i++) {
                    itemData = new Item();
                    JSONObject tmpObj = itemArray.getJSONObject(i);
                    itemData.itemId = tmpObj.getInt("item_id");
                    itemData.itemName = tmpObj.getString("item_name");
                    itemData.itemType = tmpObj.getString("item_type");
                    itemData.lastRefillDate = tmpObj.getString("rdate");
                    itemData.containerDiameter = tmpObj.getDouble("con_dai");
                    itemData.containerLevel = tmpObj.getDouble("con_hei");
                    itemData.itemWeight = tmpObj.getDouble("con_wei");
                    itemList.add(itemData);

                    if(itemData.itemType.trim().equalsIgnoreCase("Solid")&&itemData.itemWeight< ClassConstant.MASS_THRESHOLD){
                        notificationString.add(itemData.itemName+"    :   "+itemData.itemWeight);
                    }else if (itemData.itemType.trim().equalsIgnoreCase("Liquid")&&
                            (3.14*Math.pow((itemData.containerDiameter/2),2)*itemData.containerLevel)< ClassConstant.VOLUME_THRESHOLD){
                        notificationString.add(itemData.itemName+"    :   "+String.valueOf(3.14*Math.pow((itemData.containerDiameter/2),2)*itemData.containerLevel));
                    }
                }

                if(notificationString.size()>0){
                    NotificationCompat.InboxStyle style = new android.support.v4.app.NotificationCompat.InboxStyle();
                    style.setBigContentTitle("My Smart Kitchen");
                    style.setSummaryText("Following Items are low in quantity in my Kitchen");
                    for(int counter = 0 ; counter <notificationString.size(); counter++){
                        style.addLine(notificationString.get(counter).toString());
                    }

                    Intent actionIntent = new Intent(getBaseContext(),MainActivity.class);
                    actionIntent.putExtra("notification_id",ClassConstant.APP_NOTIFICATION_ID);
                    PendingIntent actionPendingIntent = PendingIntent.getActivity(getBaseContext(), 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
                    builder.setSmallIcon(R.drawable.empty_notification);
                    builder.setTicker("My Smart Kitchen Alert!");
                    builder.setStyle(style);
                    builder.setContentIntent(actionPendingIntent);
                    builder.setAutoCancel(true);
                    builder.setContentText("Following Items are low in quantity in my Kitchen");
                    NotificationManager mang = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    mang.notify(ClassConstant.APP_NOTIFICATION_ID,builder.build());
                }

                putDataToDB(itemList);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("ABHISHEK", "BAD Json received");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
            //putDataToDB();
            return null;
        }

        public void putDataToDB(ArrayList<Item> list){

            for(Item obj:list){
                ContentValues values = new ContentValues();

                if(obj.itemType.trim().equalsIgnoreCase("Solid")){
                    values.put(DatabaseProperties.LAST_REFILL_DATE,obj.lastRefillDate);
                    values.put(DatabaseProperties.ITEM_WEIGHT,obj.itemWeight);
                }else if(obj.itemType.trim().equalsIgnoreCase("Liquid")){
                    values.put(DatabaseProperties.LAST_REFILL_DATE,obj.lastRefillDate);
                    values.put(DatabaseProperties.CONTAINER_DIAMETER,obj.containerDiameter);
                    values.put(DatabaseProperties.CONTAINER_HEIGHT,obj.containerLevel);
                }
                int count = getContentResolver().update(DatabaseProperties.CONTENT_URI,values,DatabaseProperties.ITEM_ID+"=?",new String[]{String.valueOf(obj.itemId)});
                if(count==0){
                     ContentValues insertValues = new ContentValues();
                    insertValues.put(DatabaseProperties.ITEM_ID,obj.itemId);
                    insertValues.put(DatabaseProperties.ITEM_NAME,obj.itemName);
                    insertValues.put(DatabaseProperties.ITEM_TYPE,obj.itemType);
                    insertValues.put(DatabaseProperties.CONTAINER_DIAMETER,obj.containerDiameter);
                    insertValues.put(DatabaseProperties.CONTAINER_HEIGHT,obj.containerLevel);
                    insertValues.put(DatabaseProperties.ITEM_WEIGHT,obj.itemWeight);
                    insertValues.put(DatabaseProperties.LAST_REFILL_DATE,obj.lastRefillDate);
                    getContentResolver().insert(DatabaseProperties.CONTENT_URI,insertValues);
                }
            }

        }

    }


    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

            /* Close Stream */
        if (null != inputStream) {
            inputStream.close();
        }
        return result;

    }


}
