package com.waitty.kitchen.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.waitty.kitchen.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class OrderDetails implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("tableId")
    @Expose
    private int tableId;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("total")
    @Expose
    private double total;
    @SerializedName("tax")
    @Expose
    private double tax;
    @SerializedName("tax_percent")
    @Expose
    private double taxPercent;
    @SerializedName("order_amount")
    @Expose
    private double orderAmount;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("restaurantId")
    @Expose
    private int restaurantId;
    @SerializedName("statusId")
    @Expose
    private int statusId;
    @SerializedName("is_payment_refund")
    @Expose
    private Boolean isPaymentRefund;
    @SerializedName("payment_refund_category_id")
    @Expose
    private String paymentRefundCategoryId;
    @SerializedName("waiterId")
    @Expose
    private int waiterId;
    @SerializedName("accept_date_time")
    @Expose
    private String acceptDateTime;
    @SerializedName("paymentId")
    @Expose
    private String paymentId;
    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("payment_symbol")
    @Expose
    private String paymentSymbol;
    @SerializedName("order_arriving_time")
    @Expose
    private String orderArrivingTime;
    @SerializedName("order_arriving_time_in_minits")
    @Expose
    private String orderArrivingTimeMinits;
    @SerializedName("order_id_display")
    @Expose
    private String orderIdDisplay;
    @SerializedName("kitchenId")
    @Expose
    private int kitchenId;
    @SerializedName("preparing_date_time")
    @Expose
    private String preparingDateTime;
    @SerializedName("order_arriving_time_update_at")
    @Expose
    private String orderArrivingTimeUpdateAt;
    @SerializedName("table")
    @Expose
    private Table table;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("restaurant")
    @Expose
    private Restaurant restaurant;
    @SerializedName("order_status")
    @Expose
    private OrderStatus orderStatus;
    @SerializedName("order_items")
    @Expose
    private List<OrderItem> orderItems = null;
    @SerializedName("waiter")
    @Expose
    private Waiter waiter;

    private String orderEta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getOrderType() {
        return Utility.checkNull(this.orderType);
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(double taxPercent) {
        this.taxPercent = taxPercent;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getComment() {
        return Utility.checkNull(this.comment);
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Boolean getIsPaymentRefund() {
        return isPaymentRefund;
    }

    public void setIsPaymentRefund(Boolean isPaymentRefund) {
        this.isPaymentRefund = isPaymentRefund;
    }

    public String getPaymentRefundCategoryId() {
        return Utility.checkNull(this.paymentRefundCategoryId);
    }

    public void setPaymentRefundCategoryId(String paymentRefundCategoryId) {
        this.paymentRefundCategoryId = paymentRefundCategoryId;
    }

    public int getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(int waiterId) {
        this.waiterId = waiterId;
    }

    public String getAcceptDateTime() {
        return Utility.checkNull(this.acceptDateTime);
    }

    public void setAcceptDateTime(String acceptDateTime) {
        this.acceptDateTime = acceptDateTime;
    }

    public String getPaymentId() {
        return Utility.checkNull(this.paymentId);
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getTransactionId() {
        return Utility.checkNull(this.transactionId);
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentSymbol() {
        return Utility.checkNull(this.paymentSymbol);
    }

    public void setPaymentSymbol(String paymentSymbol) {
        this.paymentSymbol = paymentSymbol;
    }

    public String getOrderArrivingTime() {
        return Utility.checkNull(this.orderArrivingTime);
    }

    public void setOrderArrivingTime(String orderArrivingTime) {
        this.orderArrivingTime = orderArrivingTime;
    }

    public String getOrderArrivingTimeMinits() {
        return Utility.checkNull(this.orderArrivingTimeMinits);
    }

    public void setOrderArrivingTimeMinits(String orderArrivingTimeMinits) {
        this.orderArrivingTimeMinits = orderArrivingTimeMinits;
    }

    public String getOrderIdDisplay() {
        return Utility.checkNull(this.orderIdDisplay);
    }

    public void setOrderIdDisplay(String orderIdDisplay) {
        this.orderIdDisplay = orderIdDisplay;
    }

    public int getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(int kitchenId) {
        this.kitchenId = kitchenId;
    }

    public String getPreparingDateTime() {
        return Utility.checkNull(this.preparingDateTime);
    }

    public void setPreparingDateTime(String preparingDateTime) {
        this.preparingDateTime = preparingDateTime;
    }

    public String getOrderArrivingTimeUpdateAt() {
        return Utility.checkNull(this.orderArrivingTimeUpdateAt);
    }

    public void setOrderArrivingTimeUpdateAt(String orderArrivingTimeUpdateAt) {
        this.orderArrivingTimeUpdateAt = orderArrivingTimeUpdateAt;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public String getOrderEta() {
        return orderEta;
    }

    public void setOrderEta(String orderEta) {
        this.orderEta = orderEta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.userId);
        dest.writeInt(this.tableId);
        dest.writeString(this.orderType);
        dest.writeDouble(this.total);
        dest.writeDouble(this.tax);
        dest.writeDouble(this.taxPercent);
        dest.writeDouble(this.orderAmount);
        dest.writeString(this.comment);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeInt(this.restaurantId);
        dest.writeInt(this.statusId);
        dest.writeValue(this.isPaymentRefund);
        dest.writeString(this.paymentRefundCategoryId);
        dest.writeInt(this.waiterId);
        dest.writeString(this.acceptDateTime);
        dest.writeString(this.paymentId);
        dest.writeString(this.transactionId);
        dest.writeString(this.paymentSymbol);
        dest.writeString(this.orderArrivingTime);
        dest.writeString(this.orderArrivingTimeMinits);
        dest.writeString(this.orderIdDisplay);
        dest.writeInt(this.kitchenId);
        dest.writeString(this.preparingDateTime);
        dest.writeString(this.orderArrivingTimeUpdateAt);
        dest.writeParcelable(this.table, flags);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.restaurant, flags);
        dest.writeParcelable(this.orderStatus, flags);
        dest.writeList(this.orderItems);
        dest.writeParcelable(this.waiter, flags);
        dest.writeString(this.orderEta);
    }

    public OrderDetails() {
    }

    protected OrderDetails(Parcel in) {
        this.id = in.readInt();
        this.userId = in.readInt();
        this.tableId = in.readInt();
        this.orderType = in.readString();
        this.total = in.readDouble();
        this.tax = in.readDouble();
        this.taxPercent = in.readDouble();
        this.orderAmount = in.readDouble();
        this.comment = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.restaurantId = in.readInt();
        this.statusId = in.readInt();
        this.isPaymentRefund = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.paymentRefundCategoryId = in.readString();
        this.waiterId = in.readInt();
        this.acceptDateTime = in.readString();
        this.paymentId = in.readString();
        this.transactionId = in.readString();
        this.paymentSymbol = in.readString();
        this.orderArrivingTime = in.readString();
        this.orderArrivingTimeMinits = in.readString();
        this.orderIdDisplay = in.readString();
        this.kitchenId = in.readInt();
        this.preparingDateTime = in.readString();
        this.orderArrivingTimeUpdateAt = in.readString();
        this.table = in.readParcelable(Table.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.restaurant = in.readParcelable(Restaurant.class.getClassLoader());
        this.orderStatus = in.readParcelable(OrderStatus.class.getClassLoader());
        this.orderItems = new ArrayList<OrderItem>();
        in.readList(this.orderItems, OrderItem.class.getClassLoader());
        this.waiter = in.readParcelable(Waiter.class.getClassLoader());
        this.orderEta = in.readString();
    }

    public static final Parcelable.Creator<OrderDetails> CREATOR = new Parcelable.Creator<OrderDetails>() {
        @Override
        public OrderDetails createFromParcel(Parcel source) {
            return new OrderDetails(source);
        }

        @Override
        public OrderDetails[] newArray(int size) {
            return new OrderDetails[size];
        }
    };
}
