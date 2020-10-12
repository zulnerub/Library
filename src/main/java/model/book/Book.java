package model.book;

import enums.BookCategory;
import enums.BookGenre;
import model.user.impl.Author;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract class to provide partial implementation for PaperBook, EBook, DownloadableEBook.
 * Contains common methods for all three classes.
 */
public abstract class Book {
    private String ISBN;
    private String title;
    private String summary;
    private List<Author> authors;
    private List<BookGenre> genres;
    private List<BookCategory> categories;

    public Book(String ISBN, String title, String summary,
                List<Author> authors,
                List<BookGenre> genres,
                List<BookCategory> categories) {
        this.ISBN = ISBN;
        this.title = title;
        this.summary = summary;
        this.authors = authors;
        this.genres = genres;
        this.categories = categories;
    }

    /**
     * @return Get the unique identifier of a book - ISBN.
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * @return The title of the Book - String.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return A list of the book's category names.
     */
    public List<String> getCategoriesNames() {
        return categories.stream()
                .map(BookCategory::getSimpleName)
                .collect(Collectors.toList());
    }

    /**
     * @return A list of the book's genre names.
     */
    public List<String> getGenreNames() {
        return genres.stream()
                .map(BookGenre::getSimpleName)
                .collect(Collectors.toList());
    }

    /**
     * @return A list of the book's authors.
     */
    public List<Author> getAuthors() {
        return authors;
    }
}
