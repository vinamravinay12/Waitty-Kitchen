package com.waitty.kitchen.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderItemCustomization implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("orderItemId")
    @Expose
    private int orderItemId;
    @SerializedName("customizationId")
    @Expose
    private int customizationId;
    @SerializedName("order_item_customizations_options")
    @Expose
    private List<OrderItemCustomizationOption> orderItemCustomizationsOptions = null;
    @SerializedName("order_customization_details")
    @Expose
    private OrderCustomizationDetails orderCustomizationDetails;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getCustomizationId() {
        return customizationId;
    }

    public void setCustomizationId(int customizationId) {
        this.customizationId = customizationId;
    }

    public List<OrderItemCustomizationOption> getOrderItemCustomizationsOptions() {
        return orderItemCustomizationsOptions;
    }

    public void setOrderItemCustomizationsOptions(List<OrderItemCustomizationOption> orderItemCustomizationsOptions) {
        this.orderItemCustomizationsOptions = orderItemCustomizationsOptions;
    }

    public OrderCustomizationDetails getOrderCustomizationDetails() {
        return orderCustomizationDetails;
    }

    public void setOrderCustomizationDetails(OrderCustomizationDetails orderCustomizationDetails) {
        this.orderCustomizationDetails = orderCustomizationDetails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.orderItemId);
        dest.writeInt(this.customizationId);
        dest.writeList(this.orderItemCustomizationsOptions);
        dest.writeParcelable(this.orderCustomizationDetails, flags);
    }

    public OrderItemCustomization() {
    }

    protected OrderItemCustomization(Parcel in) {
        this.id = in.readInt();
        this.orderItemId = in.readInt();
        this.customizationId = in.readInt();
        this.orderItemCustomizationsOptions = new ArrayList<OrderItemCustomizationOption>();
        in.readList(this.orderItemCustomizationsOptions, OrderItemCustomizationOption.class.getClassLoader());
        this.orderCustomizationDetails = in.readParcelable(OrderCustomizationDetails.class.getClassLoader());
    }

    public static final Parcelable.Creator<OrderItemCustomization> CREATOR = new Parcelable.Creator<OrderItemCustomization>() {
        @Override
        public OrderItemCustomization createFromParcel(Parcel source) {
            return new OrderItemCustomization(source);
        }

        @Override
        public OrderItemCustomization[] newArray(int size) {
            return new OrderItemCustomization[size];
        }
    };
}
