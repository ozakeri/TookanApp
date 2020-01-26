package com.tokan.ir.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tokan.ir.entity.Customer;
import com.tokan.ir.entity.User;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CustomerDao {

    @Query("SELECT * FROM customer")
    List<Customer> getCustomers();

    @Query("SELECT * FROM customer WHERE nameFamily LIKE :s")
    List<Customer> getCustomersByKeyword(String s);

    @Insert(onConflict = REPLACE)
    void insertCustomer(Customer customer);

    @Delete
    void deleteCustomer(Customer customer);

    @Update
    void updateCustomer(Customer customer);
}
