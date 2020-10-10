package enums;

public enum BookGenre {
    DRAMA("drama"),
    HORROR("horror"),
    SCI_FI("sci_fi"),
    SCIENCE("science"),
    FANTASY("fantasy");

    String simpleName;

    BookGenre(String genreName) {
        simpleName = genreName;
    }

    public String getSimpleName() {
        return simpleName;
    }
}
