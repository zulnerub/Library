package controller;

import enums.BookCategory;
import enums.BookGenre;
import model.book.impl.DownloadableEBook;
import model.book.impl.EBook;
import model.book.impl.PaperBook;
import model.user.impl.Author;
import repository.BookRepository;

import java.util.ArrayDeque;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Creates connection between the user and the book repository.
 * Provides validation for the books before added to the library.
 * Operates on the book repository.
 */
public class BookController {
    private final BookRepository bookRepository = new BookRepository();

    public BookController() {
    }

    /**
     * Validates the provided input by calling methods to validate each parameter separately.
     * If all are valid creates an DownloadableEBook and adds it to the repository.
     *
     * @param bookISBN       String representation of unique book identifier.
     * @param bookTitle      Name of the book.
     * @param summary        Short info about what the book is about.
     * @param authors        A list of one or more authors.
     * @param bookGenres     A list of one or more book genres.
     * @param bookCategories A list of one or more book categories.
     * @param readLink       String representation of the link where the book can be read.
     * @param downloadLink   String representing the link where the book can be downloaded from.
     * @return Message describing whether the operation was successful or not.
     */
    public String addDownloadableEBook(String bookISBN, String bookTitle, String summary,
                                       List<Author> authors, List<BookGenre> bookGenres, List<BookCategory> bookCategories,
                                       String readLink, String downloadLink) {
        if (isBookValid(bookISBN, bookTitle, summary, authors, bookGenres, bookCategories) && isLinkValid(readLink) && isLinkValid(downloadLink)) {
            DownloadableEBook newDownloadableEBook = new DownloadableEBook(bookISBN, bookTitle, summary, authors, bookGenres, bookCategories,
                    readLink, downloadLink);

            bookRepository.addBookToLibrary(newDownloadableEBook);

            return "Book: " + bookTitle + " added successfully to library.";
        }

        return "Adding book failed.";
    }

    /**
     * Validates the provided input by calling methods to validate each parameter separately.
     * If all are valid creates an EBook and adds it to the repository.
     *
     * @param bookISBN       String representation of unique book identifier.
     * @param bookTitle      Name of the book.
     * @param summary        Short info about what the book is about.
     * @param authors        A list of one or more authors.
     * @param bookGenres     A list of one or more book genres.
     * @param bookCategories A list of one or more book categories.
     * @param readLink       String representation of the link where the book can be read.
     * @return Message describing whether the operation was successful or not.
     */
    public String addEBook(String bookISBN, String bookTitle, String summary,
                           List<Author> authors, List<BookGenre> bookGenres, List<BookCategory> bookCategories,
                           String readLink) {
        if (isBookValid(bookISBN, bookTitle, summary, authors, bookGenres, bookCategories) && isLinkValid(readLink)) {
            EBook newEBook = new EBook(bookISBN, bookTitle, summary, authors, bookGenres, bookCategories,
                    readLink);

            bookRepository.addBookToLibrary(newEBook);

            return "Book: " + bookTitle + " added successfully to library.";
        }

        return "Adding book failed.";
    }

    /**
     * Validates the link with a regular expression for links.
     *
     * @param bookLink String representation of a link.
     * @return true if the link matches the regexp or false if doesn't.
     */
    private boolean isLinkValid(String bookLink) {
        return Pattern.matches("^(http)(s)*://(www.)*([a-z0-9]+.)+[a-z]+(:[0-9]{1,4})*", bookLink);
    }

    /**
     * Validates the provided input for creating a paper book if data is valid
     * adds the book to the library and returns a success message, otherwise
     * - failure message.
     *
     * @param bookISBN       String representation of unique book identifier.
     * @param bookTitle      Name of the book.
     * @param summary        Short info about what the book is about.
     * @param authors        A list of one or more authors.
     * @param bookGenres     A list of one or more book genres.
     * @param bookCategories A list of one or more book categories.
     * @param totalCopies    Amount of copies added to the library.
     * @return Message saying the book was added on success or saying that the process has failed.
     */
    public String addPaperBook(String bookISBN, String bookTitle, String summary,
                               List<Author> authors, List<BookGenre> bookGenres, List<BookCategory> bookCategories,
                               int totalCopies) {
        if (isBookValid(bookISBN, bookTitle, summary, authors, bookGenres, bookCategories) && isTotalCopiesValid(totalCopies)) {
            PaperBook newPaperBook = new PaperBook(bookISBN, bookTitle, summary, authors, bookGenres, bookCategories,
                    totalCopies, totalCopies, true, new ArrayDeque<>());

            bookRepository.addBookToLibrary(newPaperBook);

            return "Book: " + bookTitle + " added successfully to library.";
        }

        return "Adding book failed.";
    }

    /**
     * Validates that the book to be added has at least one copy.
     *
     * @param totalCopies Represents the initial copies of the book when added to the library. Must be greater than one.
     * @return true if input's value is greater than zero, otherwise - false.
     */
    private boolean isTotalCopiesValid(int totalCopies) {
        return totalCopies > 0;
    }

    /**
     * Calls methods to validate separately each of the parameters and
     * returns an error message whe the first invalid method is found.
     *
     * @param bookISBN       Unique book identification.
     * @param bookTitle      String representation of the title of the book.
     * @param summary        Short info describing the book.
     * @param authors        List of objects of type Author with length at least one.
     * @param bookGenres     List of objects of type BookGenre with length at least one.
     * @param bookCategories List of objects of type BookCategory with length at least one.
     * @return true if all parameters are valid, otherwise on the first invalid operator
     * prints error message and returns false.
     */
    private boolean isBookValid(String bookISBN, String bookTitle, String summary,
                                List<Author> authors, List<BookGenre> bookGenres, List<BookCategory> bookCategories) {
        if (!isISBNValid(bookISBN)) {
            System.out.println(("The provided ISBN was not valid. " +
                    "The ISBN should consist of 5 numbers and a '-' symbol in the format '####-#'."));
            return false;
        }

        if (!isTitleValid(bookTitle)) {
            System.out.println("The provided book title is not valid. " +
                    "The title should be at least 3 symbols long.");
            return false;
        }

        if (!isSummaryValid(summary)) {
            System.out.println("The provided summary is not valid. " +
                    "The summary must be at least 50 symbols in length.");
            return false;
        }

        if (!areAuthorsValid(authors)) {
            System.out.println("The provided author/authors is/are not valid. " +
                    "Please provide at least one, valid author to add a book to the library.");
            return false;
        }

        if (!areBookGenresValid(bookGenres)) {
            System.out.println("The provided genre/s of the book are not valid. " +
                    "Please provide at least one valid book genre.");
            return false;
        }

        if (!areBookCategoriesValid(bookCategories)) {
            System.out.println("The provided book category/ies are not valid. " +
                    "Please provide at least one valid category to add a book to the library.");
            return false;
        }

        return true;
    }

    /**
     * Validates the provided categories for the book to be added.
     *
     * @param bookCategories Should be list of objects of type BookCategory with length at least 1.
     * @return true if provided list exists and has at least one category in it, otherwise - false.
     */
    private boolean areBookCategoriesValid(List<BookCategory> bookCategories) {
        return bookCategories != null && !bookCategories.isEmpty();
    }

    /**
     * Validates the provided list of BookGenres.
     *
     * @param bookGenres Should be A list with objects of type BookGenre.
     * @return true if there is at least one genre in the list, otherwise - false.
     */
    private boolean areBookGenresValid(List<BookGenre> bookGenres) {
        return bookGenres != null && !bookGenres.isEmpty();
    }

    /**
     * Validates the provided list of authors.
     *
     * @param authors List of objects of type Author with length at least 1.
     * @return true if list is not null and not empty, otherwise - false.
     */
    private boolean areAuthorsValid(List<Author> authors) {
        return authors != null && !authors.isEmpty();
    }

    /**
     * Validates the provided input for summary.
     *
     * @param summary Should be a string longer than 50 symbols.
     * @return true if the provided string is valid or false if not.
     */
    private boolean isSummaryValid(String summary) {
        return isStringValid(summary) && summary.length() > 50;
    }

    /**
     * Validates the provided  input if it is a valid title.
     *
     * @param bookTittle Should be a string longer than 3 symbols.
     * @return true if valid, otherwise - false
     */
    private boolean isTitleValid(String bookTittle) {
        return isStringValid(bookTittle) && bookTittle.length() > 3;
    }

    /**
     * Validates the provided ISBN for the new book.
     *
     * @param bookISBN String representation of the ISBN - should be in format "####-#".
     * @return true if input is valid otherwise -false.
     */
    private boolean isISBNValid(String bookISBN) {
        boolean result = false;

        if (isStringValid(bookISBN) && bookISBN.trim().length() == 6
                && bookISBN.trim().charAt(4) == '-') {
            result = true;

            for (int i = 0; i < 6; i++) {
                if (i != 4) {
                    if (!Character.isDigit(bookISBN.trim().charAt(i))) {
                        result = false;
                    }
                }
            }
        }

        boolean ISBNAlreadyExists = bookRepository
                .getAllBooksInLibrary()
                .stream()
                .anyMatch(book -> book.getISBN().equals(bookISBN.trim()));

        if (ISBNAlreadyExists) {
            result = false;
        }

        return result;
    }

    /**
     * Validates the provided input.
     *
     * @param strToValidate Should be a string.
     * @return true if the provided input is not null and not blank, otherwise returns false.
     */
    private boolean isStringValid(String strToValidate) {
        return strToValidate != null && !strToValidate.isBlank();
    }
}