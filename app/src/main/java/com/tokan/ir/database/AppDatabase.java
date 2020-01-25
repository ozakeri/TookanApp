package com.tokan.ir.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.tokan.ir.entity.Customer;
import com.tokan.ir.entity.User;

@Database(entities = {User.class, Customer.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract CustomerDao customerDao();
}
