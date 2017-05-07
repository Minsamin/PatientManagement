package com.example.samin.paitientmanagement.other;


/**
 * Created by Samin on 23-02-2017.
 */

public class Show_health_tips_data_item {
    private String Doctor_email;
    private String Post_about;
    private String Post_date;
    private String Post_image;
    private String Post_title;
    private String Posted_by;
    private String Thank_counter;
    private String Thank_person;


    public Show_health_tips_data_item() {
    }


    public Show_health_tips_data_item(String doctor_email, String post_about, String post_date, String post_image, String post_title, String posted_by, String thank_counter,String thank_person) {
        Doctor_email = doctor_email;
        Post_about = post_about;
        Post_date = post_date;
        Post_image = post_image;
        Post_title = post_title;
        Posted_by = posted_by;
        Thank_counter = thank_counter;
        Thank_person =thank_person;
    }

    public String getDoctor_email() {
        return Doctor_email;
    }

    public void setDoctor_email(String doctor_email) {
        Doctor_email = doctor_email;
    }

    public String getPost_about() {
        return Post_about;
    }

    public void setPost_about(String post_about) {
        Post_about = post_about;
    }

    public String getPost_date() {
        return Post_date;
    }

    public void setPost_date(String post_date) {
        Post_date = post_date;
    }

    public String getPost_image() {
        return Post_image;
    }

    public void setPost_image(String post_image) {
        Post_image = post_image;
    }

    public String getPost_title() {
        return Post_title;
    }

    public void setPost_title(String post_title) {
        Post_title = post_title;
    }

    public String getPosted_by() {
        return Posted_by;
    }

    public void setPosted_by(String posted_by) {
        Posted_by = posted_by;
    }

    public String getThank_counter() {
        return Thank_counter;
    }

    public void setThank_counter(String thank_counter) {
        Thank_counter = thank_counter;
    }


    public String getThank_person() {
        return Thank_person;
    }

    public void setThank_person(String thank_person) {
        Thank_person = thank_person;
    }
}
