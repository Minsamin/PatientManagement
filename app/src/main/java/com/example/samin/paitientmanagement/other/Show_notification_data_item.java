package com.example.samin.paitientmanagement.other;


/**
 * Created by Samin on 23-02-2017.
 */

public class Show_notification_data_item {
    private String Doctor_Email;
    private String Notification_Date;
    private String Notification_Image;
    private String Notification_Text;
    private String Notification_Time;
    private String Notification_To;


    public Show_notification_data_item() {
    }

    public Show_notification_data_item(String doctor_Email, String notification_Date, String notification_Image, String notification_Text, String notification_Time, String notification_To) {
        Doctor_Email = doctor_Email;
        Notification_Date = notification_Date;
        Notification_Image = notification_Image;
        Notification_Text = notification_Text;
        Notification_Time = notification_Time;
        Notification_To = notification_To;
    }

    public String getDoctor_Email() {
        return Doctor_Email;
    }

    public void setDoctor_Email(String doctor_Email) {
        Doctor_Email = doctor_Email;
    }

    public String getNotification_Date() {
        return Notification_Date;
    }

    public void setNotification_Date(String notification_Date) {
        Notification_Date = notification_Date;
    }

    public String getNotification_Image() {
        return Notification_Image;
    }

    public void setNotification_Image(String notification_Image) {
        Notification_Image = notification_Image;
    }

    public String getNotification_Text() {
        return Notification_Text;
    }

    public void setNotification_Text(String notification_Text) {
        Notification_Text = notification_Text;
    }

    public String getNotification_Time() {
        return Notification_Time;
    }

    public void setNotification_Time(String notification_Time) {
        Notification_Time = notification_Time;
    }

    public String getNotification_To() {
        return Notification_To;
    }

    public void setNotification_To(String notification_To) {
        Notification_To = notification_To;
    }
}
