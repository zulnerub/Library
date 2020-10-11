package enums;

/**
 * Enumeration with a parameter simpleName to represent the Genres of a book.
 */
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

    /**
     * @return String representation of the name of the genre.
     */
    public String getSimpleName() {
        return simpleName;
    }
}
