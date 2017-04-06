package com.example.samin.paitientmanagement.other;

/**
 * Created by samin on 11/6/2016.
 */
public class DoctorDetails {
    private String Name, Email, Phone, Specialization, Image_URL, Timing, Chamber, Experience, Fees;



    public DoctorDetails()
    {

    }

    public DoctorDetails(String name, String email, String phone, String specialization, String image_Url, String timing, String chamber, String experience, String fees) {
        Name = name;
        Email = email;
        Phone = phone;
        Specialization = specialization;
        Image_URL = image_Url;
        Timing = timing;
        Chamber = chamber;
        Experience = experience;
        Fees = fees;
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

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public String getImage_Url() {
        return Image_URL;
    }

    public void setImage_Url(String image_Url) {
        Image_URL = image_Url;
    }

    public String getTiming() {
        return Timing;
    }

    public void setTiming(String timing) {
        Timing = timing;
    }

    public String getChamber() {
        return Chamber;
    }

    public void setChamber(String chamber) {
        Chamber = chamber;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getFees() {
        return Fees;
    }

    public void setFees(String fees) {
        Fees = fees;
    }
}