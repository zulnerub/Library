package enums;

public enum Gender {
    MALE("m"),
    FEMALE("f");

    String shortName;

    Gender(String shortName) {
        this.shortName = shortName;
    }
}
