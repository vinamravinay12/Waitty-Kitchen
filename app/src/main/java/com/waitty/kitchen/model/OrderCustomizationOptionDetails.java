package com.waitty.kitchen.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.waitty.kitchen.utility.Utility;

public class OrderCustomizationOptionDetails implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("amount")
    @Expose
    private String amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Utility.checkNull(this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return Utility.checkNull(this.amount);
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.amount);
    }

    public OrderCustomizationOptionDetails() {
    }

    protected OrderCustomizationOptionDetails(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.amount = in.readString();
    }

    public static final Parcelable.Creator<OrderCustomizationOptionDetails> CREATOR = new Parcelable.Creator<OrderCustomizationOptionDetails>() {
        @Override
        public OrderCustomizationOptionDetails createFromParcel(Parcel source) {
            return new OrderCustomizationOptionDetails(source);
        }

        @Override
        public OrderCustomizationOptionDetails[] newArray(int size) {
            return new OrderCustomizationOptionDetails[size];
        }
    };
}