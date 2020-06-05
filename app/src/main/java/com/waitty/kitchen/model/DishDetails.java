package com.waitty.kitchen.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.waitty.kitchen.utility.Utility;

public class DishDetails implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.dishImage);
        dest.writeString(this.description);
    }

    public DishDetails() {
    }

    protected DishDetails(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.dishImage = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<DishDetails> CREATOR = new Parcelable.Creator<DishDetails>() {
        @Override
        public DishDetails createFromParcel(Parcel source) {
            return new DishDetails(source);
        }

        @Override
        public DishDetails[] newArray(int size) {
            return new DishDetails[size];
        }
    };
}
