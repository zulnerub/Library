package model.book.impl;

import enums.BookTags;
import enums.BookGenre;
import model.book.Book;
import model.user.impl.Author;

import java.util.List;

/**
 * An Object that emulates a paper book, containing number of copies in stock.
 * And other attributes characteristic for a real book like - isbn, author/s, title, summary etc.
 */
public class PaperBook extends Book {

    private int currentlyAvailable;
    private int totalCopies;

    public PaperBook(String ISBN, String title, String summary,
                     List<Author> authors,
                     BookGenre genre,
                     List<BookTags> categories,
                     int currentlyAvailable, int totalCopies) {
        super(ISBN, title, summary, authors, genre, categories);
        this.currentlyAvailable = currentlyAvailable;
        this.totalCopies = totalCopies;
    }

    /**
     * @return Get the value of the books free at the moment.
     */
    public int getCurrentlyAvailable() {
        return currentlyAvailable;
    }

    /**
     * Sets the modified amount of copies of the book after borrowing or returning it.
     *
     * @param currentlyAvailable New amount of free copies after borrow or return operations.
     */
    public void setCurrentlyAvailable(int currentlyAvailable) {
        this.currentlyAvailable = currentlyAvailable;
    }
}
