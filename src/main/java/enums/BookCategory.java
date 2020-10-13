package enums;

/**
 * Enumeration with parameter simpleName to represent the Categories of a book.
 */
public enum BookCategory {

    CHILDREN("children"),
    STORY("story"),
    LEARNING("learning"),
    HOBBY("hobby");

    String simpleName;

    BookCategory(String categoryName) {
        simpleName = categoryName;
    }

    /**
     * @return String representation of the name of the category.
     */
    public String getSimpleName() {
        return simpleName;
    }
}
