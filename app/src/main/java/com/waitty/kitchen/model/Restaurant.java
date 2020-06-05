package com.waitty.kitchen.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.waitty.kitchen.utility.Utility;

public class Restaurant implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.countryCode);
        dest.writeString(this.mobile);
        dest.writeString(this.key);
        dest.writeDouble(this.taxPercent);
        dest.writeString(this.address);
        dest.writeParcelable(this.city, flags);
        dest.writeParcelable(this.country, flags);
    }

    public Restaurant() {
    }

    protected Restaurant(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.email = in.readString();
        this.countryCode = in.readString();
        this.mobile = in.readString();
        this.key = in.readString();
        this.taxPercent = in.readDouble();
        this.address = in.readString();
        this.city = in.readParcelable(City.class.getClassLoader());
        this.country = in.readParcelable(Country.class.getClassLoader());
    }

    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel source) {
            return new Restaurant(source);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}