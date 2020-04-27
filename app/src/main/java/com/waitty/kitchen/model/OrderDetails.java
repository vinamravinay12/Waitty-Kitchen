package com.waitty.kitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.waitty.kitchen.utility.Utility;
import java.util.List;

public class OrderDetails {

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

    public class City {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;

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

    }

    public class Country {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;

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

    }

    public class DishDetails {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("dish_image")
        @Expose
        private String dishImage;
        @SerializedName("description")
        @Expose
        private String description;

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

        public String getDishImage() {
            return Utility.checkNull(this.dishImage);
        }

        public void setDishImage(String dishImage) {
            this.dishImage = dishImage;
        }

        public String getDescription() {
            return Utility.checkNull(this.description);
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

    public class OrderCustomizationDetails {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("type")
        @Expose
        private String type;

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

        public String getType() {
            return Utility.checkNull(this.type);
        }

        public void setType(String type) {
            this.type = type;
        }

    }

    public class OrderCustomizationOptionDetails {

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

    }

    public class OrderItem {
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

    }

    public class OrderItemCustomization {

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
        private List<OrderItemCustomizationsOption> orderItemCustomizationsOptions = null;
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

        public List<OrderItemCustomizationsOption> getOrderItemCustomizationsOptions() {
            return orderItemCustomizationsOptions;
        }

        public void setOrderItemCustomizationsOptions(List<OrderItemCustomizationsOption> orderItemCustomizationsOptions) {
            this.orderItemCustomizationsOptions = orderItemCustomizationsOptions;
        }

        public OrderCustomizationDetails getOrderCustomizationDetails() {
            return orderCustomizationDetails;
        }

        public void setOrderCustomizationDetails(OrderCustomizationDetails orderCustomizationDetails) {
            this.orderCustomizationDetails = orderCustomizationDetails;
        }

    }

    public class OrderItemCustomizationsOption {

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

    }

    public class OrderStatus {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;

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

    }

    public class Restaurant {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("country_code")
        @Expose
        private String countryCode;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("key")
        @Expose
        private String key;
        @SerializedName("tax_percent")
        @Expose
        private double taxPercent;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("city")
        @Expose
        private City city;
        @SerializedName("country")
        @Expose
        private Country country;

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

        public String getEmail() {
            return Utility.checkNull(this.email);
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCountryCode() {
            return Utility.checkNull(this.countryCode);
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getMobile() {
            return Utility.checkNull(this.mobile);
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getKey() {
            return Utility.checkNull(this.key);
        }

        public void setKey(String key) {
            this.key = key;
        }

        public double getTaxPercent() {
            return taxPercent;
        }

        public void setTaxPercent(double taxPercent) {
            this.taxPercent = taxPercent;
        }

        public String getAddress() {
            return Utility.checkNull(this.address);
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public City getCity() {
            return city;
        }

        public void setCity(City city) {
            this.city = city;
        }

        public Country getCountry() {
            return country;
        }

        public void setCountry(Country country) {
            this.country = country;
        }

    }

    public class Table {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;

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

    }

    public class User {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("country_code")
        @Expose
        private String countryCode;
        @SerializedName("mobile")
        @Expose
        private String mobile;

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

        public String getEmail() {
            return Utility.checkNull(this.email);
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCountryCode() {
            return Utility.checkNull(this.countryCode);
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getMobile() {
            return Utility.checkNull(this.mobile);
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

    }

    public class Waiter {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("key")
        @Expose
        private String key;

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

        public String getKey() {
            return Utility.checkNull(this.key);
        }

        public void setKey(String key) {
            this.key = key;
        }

    }
}
