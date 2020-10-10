package enums;

public enum BookCategory {
    CHILDREN("children"),
    STORY("story"),
    LEARNING("learning"),
    HOBBY("hobby");

    String simpleName;

    BookCategory(String categoryName) {
        simpleName = categoryName;
    }

    public String getSimpleName() {
        return simpleName;
    }
}
