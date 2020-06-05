package com.waitty.kitchen.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItemCustomizationOption implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("orderItemCustomizationId")
    @Expose
    private int orderItemCustomizationId;
    @SerializedName("customizationOptionId")
    @Expose
    private int customizationOptionId;
    @SerializedName("order_customization_option_details")
    @Expose
    private OrderCustomizationOptionDetails orderCustomizationOptionDetails;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderItemCustomizationId() {
        return orderItemCustomizationId;
    }

    public void setOrderItemCustomizationId(int orderItemCustomizationId) {
        this.orderItemCustomizationId = orderItemCustomizationId;
    }

    public int getCustomizationOptionId() {
        return customizationOptionId;
    }

    public void setCustomizationOptionId(int customizationOptionId) {
        this.customizationOptionId = customizationOptionId;
    }

    public OrderCustomizationOptionDetails getOrderCustomizationOptionDetails() {
        return orderCustomizationOptionDetails;
    }

    public void setOrderCustomizationOptionDetails(OrderCustomizationOptionDetails orderCustomizationOptionDetails) {
        this.orderCustomizationOptionDetails = orderCustomizationOptionDetails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.orderItemCustomizationId);
        dest.writeInt(this.customizationOptionId);
        dest.writeParcelable(this.orderCustomizationOptionDetails, flags);
    }

    public OrderItemCustomizationOption() {
    }

    protected OrderItemCustomizationOption(Parcel in) {
        this.id = in.readInt();
        this.orderItemCustomizationId = in.readInt();
        this.customizationOptionId = in.readInt();
        this.orderCustomizationOptionDetails = in.readParcelable(OrderCustomizationOptionDetails.class.getClassLoader());
    }

    public static final Parcelable.Creator<OrderItemCustomizationOption> CREATOR = new Parcelable.Creator<OrderItemCustomizationOption>() {
        @Override
        public OrderItemCustomizationOption createFromParcel(Parcel source) {
            return new OrderItemCustomizationOption(source);
        }

        @Override
        public OrderItemCustomizationOption[] newArray(int size) {
            return new OrderItemCustomizationOption[size];
        }
    };
}
