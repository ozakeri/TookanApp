package com.tokan.ir.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.tokan.ir.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
