package com.example.cookingrecipegood.AdminfoodPanel;

public class dataitemoldoderatadmin {
    String Foodname,Phonenumber,TotalCount,ramdomidOldorder;
    public dataitemoldoderatadmin(){}

    public dataitemoldoderatadmin(String foodname, String phonenumber, String totalCount, String ramdomidOldorder) {
        Foodname = foodname;
        Phonenumber = phonenumber;
        TotalCount = totalCount;
        this.ramdomidOldorder = ramdomidOldorder;
    }

    public String getFoodname() {
        return Foodname;
    }

    public void setFoodname(String foodname) {
        Foodname = foodname;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }

    public String getRamdomidOldorder() {
        return ramdomidOldorder;
    }

    public void setRamdomidOldorder(String ramdomidOldorder) {
        this.ramdomidOldorder = ramdomidOldorder;
    }
}
