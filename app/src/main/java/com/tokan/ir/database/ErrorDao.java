package com.tokan.ir.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tokan.ir.entity.Error;

import java.util.List;

@Dao
public interface ErrorDao {

    @Query("SELECT * FROM error")
    List<Error> getErrors();

    @Insert
    void insertError(Error error);

    @Delete
    void deleteError(Error error);

    @Update
    void updateError(Error error);
}
