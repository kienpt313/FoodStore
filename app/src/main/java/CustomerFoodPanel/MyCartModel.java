package CustomerFoodPanel;

public class MyCartModel {
    String NameFood,Price,TotalCount,CurrentDate,CurrentTime,RamdomFoodOderID,AdminID;

    public MyCartModel(){}

    public MyCartModel(String nameFood, String price, String totalCount, String currentDate, String currentTime, String ramdomFoodOderID, String adminID) {
        NameFood = nameFood;
        Price = price;
        TotalCount = totalCount;
        CurrentDate = currentDate;
        CurrentTime = currentTime;
        RamdomFoodOderID = ramdomFoodOderID;
        AdminID = adminID;
    }

    public String getNameFood() {
        return NameFood;
    }

    public void setNameFood(String nameFood) {
        NameFood = nameFood;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        CurrentDate = currentDate;
    }

    public String getCurrentTime() {
        return CurrentTime;
    }

    public void setCurrentTime(String currentTime) {
        CurrentTime = currentTime;
    }

    public String getRamdomFoodOderID() {
        return RamdomFoodOderID;
    }

    public void setRamdomFoodOderID(String ramdomFoodOderID) {
        RamdomFoodOderID = ramdomFoodOderID;
    }

    public String getAdminID() {
        return AdminID;
    }

    public void setAdminID(String adminID) {
        AdminID = adminID;
    }
}

