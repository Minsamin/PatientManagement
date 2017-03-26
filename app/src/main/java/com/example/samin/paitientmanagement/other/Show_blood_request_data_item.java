package com.example.samin.paitientmanagement.other;


/**
 * Created by Samin on 23-02-2017.
 */

public class Show_blood_request_data_item {
    private String Person_Name;
    private String Person_Address;
    private String Person_Phone;
    private String Person_Email;
    private String Person_Request_blood_group;


    public Show_blood_request_data_item()
    {
    }

    public Show_blood_request_data_item(String person_Name, String person_Address, String person_Phone, String person_Email, String person_Request_blood_group) {
        Person_Name = person_Name;
        Person_Address = person_Address;
        Person_Phone = person_Phone;
        Person_Email = person_Email;
        Person_Request_blood_group = person_Request_blood_group;
    }


    public String getPerson_Name() {
        return Person_Name;
    }

    public void setPerson_Name(String person_Name) {
        Person_Name = person_Name;
    }

    public String getPerson_Address() {
        return Person_Address;
    }

    public void setPerson_Address(String person_Address) {
        Person_Address = person_Address;
    }

    public String getPerson_Phone() {
        return Person_Phone;
    }

    public void setPerson_Phone(String person_Phone) {
        Person_Phone = person_Phone;
    }

    public String getPerson_Email() {
        return Person_Email;
    }

    public void setPerson_Email(String person_Email) {
        Person_Email = person_Email;
    }

    public String getPerson_Request_blood_group() {
        return Person_Request_blood_group;
    }

    public void setPerson_Request_blood_group(String person_Request_blood_group) {
        Person_Request_blood_group = person_Request_blood_group;
    }
}
