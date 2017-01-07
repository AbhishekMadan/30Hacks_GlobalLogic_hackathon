package com.example.abhishekmadan.mysmartkitchen.activity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abhishekmadan.mysmartkitchen.R;
import com.example.abhishekmadan.mysmartkitchen.adapter.ItemListAdapter;
import com.example.abhishekmadan.mysmartkitchen.database.Database;
import com.example.abhishekmadan.mysmartkitchen.database.DatabaseProperties;
import com.example.abhishekmadan.mysmartkitchen.modal.ClassConstant;
import com.example.abhishekmadan.mysmartkitchen.modal.Item;
import com.example.abhishekmadan.mysmartkitchen.service.GetDataService;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public Toolbar mToolBar;
    public ArrayList<Item> mItemList;
    public ItemListAdapter adapter;
    public ListView mItemListView;
    public ProgressBar mProgressBar;
    Handler mHandler = new Handler();
    public MyObserver myObserver = null;

    //temp creation
    public Database obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startMyService();

    }

    @Override
    protected void onStart() {
        super.onStart();
        NotificationManager mang = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mang.cancel(ClassConstant.APP_NOTIFICATION_ID);
        init();
        RegisterContentObserver();
        startTask();

    }

    public void init(){
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("MySmartKitchen");

        mProgressBar = (ProgressBar) findViewById(R.id.main_progressbar);

        mItemListView = (ListView) findViewById(R.id.item_listview);
        mItemList = new ArrayList<Item>();
        adapter = new ItemListAdapter(MainActivity.this,mItemList);
        mItemListView.setAdapter(adapter);
        mItemListView.setOnItemClickListener(this);

    }

   public void startTask(){
       mItemList.clear();
       mProgressBar.setVisibility(View.VISIBLE);
       Cursor cursor = getContentResolver().query(DatabaseProperties.CONTENT_URI,null,null,null,null);
       //get data from cursor and use it in adapter list

             while(cursor.moveToNext()){
             Item obj = new Item();
             obj.itemId = cursor.getInt(cursor.getColumnIndex(DatabaseProperties.ITEM_ID));
             obj.itemName = cursor.getString(cursor.getColumnIndex(DatabaseProperties.ITEM_NAME));
             obj.itemType = cursor.getString(cursor.getColumnIndex(DatabaseProperties.ITEM_TYPE));
             obj.itemWeight = cursor.getDouble(cursor.getColumnIndex(DatabaseProperties.ITEM_WEIGHT));
             obj.containerDiameter = cursor.getDouble(cursor.getColumnIndex(DatabaseProperties.CONTAINER_DIAMETER));
             obj.containerLevel = cursor.getDouble(cursor.getColumnIndex(DatabaseProperties.CONTAINER_HEIGHT));
             obj.itemType = cursor.getString(cursor.getColumnIndex(DatabaseProperties.ITEM_TYPE));
             obj.lastRefillDate = cursor.getString(cursor.getColumnIndex(DatabaseProperties.LAST_REFILL_DATE));
           mItemList.add(obj);
       }
       adapter.notifyDataSetChanged();
       mProgressBar.setVisibility(View.GONE);
   }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AddressDetailsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item tempObj = new Item();
        TextView itemCode = (TextView) view.findViewById(R.id.item_code_textview);
        TextView itemName = (TextView) view.findViewById(R.id.item_name_textview);
        TextView itemLastPurchaseDate = (TextView) view.findViewById(R.id.last_purchase_date_textview);

        tempObj.itemId = Integer.parseInt(itemCode.getText().toString());
        tempObj.itemName = itemName.getText().toString();
        tempObj.lastRefillDate = itemLastPurchaseDate.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putSerializable("item",tempObj);
        Intent intent = new Intent(this,Container.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startMyService(){
        Intent intent1 = new Intent(MainActivity.this, GetDataService.class);
        PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this, 0, intent1, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND,10);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC,calendar.getTimeInMillis(),30*1000,pendingIntent);
    }
    //register the content observer to get Notification of data change in the content provider

    //Unregister the content observer as the application no longer needs it.
    @Override
    protected void onPause() {
        super.onPause();
        UnRegisterContentObserver();
    }

    /**
     * Register Content Observer to listen to data Change
     */
    private void RegisterContentObserver(){
        ContentResolver cr  = getContentResolver();
        myObserver = new MyObserver(mHandler);
        cr.registerContentObserver(DatabaseProperties.CONTENT_URI,true,myObserver);
    }

    /**
     * UnRegister the content Observer
     */
    private void UnRegisterContentObserver(){
        ContentResolver cr  = getContentResolver();
        if(myObserver != null) {
            cr.unregisterContentObserver(myObserver);
            myObserver = null;
        }
    }

    class MyObserver extends ContentObserver{

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MyObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d("ABHISHEK","Listen to changes in DB! Listner");
            startTask();

        }
    }
}
