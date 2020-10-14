package model.book;

import enums.BookTags;
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
    private BookGenre genre;
    private List<Author> authors;
    private List<BookTags> tags;

    public Book(String ISBN, String title, String summary,
                List<Author> authors,
                BookGenre genre,
                List<BookTags> tags) {
        this.ISBN = ISBN;
        this.title = title;
        this.summary = summary;
        this.authors = authors;
        this.genre = genre;
        this.tags = tags;
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
    public List<String> getBookTags() {
        return tags.stream()
                .map(BookTags::name)
                .collect(Collectors.toList());
    }

    /**
     * @return Gets the books genre.
     */
    public BookGenre getGenre() {
        return genre;
    }

    /**
     * @return A list of the book's authors.
     */
    public List<Author> getAuthors() {
        return authors;
    }
}
