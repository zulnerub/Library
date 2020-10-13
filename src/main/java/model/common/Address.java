package model.common;

/**
 * This is an object containing parameters of a real address - street, city, country.
 */
public class Address {

    private String country;
    private String city;
    private String street;

    public Address(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }

    /**
     * @return Retrieves the fields of the class formatted to a String showing the full address.
     */
    public String getFullAddress() {
        return street + ", " + city + ", " + country;
    }

    /**
     * @return Retrieves the street field to show only a part of the address.
     */
    public String getShortAddress() {
        return street;
    }

    /**
     * @return true if all of the address fields are valid otherwise - false.
     */
    public boolean isAddressValid() {
        return isCountryValid() && isCityValid() && isStreetValid();
    }

    /**
     * @return true if street is not null, empty or blank, otherwise - false.
     */
    private boolean isStreetValid() {
        return street != null && !street.isBlank();
    }

    /**
     * @return true if city is not null, empty or blank, otherwise - false.
     */
    private boolean isCityValid() {
        return city != null && !city.isBlank();
    }

    /**
     * @return true if country is not null, empty or blank, otherwise - false.
     */
    private boolean isCountryValid() {
        return country != null && !country.isBlank();
    }
}
