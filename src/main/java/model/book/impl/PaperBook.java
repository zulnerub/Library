package model.book.impl;

import enums.BookCategory;
import enums.BookGenre;
import model.book.Book;
import model.user.impl.Author;
import model.user.impl.User;

import java.util.List;
import java.util.Queue;

/**
 * An Object that emulates a paper book, containing number of copies in stock.
 * And other atributes characteristic for a real book like - isbn, author/s, title, summary etc.
 */
public class PaperBook extends Book {
    private int currentlyAvailable;
    private int totalCopies;
    private boolean isInStock;
    private Queue<User> offeredCopiesToUsers;


    public PaperBook(String ISBN, String title, String summary,
                     List<Author> authors,
                     List<BookGenre> genres,
                     List<BookCategory> categories,
                     int currentlyAvailable, int totalCopies,
                     boolean isInStock, Queue<User> offeredCopiesToUsers) {
        super(ISBN, title, summary, authors, genres, categories);
        this.currentlyAvailable = currentlyAvailable;
        this.totalCopies = totalCopies;
        this.isInStock = isInStock;
        this.offeredCopiesToUsers = offeredCopiesToUsers;
    }
}