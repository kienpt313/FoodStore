package CustomerFoodPanel;

public class Customer {
    private String Firstname,Lastname,Password,ConfirmPassword,Email,Mbnumber,City,District,Address;
    public Customer(){}

    public Customer(String firstname, String lastname, String password, String confirmPassword, String email, String mbnumber, String city, String district, String address) {
        Firstname = firstname;
        Lastname = lastname;
        Password = password;
        ConfirmPassword = confirmPassword;
        Email = email;
        Mbnumber = mbnumber;
        City = city;
        District = district;
        Address = address;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMbnumber() {
        return Mbnumber;
    }

    public void setMbnumber(String mbnumber) {
        Mbnumber = mbnumber;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
