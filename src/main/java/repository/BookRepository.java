package repository;

import model.book.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Has the role of containing all the books in the library and execute simple operations like:
 * - adding book;
 * - getting all books
 * - etc.
 */
public class BookRepository {
    private final Map<String, Book> books = new HashMap<>();

    public BookRepository() {
    }

    /**
     * Receive an object of type Book and add it to the library.
     *
     * @param book Object of type book.
     */
    public void addBookToLibrary(Book book) {
        books.putIfAbsent(book.getISBN(), book);
    }

    /**
     * @return Extracts all books form the library in a new ArrayList.
     */
    public List<Book> getAllBooksInLibrary() {
        return new ArrayList<>(books.values());
    }
}
