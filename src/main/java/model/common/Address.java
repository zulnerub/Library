package model.common;

public class Address {
    private String country;
    private String city;
    private String street;

    public Address(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }

    public String getFullAddress(){
        return street + ", " + city + ", " + country;
    }

    public String getShortAddress(){
        return street;
    }

    public boolean isAddressValid(){
        return isCountryValid() && isCityValid() && isStreetValid();
    }

    private boolean isStreetValid() {
        return street != null && !street.isBlank();
    }

    private boolean isCityValid() {
        return city != null && !city.isBlank();
    }

    private boolean isCountryValid() {
        return country != null && !country.isBlank();
    }
}
