package com.example.abhishekmadan.mysmartkitchen.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by praveen.mulchandani on 2/12/2016.
 */
public class Database extends ContentProvider {


    private SQLiteDatabase database;
    static final int uriCode = 1;
    static HashMap<String, String> sValues;


    /**
     * Query to Create a Table
     */

    static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DatabaseProperties.PROVIDER_NAME, DatabaseProperties.DATABASE_NAME, uriCode);
    }

    private static class SmartKitchenDbHelper extends SQLiteOpenHelper {




        public SmartKitchenDbHelper(Context context) {
            super(context, DatabaseProperties.DATABASE_NAME, null, DatabaseProperties.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String CREATE_SMART_KITCHEN_TABLE = "create table " + DatabaseProperties.TABLE_NAME +
                    "( "+DatabaseProperties.DB_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DatabaseProperties.ITEM_ID+ " INTEGER NOT NULL, " +
                    DatabaseProperties.ITEM_NAME+" TEXT NOT NULL, "+
                    DatabaseProperties.CONTAINER_DIAMETER +" FLOAT, "+
                    DatabaseProperties.ITEM_WEIGHT +" FLOAT, " +
                    DatabaseProperties.CONTAINER_HEIGHT +" FLOAT, "+
                    DatabaseProperties.LAST_REFILL_DATE + " TEXT,"+
                    DatabaseProperties.ITEM_TYPE+" TEXT NOT NULL);";
            db.execSQL(CREATE_SMART_KITCHEN_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DatabaseProperties.TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        SmartKitchenDbHelper dbHelper = new SmartKitchenDbHelper(context);
        database = dbHelper.getWritableDatabase();
        return (database == null) ? false : true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DatabaseProperties.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case uriCode:
                // A projection map maps from passed column names to database column names
                queryBuilder.setProjectionMap(sValues);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // Cursor provides read and write access to the database
        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null,
                null, sortOrder);

        // Register to watch for URI changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = database.insert(DatabaseProperties.TABLE_NAME, null, values);
        if (rowId > 0) {
            Uri _uri = ContentUris.withAppendedId(DatabaseProperties.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        } else {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleteCount;
        deleteCount = database.delete(DatabaseProperties.TABLE_NAME,selection,selectionArgs);
        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int count;
        count= database.update(DatabaseProperties.TABLE_NAME,values,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
