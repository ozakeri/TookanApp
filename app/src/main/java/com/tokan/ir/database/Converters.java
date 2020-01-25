package com.tokan.ir.database;

import androidx.room.TypeConverter;

import com.tokan.ir.entity.Customer;

import java.util.ArrayList;

public class Converters {

    @TypeConverter
    public static Customer fromCustomer(Double value) {
        return value == null ? null : new Customer(value);
    }

    @TypeConverter
    public static Double toCustomer(Customer customer) {
        return customer == null ? null : customer.getDoubleList();
    }
}
