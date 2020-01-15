package com.tokan.ir.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private Context context;
    private static DatabaseClient mInstance;
    private AppDatabase appDatabase;

    public DatabaseClient(Context context) {
        this.context = context;
        appDatabase = Room.databaseBuilder(context,AppDatabase.class,"MyDatabase").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
