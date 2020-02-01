package com.tokan.ir.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tokan.ir.entity.Backup;
import com.tokan.ir.entity.Error;

import java.util.List;

@Dao
public interface BackupDao {

    @Query("SELECT * FROM backup")
    List<Backup> getBackups();

    @Insert
    void insertBackup(Backup backup);

    @Delete
    void deleteBackup(Backup backup);

    @Update
    void updateBackup(Backup backup);
}
