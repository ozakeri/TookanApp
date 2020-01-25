package com.tokan.ir.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity
public class Customer implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nameFamily")
    private String nameFamily;

    @ColumnInfo(name = "nationalCode")
    private String nationalCode;

    @ColumnInfo(name = "birthDate")
    private String birthDate;

    @ColumnInfo(name = "sex")
    private String sex;

    @ColumnInfo(name = "testDate")
    private String testDate;

    @ColumnInfo(name = "longList")
    private double doubleList;

    public Customer() {
    }

    public Customer(double doubleList) {
        this.doubleList = doubleList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameFamily() {
        return nameFamily;
    }

    public void setNameFamily(String nameFamily) {
        this.nameFamily = nameFamily;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public double getDoubleList() {
        return doubleList;
    }

    public void setDoubleList(double doubleList) {
        this.doubleList = doubleList;
    }
}
