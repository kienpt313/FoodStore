package com.example.cookingrecipegood.AdminfoodPanel;

public class FoodDetails {
    public String Food,Quantity,Price,Description,ImageURL,RandomUID,AdminID,City,Districts,Address;

    public FoodDetails(String food, String quantity, String price, String description, String imageURL, String randomUID, String adminID, String city, String districts, String address) {
        Food = food;
        Quantity = quantity;
        Price = price;
        Description = description;
        ImageURL = imageURL;
        RandomUID = randomUID;
        AdminID = adminID;
        City = city;
        Districts = districts;
        Address = address;
    }
}
