package com.example.samin.paitientmanagement.other;


/**
 * Created by Samin on 23-02-2017.
 */

public class Show_pathology_data_item {
    private String Name;
    private String Direction;
    private String Address;
    private String Phone;
    private String Website;

    public Show_pathology_data_item(String name, String direction, String address, String phone, String website) {
        Name = name;
        Direction = direction;
        Address = address;
        Phone = phone;
        Website = website;
    }

    public Show_pathology_data_item() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }
}
