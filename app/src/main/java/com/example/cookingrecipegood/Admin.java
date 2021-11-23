package com.example.cookingrecipegood;

public class Admin {
    private String Fname,Lname,Emaild,Password,Confirmpassword,Moblie,Address,City,District;

    public Admin(String fname, String lname, String emaild, String password, String confirmpassword, String moblie, String address, String city, String district) {
        Fname = fname;
        Lname = lname;
        Emaild = emaild;
        Password = password;
        Confirmpassword = confirmpassword;
        Moblie = moblie;
        Address = address;
        City = city;
        District = district;
    }

    public Admin(){

    }
    public String getFname() {
        return Fname;
    }

    public String getLname() {
        return Lname;
    }

    public String getEmaild() {
        return Emaild;
    }

    public String getPassword() {
        return Password;
    }

    public String getConfirmpassword() {
        return Confirmpassword;
    }

    public String getMoblie() {
        return Moblie;
    }

    public String getAddress() {
        return Address;
    }

    public String getCity() {
        return City;
    }

    public String getDistrict() {
        return District;
    }
}
