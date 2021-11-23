package CustomerFoodPanel;

public class DataOderFood {
    private String ImageURL;
    private String Food;
    private String Price;
    private String RandomUID;
    private String Description;
    private String AdminID,Address,City,Districts;


    public DataOderFood() {
    }

    public DataOderFood(String imageURL, String food, String price, String randomUID, String description, String adminID, String address, String city, String districts) {
        ImageURL = imageURL;
        Food = food;
        Price = price;
        RandomUID = randomUID;
        Description = description;
        AdminID = adminID;
        Address = address;
        City = city;
        Districts = districts;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getFood() {
        return Food;
    }

    public void setFood(String food) {
        Food = food;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAdminID() {
        return AdminID;
    }

    public void setAdminID(String adminID) {
        AdminID = adminID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDistricts() {
        return Districts;
    }

    public void setDistricts(String districts) {
        Districts = districts;
    }
}

