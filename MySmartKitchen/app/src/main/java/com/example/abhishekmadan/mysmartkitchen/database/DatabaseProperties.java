package com.example.abhishekmadan.mysmartkitchen.database;

import android.net.Uri;

/**
 * Created by praveen.mulchandani on 2/12/2016.
 */
public class DatabaseProperties {

    public static final int DATABASE_VERSION = 6;
    public static final String PROVIDER_NAME = "com.smartkitchen.db";

    public static final String TABLE_NAME = "SmartKitchen";
    public static final String DATABASE_NAME="SmartKitchenDb";
    public static final String URL = "content://" + PROVIDER_NAME + "/"+DATABASE_NAME;
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String DB_ID = "_id";
    public static final String ITEM_ID = "ItemId";
    public static final String ITEM_NAME = "ItemName";
    public static final String CONTAINER_DIAMETER = "Container_Diameter";
    public static final String CONTAINER_HEIGHT = "Container_Height";
    public static final String ITEM_WEIGHT = "itemWeight";
    public static final String ITEM_TYPE = "itemType";
    public static final String LAST_REFILL_DATE = "Last_Refill_Date";


}
