package repository;

import model.book.Book;
import model.book.impl.PaperBook;
import model.common.UserRegistryForm;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Has the role of containing all the books in the library and execute simple operations like:
 * - adding book;
 * - getting all books
 * - etc.
 */
public class BookRepository {

    public static final int INITIAL_BORROW_TIME = 14;
    public static final int DAYS_TO_BORROW_BOOK = 3;
    private Map<String, Book> books = new HashMap<>();
    private Map<String, List<UserRegistryForm>> borrowedBooks = new HashMap<>();
    private Map<String, List<UserRegistryForm>> offeredBooks = new HashMap<>();
    private Map<Integer, UserRegistryForm> requestedBooks = new LinkedHashMap<>();
    private Set<String> bannedUsers = new HashSet<>();
    private LocalDate currentDate = LocalDate.now();
    private int requestIndex = 0;

    /**
     * Checks if the user has offered books and if the provided book ISBN
     * is offered to him. If  book ISBN is found the book is borrowed by the user and the
     * corresponding message is returned.
     *
     * @param username Unique identifier of the user.
     * @param ISBN     Unique identifier of the book.
     * @return Message to indicate what was the executed action.
     */
    public String borrowBook(String username, String ISBN) {

        if (bannedUsers.contains(username)) {
            return "User " + username + " is banned form the library for delayed books!";
        }

        if (!offeredBooks.containsKey(username)) {
            return "User " + username + " has no offered books yet.";
        }

        UserRegistryForm offerForm = offeredBooks.get(username).stream()
                .filter(form -> form.getISBN().equals(ISBN))
                .findFirst()
                .orElse(null);

        if (offerForm == null) {
            return "There is no book with ISBN " + ISBN + " offered to user " + username + ".";
        }

        UserRegistryForm borrowForm = new UserRegistryForm(username, ISBN, currentDate, INITIAL_BORROW_TIME);

        borrowedBooks.putIfAbsent(username, new ArrayList<>());
        borrowedBooks.get(username).add(borrowForm);

        offeredBooks.get(username).removeIf(offeredBook -> offeredBook.getISBN().equals(ISBN));

        return "User " + username + " have successfully borrowed book with ISBN number: " + ISBN;

    }

    /**
     * Checks if the provided ISBN is assigned to a PaperBook
     * and whether there are available copies of that book.
     * If all are true a request for a book is created.
     *
     * @param username Unique identifier of the user.
     * @param ISBN     Unique identifier of the book.
     * @return Message corresponding to the applied action.
     */
    public String requestBook(String username, String ISBN) {

        if (isNotPaperBook(ISBN)) {
            return "The provided ISBN is not correct or the book associated with the ISBN is not physical.";
        }

        PaperBook currentBook = ((PaperBook) books.get(ISBN));

        int availableCopies = freeCopies(currentBook);

        if (availableCopies > 0) {
            removeOneCopyFromLibrary(currentBook);

            offeredBooks.putIfAbsent(username, new ArrayList<>());
            offeredBooks.get(username).add(new UserRegistryForm(username, ISBN, currentDate, DAYS_TO_BORROW_BOOK));

            return "You are first in line and there is available book in stock. " +
                    "You have 3 days to borrow the book.";

        }

        requestedBooks.put(++requestIndex, new UserRegistryForm(username, ISBN));

        return "You are " + getPlaceInQueue(username, ISBN)
                + " in line for that book.";

    }

    /**
     * Checks if the user has borrowed books and has borrowed given book
     * with ISBN. Also checks if it is possible to extend the due date.
     *
     * @param username Unique identifier of the user.
     * @param ISBN     Unique identifier of book.
     * @return Message corresponding to the action taken or the result of the checks.
     */
    public String postponeDueDate(String username, String ISBN) {
        if (!borrowedBooks.containsKey(username)) {
            return "User " + username + " has no borrowed books.";
        }

        UserRegistryForm userRegistryForm = borrowedBooks.get(username).stream()
                .filter(borrowForm -> borrowForm.getISBN().equals(ISBN))
                .findFirst()
                .orElse(null);

        if (userRegistryForm == null) {
            return "You have no book to return with ISBN " + ISBN;
        }

        LocalDate rentedOn = userRegistryForm.getStartDate();
        LocalDate dueDate = userRegistryForm.getEndDate();

        if (dueDate.plusDays(1).isAfter(rentedOn.plusDays(28))) {
            return "You have already postponed your due date with 14 days. " +
                    "The date will not be postponed.";
        }

        userRegistryForm.extendDueDate(1);

        return "Due date postponed to: " + userRegistryForm.getEndDate().toString();

    }

    /**
     * Searches if the provided user has rented a book with the provided ISBN.
     * If a form describing such action is found the book is
     * then returned to the library and a proper message is returned.
     * Else message for failure is returned.
     *
     * @param username Unique user identifier.
     * @param ISBN     Unique book identifier.
     * @return Message for the action taken. Book has been removed or not.
     */
    public String returnBookToLibrary(String username, String ISBN) {

        UserRegistryForm userBorrowForm = borrowedBooks.get(username).stream()
                .filter(borrowForm -> borrowForm.getISBN().equals(ISBN))
                .findFirst()
                .orElse(null);

        if (userBorrowForm == null) {
            return "The provided user doesn't exist or has not borrowed any book with that ISBN.";
        }

        addOneCopyToLibrary(((PaperBook) books.get(userBorrowForm.getISBN())));

        borrowedBooks.get(username).remove(userBorrowForm);

        return "Book successfully returned to the library";
    }

    /**
     * Finds a borrow form by given username and ISBN and
     * if found gets the due date.
     *
     * @param username Unique user identifier.
     * @param ISBN     Unique book identifier.
     * @return The due date of the form or null if not found.
     */
    public LocalDate getDueDate(String username, String ISBN, Map<String, List<UserRegistryForm>> formCollection) {

        UserRegistryForm userBorrowForm = formCollection.get(username).stream()
                .filter(borrowForm -> borrowForm.getISBN().equals(ISBN))
                .findFirst()
                .orElse(null);

        return userBorrowForm != null ? userBorrowForm.getEndDate() : null;
    }

    /**
     * Calculates the place of the user in the queue for given book.
     *
     * @param username Unique identifier of the user.
     * @param ISBN     Unique identifier of the book.
     * @return Place in queue for the user - int.
     */
    public int getPlaceInQueue(String username, String ISBN) {
        List<UserRegistryForm> list = requestedBooks.values().stream()
                .filter(form -> form.getISBN().equals(ISBN))
                .collect(Collectors.toList());

        UserRegistryForm userRegistryForm = list.stream()
                .filter(f -> f.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        return list.indexOf(userRegistryForm);
    }

    /**
     * @param ISBN Unique identifier of book.
     * @return true if given book is not a PaperBook or
     * false if is a PaperBook.
     */
    private boolean isNotPaperBook(String ISBN) {
        return !(books.get(ISBN) instanceof PaperBook);
    }

    /**
     * Receive an object of type Book and add it to the library
     * if the object is not null.
     *
     * @param book Object of type book.
     */
    public void addBookToLibrary(Book book) {
        if (book != null) {
            books.putIfAbsent(book.getISBN(), book);
        }
    }

    /**
     * @return Extracts all books form the library in a new ArrayList.
     */
    public List<Book> getAllBooksInLibrary() {
        return new ArrayList<>(books.values());
    }

    /**
     * Simulates the passing of one day. By increasing the current day with one day.
     * <p>
     * After changing the date calls the three methods for syncing the libraries records.
     * - Banning user for passed due dates on books.
     * - Making available books not borrowed by the users after being offered to them 3 days ago.
     * - Offering books to users who are in line if a book is made available and if they are not in the banned list.
     */
    private void changeDay() {
        currentDate = LocalDate.now().plusDays(1);

        syncBorrowedBooks();
        syncOfferedBooks();
        syncRequestedBooks();
    }

    private void syncBorrowedBooks() {

        borrowedBooks.forEach((username, borrowedForms) -> borrowedForms.forEach(form -> {

            if (form.getEndDate().isAfter(currentDate)) {

                bannedUsers.add(username);
            }
        }));
    }

    /**
     * This method iterates over the offers and if the date for borrowing the book is passed
     * and the book is still not borrowed, deletes the offer
     * and makes the copy of the book available in the library again.
     */
    private void syncOfferedBooks() {

        offeredBooks.forEach((username, userOffers) -> userOffers.forEach(offeredBook -> {

            if (offeredBook.getEndDate().isAfter(currentDate)) {

                addOneCopyToLibrary(((PaperBook) books.get(offeredBook.getISBN())));

                offeredBooks.get(username).remove(offeredBook);
            }
        }));
    }

    /**
     * Iterates through the requested books and if a copy of a requested book is
     * present offer it to the next user in queue.
     */
    private void syncRequestedBooks() {

        requestedBooks.forEach((index, requestForm) -> {

            PaperBook requestedBook = ((PaperBook) books.get(requestForm.getISBN()));

            String username = requestForm.getUsername();
            String bookISBN = requestForm.getISBN();

            if (freeCopies(requestedBook) > 0) {
                UserRegistryForm offerForm = new UserRegistryForm(username, bookISBN, currentDate, DAYS_TO_BORROW_BOOK);

                offeredBooks.putIfAbsent(username, new ArrayList<>());
                offeredBooks.get(username).add(offerForm);

                requestedBooks.remove(index);
            }
        });
    }

    /**
     * @return the current amount of available copies of the book.
     */
    public int freeCopies(PaperBook paperBook) {
        return paperBook.getCurrentlyAvailable();
    }

    /**
     * Removes one copy from the available copies whe book is offered or rented.
     */
    private void removeOneCopyFromLibrary(PaperBook paperBook) {
        paperBook.setCurrentlyAvailable(
                paperBook.getCurrentlyAvailable() - 1
        );
    }

    /**
     * Makes one more copy available when a copy is returned by the user.
     */
    public void addOneCopyToLibrary(PaperBook paperBook) {
        paperBook.setCurrentlyAvailable(
                paperBook.getCurrentlyAvailable() + 1
        );
    }
}
