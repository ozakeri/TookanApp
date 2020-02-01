package com.tokan.ir.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.tokan.ir.entity.Backup;
import com.tokan.ir.entity.Customer;
import com.tokan.ir.entity.Error;
import com.tokan.ir.entity.User;

@Database(entities = {User.class, Customer.class, Error.class, Backup.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract CustomerDao customerDao();

    public abstract ErrorDao errorDao();

    public abstract BackupDao backupDao();
}
