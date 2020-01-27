package com.tokan.ir.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Customer implements Parcelable, Serializable {

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

    @ColumnInfo(name = "flowValue")
    private String flowValue;

    @ColumnInfo(name = "volumeValue")
    private String volumeValue;

    @ColumnInfo(name = "comment")
    private String comment;

    @ColumnInfo(name = "startTime")
    private Date startTime;

    @ColumnInfo(name = "endTime")
    private Date endTime;

    @ColumnInfo(name = "delayTime")
    private Date delayTime;


    public Customer() {
    }

    protected Customer(Parcel in) {
        id = in.readInt();
        nameFamily = in.readString();
        nationalCode = in.readString();
        birthDate = in.readString();
        sex = in.readString();
        testDate = in.readString();
        flowValue = in.readString();
        volumeValue = in.readString();
        comment = in.readString();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

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

    public String getFlowValue() {
        return flowValue;
    }

    public void setFlowValue(String flowValue) {
        this.flowValue = flowValue;
    }

    public String getVolumeValue() {
        return volumeValue;
    }

    public void setVolumeValue(String volumeValue) {
        this.volumeValue = volumeValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nameFamily);
        parcel.writeString(nationalCode);
        parcel.writeString(birthDate);
        parcel.writeString(sex);
        parcel.writeString(testDate);
        parcel.writeString(flowValue);
        parcel.writeString(volumeValue);
        parcel.writeString(comment);
    }
}
