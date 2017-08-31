package com.itechnotion.admin.sharelocation;

/**
 * Created by admin on 31/8/17.
 */

public class LocationObject {
    String address;
    String city;
    String state;
    String Country;
    String ZIPcode;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getZIPcode() {
        return ZIPcode;
    }

    public void setZIPcode(String ZIPcode) {
        this.ZIPcode = ZIPcode;
    }
}
