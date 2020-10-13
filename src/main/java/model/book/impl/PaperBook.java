package model.book.impl;

import enums.BookCategory;
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
                     List<BookGenre> genres,
                     List<BookCategory> categories,
                     int currentlyAvailable, int totalCopies) {
        super(ISBN, title, summary, authors, genres, categories);
        this.currentlyAvailable = currentlyAvailable;
        this.totalCopies = totalCopies;
    }

    /**
     * @return the current amount of available copies of the book.
     */
    public int freeCopies() {
        return currentlyAvailable;
    }

    /**
     * Removes one copy from the available copies whe book is offered or rented.
     */
    public void removeOneCopyFromLibrary() {
        currentlyAvailable -= 1;
    }

    /**
     * Makes one more copy available when a copy is returned by the user.
     */
    public void addOneCopyFromLibrary() {
        currentlyAvailable += 1;
    }
}
