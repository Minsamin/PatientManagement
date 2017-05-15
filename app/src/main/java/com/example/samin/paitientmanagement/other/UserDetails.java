package com.example.samin.paitientmanagement.other;

/**
 * Created by samin on 11/6/2016.
 */
public class UserDetails {
    private String Name, Email, Phone ,User_Type,Image_URL,Address;



    public UserDetails()
    {

    }

    public UserDetails(String name, String email, String phone, String user_Type, String image_URL, String address) {
        Name = name;
        Email = email;
        Phone = phone;
        User_Type = user_Type;
        Image_URL = image_URL;
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUser_Type() {
        return User_Type;
    }

    public void setUser_Type(String user_Type) {
        User_Type = user_Type;
    }

    public String getImage_URL() {
        return Image_URL;
    }

    public void setImage_URL(String image_URL) {
        Image_URL = image_URL;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}