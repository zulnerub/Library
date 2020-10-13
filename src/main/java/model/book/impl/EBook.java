package model.book.impl;

import enums.BookCategory;
import enums.BookGenre;
import model.book.Book;
import model.book.Electronic;
import model.user.impl.Author;

import java.util.List;

/**
 * Object that represents an online book with possibility to be read online.
 */
public class EBook extends Book implements Electronic {

    private String onlineLink;

    public EBook(String ISBN, String title, String summary,
                 List<Author> authors,
                 List<BookGenre> genres,
                 List<BookCategory> categories,
                 String onlineLink) {
        super(ISBN, title, summary, authors, genres, categories);
        this.onlineLink = onlineLink;
    }

    /**
     * @return String representation of the book's link for online reading.
     */
    @Override
    public String getOnlineLink() {
        return onlineLink;
    }
}
