package com.tokan.ir.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

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

    @ColumnInfo(name = "startVoidedTime")
    private String startVoidedTime;

    @ColumnInfo(name = "endVoidedTime")
    private String endVoidedTime;

    @ColumnInfo(name = "delayTime")
    private String delayTime;

    @ColumnInfo(name = "startFlowTime")
    private String startFlowTime;

    @ColumnInfo(name = "endFlowTime")
    private String endFlowTime;

    @ColumnInfo(name = "timeToMaxFlow")
    private String timeToMaxFlow;

    @ColumnInfo(name = "voidedVolume")
    private String voidedVolume;


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
        startVoidedTime = in.readString();
        endVoidedTime = in.readString();
        delayTime = in.readString();
        startFlowTime = in.readString();
        endFlowTime = in.readString();
        timeToMaxFlow = in.readString();
        voidedVolume = in.readString();
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

    public String getStartVoidedTime() {
        return startVoidedTime;
    }

    public void setStartVoidedTime(String startVoidedTime) {
        this.startVoidedTime = startVoidedTime;
    }

    public String getEndVoidedTime() {
        return endVoidedTime;
    }

    public void setEndVoidedTime(String endVoidedTime) {
        this.endVoidedTime = endVoidedTime;
    }

    public String getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }

    public String getStartFlowTime() {
        return startFlowTime;
    }

    public void setStartFlowTime(String startFlowTime) {
        this.startFlowTime = startFlowTime;
    }

    public String getEndFlowTime() {
        return endFlowTime;
    }

    public void setEndFlowTime(String endFlowTime) {
        this.endFlowTime = endFlowTime;
    }

    public String getTimeToMaxFlow() {
        return timeToMaxFlow;
    }

    public void setTimeToMaxFlow(String timeToMaxFlow) {
        this.timeToMaxFlow = timeToMaxFlow;
    }

    public String getVoidedVolume() {
        return voidedVolume;
    }

    public void setVoidedVolume(String voidedVolume) {
        this.voidedVolume = voidedVolume;
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
        parcel.writeString(startVoidedTime);
        parcel.writeString(endVoidedTime);
        parcel.writeString(delayTime);
        parcel.writeString(startFlowTime);
        parcel.writeString(endFlowTime);
        parcel.writeString(timeToMaxFlow);
        parcel.writeString(voidedVolume);
    }
}
