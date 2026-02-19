package com.preet.androidtemplate.core.model;

public class SignupRequest {

    private String fullname;
    private String mobile_number;
    private String email;
    private String dob;
    private String gender;
    private int height_cm;
    private int weight_kg;
    private String password;

    public SignupRequest(String fullname,
                         String mobile_number,
                         String email,
                         String dob,
                         String gender,
                         int height_cm,
                         int weight_kg,
                         String password) {

        this.fullname = fullname;
        this.mobile_number = mobile_number;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.height_cm = height_cm;
        this.weight_kg = weight_kg;
        this.password = password;
    }

    // getters only (no setters needed for request)
    public String getFullname() { return fullname; }
    public String getMobile_number() { return mobile_number; }
    public String getEmail() { return email; }
    public String getDob() { return dob; }
    public String getGender() { return gender; }
    public int getHeight_cm() { return height_cm; }
    public int getWeight_kg() { return weight_kg; }
    public String getPassword() { return password; }
}

