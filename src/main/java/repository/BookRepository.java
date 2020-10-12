package repository;

import model.book.Book;
import model.book.impl.PaperBook;

import java.time.LocalDate;
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
    private Map<String, Map<String, LocalDate[]>> usersWithBorrowedBooks = new HashMap<>();
    private Map<String, List<String>> offeredBooks = new HashMap<>();

    public BookRepository() {
    }

    /**
     * Provides checks for the validity of the claim - user has offered books, user has exact offered book,
     * and returns the corresponding message.
     *
     * @param username Unique identifier of the user.
     * @param ISBN     Unique identifier of the book.
     * @return Message explayning the actions taken/not taken.
     */
    public String borrowBook(String username, String ISBN) {
        if (offeredBooks.containsKey(username)) {
            if (offeredBooks.get(username).contains(ISBN)) {
                registerBorrowedBook(username, ISBN);
                offeredBooks.get(username).remove(ISBN);
                ((PaperBook)books.get(ISBN)).getUsersInQueue().remove(username);
                return "User " + username + " have successfully borrowed book with ISBN number: " + ISBN;
            }
            return "There is no book with ISBN " + ISBN + " offered to user " + username + ".";
        }
        return "User " + username + " has no offered books yet.";
    }

    /**
     * Searches if the book exists and if it is available and provides a corresponding message.
     *
     * @param username Unique identifier of the user.
     * @param ISBN     Unique identifier of the book.
     * @return If found and available the book is saved
     */
    public String requestBook(String username, String ISBN) {
        if (books.get(ISBN) instanceof PaperBook) {
            int currentlyAvailableCopies = ((PaperBook) books.get(ISBN)).amountOfFreeCopies();

            if (currentlyAvailableCopies > 0) {
                ((PaperBook) books.get(ISBN)).changeAmountOfFreeCopies(-1);

                offeredBooks.putIfAbsent(username, new ArrayList<>());
                offeredBooks.get(username).add(ISBN);

                return "You are first in line and there is available book in stock. " +
                        "You have 3 days to borrow the book.";
            } else {
                ((PaperBook) books.get(ISBN)).getUsersInQueue().add(username);
                return "You are " + (((PaperBook) books.get(ISBN)).getUsersInQueue().size() + 1)
                        + " in line for that book.";
            }
        }

        return "The provided ISBN is not correct or the book associated with the ISBN is not physical.";
    }


    /**
     * Receives the username and the book he wants to postpone the return of.
     * Based on the executed operation returns a message to inform of the applied action.
     *
     * @param username Unique identifier of the user.
     * @param ISBN     Unique identifier of the book
     * @return Message stating the action taken.
     */
    public String postponeDueDate(String username, String ISBN) {
        if (usersWithBorrowedBooks.containsKey(username)) {
            if (usersWithBorrowedBooks.get(username).containsKey(ISBN)) {
                LocalDate rentedOn = usersWithBorrowedBooks.get(username).get(ISBN)[0];
                LocalDate dueDate = usersWithBorrowedBooks.get(username).get(ISBN)[1];

                if (!dueDate.plusDays(1).isAfter(rentedOn.plusDays(28))) {
                    usersWithBorrowedBooks.get(username).get(ISBN)[1] = dueDate.plusDays(1);
                    return "Due date postponed to: " + dueDate.plusDays(1).toString();
                }
                return "You have already postponed your due date with 14 days. " +
                        "The date will not be postponed.";
            }

            return "You have no book to return with ISBN " + ISBN;
        }
        return "User " + username + " has no borrowed books.";
    }

    /**
     * Seeks the records of a user and if has any, Searches for the book with the provided isbn
     * and if found gets the due date when the book has to be returned to the library.
     *
     * @param username Unique identifier for the user.
     * @param ISBN     Unique identifier for the book.
     * @return Due date for the searched book of the user or null if nothing is found.
     */
    public LocalDate dueDateForPaperBook(String username, String ISBN) {
        if (usersWithBorrowedBooks.containsKey(username)) {
            if (usersWithBorrowedBooks.get(username).containsKey(ISBN)) {
                return usersWithBorrowedBooks.get(username).get(ISBN)[1];
            }
        }

        return null;
    }

    /**
     * Registers the user if he has no borrowed books currently and registers on his account
     * the borrowed book with records for the date of borrowing and due date for the return.
     *
     * @param username Unique identifier of the user.
     * @param ISBN     Unique identifier of the book.
     */
    public void registerBorrowedBook(String username, String ISBN) {
        usersWithBorrowedBooks.putIfAbsent(username, new HashMap<>());
        usersWithBorrowedBooks.get(username)
                .put(ISBN, new LocalDate[]{
                        LocalDate.now(), LocalDate.now().plusDays(14)
                });
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
