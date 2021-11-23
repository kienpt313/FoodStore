package com.example.cookingrecipegood.AdminfoodPanel;

public class DataPendingOrderAdmin {
    private String TotalCount,NameFood,CustomerID,RamdomFoodOderID;
    public DataPendingOrderAdmin(){}

    public DataPendingOrderAdmin(String totalCount, String nameFood, String customerID, String ramdomFoodOderID) {
        TotalCount = totalCount;
        NameFood = nameFood;
        CustomerID = customerID;
        RamdomFoodOderID = ramdomFoodOderID;
    }

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }

    public String getNameFood() {
        return NameFood;
    }

    public void setNameFood(String nameFood) {
        NameFood = nameFood;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getRamdomFoodOderID() {
        return RamdomFoodOderID;
    }

    public void setRamdomFoodOderID(String ramdomFoodOderID) {
        RamdomFoodOderID = ramdomFoodOderID;
    }
}
