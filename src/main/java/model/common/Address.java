package model.common;

public class Address {
    private String country;
    private String city;
    private String address;

    public Address(String country, String city, String address) {
        this.country = country;
        this.city = city;
        this.address = address;
    }

    public String getFullAddress(){
        return address + ", " + city + ", " + country;
    }

    public String getShortAddress(){
        return address;
    }
}
