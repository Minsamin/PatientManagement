package com.example.samin.paitientmanagement.other;

import android.util.Log;


/**
 * Created by Samin on 23-02-2017.
 */

public class Show_appointment_data_item {
    private String Appointment_Doctor_Name;
    private String Appointment_Doctor_Email;
    private String Appointment_Doctor_phone;
    private String Appointment_Date;
    private String Appointment_Reason;
    private String Patient_Name;
    private String Patient_Phone;
    private String Patient_Email;
    private String Request_Status;



    public Show_appointment_data_item(String Appointment_Doctor_Name, String Appointment_Doctor_Email, String Appointment_Doctor_phone, String Appointment_Date, String Appointment_Reason, String Patient_Name, String Patient_Phone,String Patient_Email,String Request_Status) {
        this.Appointment_Doctor_Name = Appointment_Doctor_Name;
        this.Appointment_Doctor_Email = Appointment_Doctor_Email;
        this.Appointment_Doctor_phone = Appointment_Doctor_phone;
        this.Appointment_Date = Appointment_Date;
        this.Appointment_Reason = Appointment_Reason;
        this.Patient_Name = Patient_Name;
        this.Patient_Phone = Patient_Phone;
        this.Patient_Email = Patient_Email;
        this.Request_Status = Request_Status;

    }

    public Show_appointment_data_item() {
    }

    public String getAppointment_Doctor_Name() {
       // Log.d("LOGGED", "populateViewHolder: Called ");
        return Appointment_Doctor_Name;
    }

    public void setAppointment_Doctor_Name(String Appointment_Doctor_Name) {
        this.Appointment_Doctor_Name = Appointment_Doctor_Name;
    }

    public String getAppointment_Doctor_Email() {
        return Appointment_Doctor_Email;
    }

    public void setAppointment_Doctor_Email(String Appointment_Doctor_Email) {
        this.Appointment_Doctor_Email = Appointment_Doctor_Email;
    }

    public String getAppointment_Doctor_phone() {
        return Appointment_Doctor_phone;
    }

    public void setAppointment_Doctor_phone(String Appointment_Doctor_phone) {
        this.Appointment_Doctor_phone = Appointment_Doctor_phone;
    }

    public String getAppointment_Date() {
        return Appointment_Date;
    }

    public void setAppointment_Date(String Appointment_Date) {
        this.Appointment_Date = Appointment_Date;
    }

    public String getAppointment_Reason() {
        return Appointment_Reason;
    }

    public void setAppointment_Reason(String Appointment_Reason) {
        this.Appointment_Reason = Appointment_Reason;
    }

    public String getPatient_Name() {
        return Patient_Name;
    }

    public void setPatient_Name(String Patient_Name) {
        this.Patient_Name = Patient_Name;
    }

    public String getPatient_Phone() {
        return Patient_Phone;
    }

    public void setPatient_Phone(String Patient_Phone) {
        this.Patient_Phone = Patient_Phone;
    }



    public String getPatient_Email() {
        return Patient_Email;
    }

    public void setPatient_Email(String Patient_Email) {
        this.Patient_Email = Patient_Email;
    }


     public String getRequest_Status() {
        return Request_Status;
    }

    public void setRequest_Status(String Request_Status) {
        this.Request_Status = Request_Status;
    }





}
