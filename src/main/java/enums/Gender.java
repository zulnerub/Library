package enums;

/**
 * Enumeration with simple name to represent the genders for the users.
 */
public enum Gender {

    MALE("m"),
    FEMALE("f");

    String shortName;

    Gender(String shortName) {
        this.shortName = shortName;
    }

    /**
     * @return String representation of the genre short name.
     */
    public String getShortName() {
        return shortName;
    }
}
