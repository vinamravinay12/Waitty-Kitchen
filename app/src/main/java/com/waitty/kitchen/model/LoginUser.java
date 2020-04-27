package com.waitty.kitchen.model;

import com.waitty.kitchen.utility.Utility;
import java.util.List;

public class LoginUser {

    int id,parentId;
    String country_code,email,mobile,name,profile_image="",key;
    private List<Restaurant> restaurant = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry_code() {
        return Utility.checkNull(this.country_code);
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getEmail() {
        return Utility.checkNull(this.email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return Utility.checkNull(this.mobile);
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return Utility.checkNull(this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_image() {
        return Utility.checkNull(this.profile_image);
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getKey() {
        return Utility.checkNull(this.key);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Restaurant> getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(List<Restaurant> restaurant) {
        this.restaurant = restaurant;
    }

    public class Restaurant {
        int id,parentId;
        String country_code,email,mobile,name,profile_image="",key;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCountry_code() {
            return Utility.checkNull(this.country_code);
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getEmail() {
            return Utility.checkNull(this.email);
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return Utility.checkNull(this.mobile);
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return Utility.checkNull(this.name);
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProfile_image() {
            return Utility.checkNull(this.profile_image);
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getKey() {
            return Utility.checkNull(this.key);
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
