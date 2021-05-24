package com.trainingexample.convertapitosqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.trainingexample.convertapitosqlite.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DbHelper extends SQLiteOpenHelper {

    public static final String TAG = "DataBase HELPER";
    public static final String DATABASE_NAME = "reciters.db";
    public static final int DATABASE_VERSION = 1;
    SQLiteDatabase db;
    Context context;
    Resources mResources;
    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mResources = context.getResources();
        db = this.getWritableDatabase();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE
                = "CREATE TABLE " + DBContract.MenuEntry.TABLE_NAME + " (" +
                DBContract.MenuEntry.RAW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContract.MenuEntry.SERVER_COLUMN + " TEXT UNIQUE NOT NULL, " +
                DBContract.MenuEntry.LITTER_COLUMN + " TEXT NOT NULL, " +
                DBContract.MenuEntry.NAME_COLUMN + " TEXT NOT NULL, " +
                DBContract.MenuEntry.COUNT_COLUMN + " TEXT NOT NULL, " +
                DBContract.MenuEntry.SURAS_COLUMN + " TEXT NOT NULL, " +
                DBContract.MenuEntry.REWAYA_COLUMN + " TEXT NOT NULL, " +
                DBContract.MenuEntry.ID_COLUMN + " TEXT NOT NULL " + " );";

        db.execSQL(SQL_CREATE_TABLE);
        Log.d(TAG, "DB created successfully");

        try {
            readDataToDb(db);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void readDataToDb(SQLiteDatabase db) throws IOException, JSONException {
        final String SERVER_COLUMN = "Server";
        final String LITTER_COLUMN = "letter";
        final String NAME_COLUMN = "name";
        final String COUNT_COLUMN = "count";
        final String SURAS_COLUMN = "suras";
        final String REWAYA_COLUMN = "rewaya";
        final String ID_COLUMN = "id";

        try {
            String jsonDataString = readDataJsonFromFile();
            JSONObject object = new JSONObject(jsonDataString);
            JSONArray menuItemJsonArray = object.getJSONArray("reciters");

            for (int i=0; i<menuItemJsonArray.length(); i++) {
                String server, litter, name, count, suras, rewaya, id;

                JSONObject menuItemObject = menuItemJsonArray.getJSONObject(i);

                server = menuItemObject.getString(SERVER_COLUMN);
                litter = menuItemObject.getString(LITTER_COLUMN);
                name = menuItemObject.getString(NAME_COLUMN);
                count = menuItemObject.getString(COUNT_COLUMN);
                suras = menuItemObject.getString(SURAS_COLUMN);
                rewaya = menuItemObject.getString(REWAYA_COLUMN);
                id = menuItemObject.getString(ID_COLUMN);
                ContentValues menuValue = new ContentValues();

                menuValue.put(DBContract.MenuEntry.SERVER_COLUMN,server);
                menuValue.put(DBContract.MenuEntry.LITTER_COLUMN,litter);
                menuValue.put(DBContract.MenuEntry.NAME_COLUMN,name);
                menuValue.put(DBContract.MenuEntry.COUNT_COLUMN,count);
                menuValue.put(DBContract.MenuEntry.SURAS_COLUMN,suras);
                menuValue.put(DBContract.MenuEntry.REWAYA_COLUMN,rewaya);
                menuValue.put(DBContract.MenuEntry.ID_COLUMN,id);
                db.insert(DBContract.MenuEntry.TABLE_NAME, null, menuValue);
                Log.d(TAG, "inserted successfully: " + menuValue);

            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private String readDataJsonFromFile() throws IOException {
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jasonDataString = null;
            inputStream = mResources.openRawResource(R.raw.json_file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((jasonDataString = bufferedReader.readLine()) != null) {
                builder.append(jasonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }
}
