package com.waitty.kitchen.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.waitty.kitchen.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class OrderItem implements Parcelable {
    @SerializedName("in_stock")
    @Expose
    private int in_stock;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("dish_amount")
    @Expose
    private double dishAmount;
    @SerializedName("dishId")
    @Expose
    private int dishId;
    @SerializedName("orderId")
    @Expose
    private int orderId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("is_prepared")
    @Expose
    private Boolean isPrepared;
    @SerializedName("order_item_customizations")
    @Expose
    private List<OrderItemCustomization> orderItemCustomizations = null;
    @SerializedName("dish_details")
    @Expose
    private DishDetails dishDetails;

    public int getIn_stock() {
        return in_stock;
    }

    public void setIn_stock(int in_stock) {
        this.in_stock = in_stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDishAmount() {
        return dishAmount;
    }

    public void setDishAmount(double dishAmount) {
        this.dishAmount = dishAmount;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCreatedAt() {
        return Utility.checkNull(this.createdAt);
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return Utility.checkNull(this.updatedAt);
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsPrepared() {
        return isPrepared;
    }

    public void setIsPrepared(Boolean isPrepared) {
        this.isPrepared = isPrepared;
    }

    public List<OrderItemCustomization> getOrderItemCustomizations() {
        return orderItemCustomizations;
    }

    public void setOrderItemCustomizations(List<OrderItemCustomization> orderItemCustomizations) {
        this.orderItemCustomizations = orderItemCustomizations;
    }

    public DishDetails getDishDetails() {
        return dishDetails;
    }

    public void setDishDetails(DishDetails dishDetails) {
        this.dishDetails = dishDetails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.in_stock);
        dest.writeInt(this.id);
        dest.writeInt(this.quantity);
        dest.writeDouble(this.dishAmount);
        dest.writeInt(this.dishId);
        dest.writeInt(this.orderId);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeValue(this.isPrepared);
        dest.writeList(this.orderItemCustomizations);
        dest.writeParcelable(this.dishDetails, flags);
    }

    public OrderItem() {
    }

    protected OrderItem(Parcel in) {
        this.in_stock = in.readInt();
        this.id = in.readInt();
        this.quantity = in.readInt();
        this.dishAmount = in.readDouble();
        this.dishId = in.readInt();
        this.orderId = in.readInt();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.isPrepared = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.orderItemCustomizations = new ArrayList<OrderItemCustomization>();
        in.readList(this.orderItemCustomizations, OrderItemCustomization.class.getClassLoader());
        this.dishDetails = in.readParcelable(DishDetails.class.getClassLoader());
    }

    public static final Parcelable.Creator<OrderItem> CREATOR = new Parcelable.Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel source) {
            return new OrderItem(source);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };
}
