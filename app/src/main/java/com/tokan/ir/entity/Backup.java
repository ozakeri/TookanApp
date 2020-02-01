package com.tokan.ir.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Backup implements Parcelable, Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "packageName")
    private String packageName;

    @ColumnInfo(name = "databaseName")
    private String databaseName;

    @ColumnInfo(name = "currentDbPath")
    private String currentDbPath;

    @ColumnInfo(name = "backupPath")
    private String backupPath;


    public Backup() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getCurrentDbPath() {
        return currentDbPath;
    }

    public void setCurrentDbPath(String currentDbPath) {
        this.currentDbPath = currentDbPath;
    }

    public String getBackupPath() {
        return backupPath;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
    }

    protected Backup(Parcel in) {
        id = in.readInt();
        packageName = in.readString();
        databaseName = in.readString();
        currentDbPath = in.readString();
        backupPath = in.readString();
    }

    public static final Creator<Backup> CREATOR = new Creator<Backup>() {
        @Override
        public Backup createFromParcel(Parcel in) {
            return new Backup(in);
        }

        @Override
        public Backup[] newArray(int size) {
            return new Backup[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(packageName);
        parcel.writeString(databaseName);
        parcel.writeString(currentDbPath);
        parcel.writeString(backupPath);
    }
}
