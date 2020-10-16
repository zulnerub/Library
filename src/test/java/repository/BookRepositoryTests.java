package repository;

import exception.CustomException;
import model.book.impl.DownloadableEBook;
import model.book.impl.EBook;
import model.book.impl.PaperBook;
import model.common.Address;
import model.common.UserRegistryForm;
import model.user.impl.Author;
import model.user.impl.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static enums.BookGenre.*;
import static enums.BookTags.*;
import static enums.BookTags.LEARNING;
import static enums.Gender.MALE;
import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryTests {

    UserRepository userRepository = new UserRepository();
    BookRepository bookRepository = new BookRepository(userRepository);

    LocalDate georgeBirthDate = LocalDate.of(1965, 1, 1);
    LocalDate joanBirthDate = LocalDate.of(1972, 3, 22);

    Author georgeMartin = new Author("George", "Martin", georgeBirthDate, null);
    Author joanRolling = new Author("Joan", "Rolling", joanBirthDate, null);

    Address userAddress = new Address("Bulgaria", "Haskovo", "Orfej 16");

    User validUser = new User("Simeon", "Atanasov", userAddress, MALE,
            "validUsername", "validPassword", "valid@email.address",
            true, 33);

    PaperBook gameOfThrones = new PaperBook(
            "1234-5",
            "Game of thrones",
            "Very interesting book about internal and  external royal family affairs.",
            Collections.singletonList(georgeMartin),
            FANTASY,
            Arrays.asList(STORY, HOBBY),
            5,
            5);

    EBook harryPotter = new EBook(
            "1234-6",
            "Harry Potter",
            "A book about magic and magicians. For kids of all ages - small or big.",
            Collections.singletonList(joanRolling),
            FANTASY,
            Arrays.asList(STORY, CHILDREN),
            "http://harrypotter.online.read.com");

    DownloadableEBook dayEarthStoodStill = new DownloadableEBook(
            "1234-7",
            "The day the earth stood still",
            "A book about aliens and post apocaliptic world scenarious.",
            Collections.singletonList(joanRolling),
            SCI_FI,
            Collections.singletonList(LEARNING),
            "http://earthstood.online.read.com",
            "http://stillearth.online.download.com");

    @DisplayName("testing method removeBookForThisPenalty - " +
            "should return a message that the username has no borrowed books or " +
            "that the user has not borrowed this book. For book not borrowed by user.")
    @Test
    void returnBookToLibrary_ShouldReturnMessageBookNotFound_InputBookNotBorrowed(){
        //Given
        bookRepository.addBookToLibrary(gameOfThrones);
        bookRepository.addBookToLibrary(harryPotter);
        userRepository.addUser(validUser);

        String username = validUser.getUsername();;
        String bookISBN = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookISBN);
        bookRepository.borrowBook(username, bookISBN);

        String inputISBN = harryPotter.getISBN();

        String expectedMessage = "The provided user doesn't exist or has not borrowed any book with that ISBN.";

        //When
        String actualMessage = bookRepository.returnBookToLibrary(username, inputISBN);

        //Then
        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("testing method removeBookForThisPenalty - " +
            "should return a message that the username has no borrowed books or " +
            "that the user has not borrowed this book. For user having no records.")
    @Test
    void returnBookToLibrary_ShouldReturnMessageBookNotFound_InputUserHasNoRecords(){
        //Given
        bookRepository.addBookToLibrary(gameOfThrones);
        userRepository.addUser(validUser);

        String username = validUser.getUsername();;
        String bookISBN = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookISBN);

        String expectedMessage = "The provided user doesn't exist or has not borrowed any book with that ISBN.";

        //When
        String actualMessage = bookRepository.returnBookToLibrary(username, bookISBN);

        //Then
        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("testing method returnBookToLibrary - " +
            "should add a copy of the returned book to the library")
    @Test
    void returnBookToLibrary_ShouldAddOneCopyOfBookToLibrary(){
        //Given
        bookRepository.addBookToLibrary(gameOfThrones);
        userRepository.addUser(validUser);

        String username = validUser.getUsername();;
        String bookISBN = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookISBN);
        bookRepository.borrowBook(username, bookISBN);

        PaperBook returnedBook = (PaperBook) bookRepository.getAllBooksInLibrary().stream()
                .filter(book -> book.getISBN().equals(bookISBN))
                .findFirst().orElse(null);

        int expectedSize = 5;

        //When
        bookRepository.returnBookToLibrary(username, bookISBN);

        //Then
        assertEquals(expectedSize, bookRepository.freeCopies(returnedBook));
    }

    @DisplayName("testing method removeBookForThisPenalty - should remove record of borrowed book")
    @Test
    void returnBookToLibrary_ShouldRemoveBorrowRecord(){
        //Given
        bookRepository.addBookToLibrary(gameOfThrones);
        userRepository.addUser(validUser);

        String username = validUser.getUsername();;
        String bookISBN = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookISBN);
        bookRepository.borrowBook(username, bookISBN);

        int expectedSize = 0;

        //When
        bookRepository.returnBookToLibrary(username, bookISBN);

        //Then
        assertEquals(expectedSize, bookRepository.getBorrowedBooksSize());
    }

    @DisplayName("testing method removeBookForThisPenalty - should Print message for remove penalty")
    @Test
    void removeBanForThisPenalty_ShouldReturnMessageIndicatingPenaltyWasRemoved(){
        //Given
        bookRepository.addBookToLibrary(gameOfThrones);
        userRepository.addUser(validUser);

        String username = validUser.getUsername();;
        String bookISBN = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookISBN);
        bookRepository.borrowBook(username, bookISBN);

        bookRepository.changeDay(15);

        String expectedMessage = "Book successfully returned to the library." +
                "\nYou had penalty for this book. Penalty removed.";

        //When
        String actualMessage = bookRepository.returnBookToLibrary(username, bookISBN);

        //Then
        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("testing method returnBookToLibrary - should return success message with valid input")
    @Test
    void returnBookToLibrary_ShouldReturnMessageForSuccessfullyReturningBook(){
        //Given
        bookRepository.addBookToLibrary(gameOfThrones);
        userRepository.addUser(validUser);

        String username = validUser.getUsername();;
        String bookISBN = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookISBN);
        bookRepository.borrowBook(username, bookISBN);

        String expectedMessage = "Book successfully returned to the library.";

        //When
        String actualMessage = bookRepository.returnBookToLibrary(username, bookISBN);

        //Then
        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("testing method postponeDueDate - should return correct message for user not having any books to return")
    @Test
    void postponeDueDate_ShouldReturnMessageNoBooksToReturnForThatUser(){
        //Given
        bookRepository.addBookToLibrary(gameOfThrones);
        userRepository.addUser(validUser);

        String username = validUser.getUsername();
        String bookISBN = gameOfThrones.getISBN();

        String expectedMessage = "User " + username + " has no borrowed books.";

        //When
        String actualMessage = bookRepository.postponeDueDateWithSevenDays(username, bookISBN);

        //Then
        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("testing method postponeDueDate - should return correct message for not finding" +
            "book with that isbn for the user to return.")
    @Test
    void postponeDueDate_ShouldReturnMessageNoBookWithThisISBNToReturn(){
        //Given
        bookRepository.addBookToLibrary(gameOfThrones);
        bookRepository.addBookToLibrary(harryPotter);
        userRepository.addUser(validUser);

        String username = validUser.getUsername();
        String bookISBN = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookISBN);
        bookRepository.borrowBook(username, bookISBN);

        String inputISBN = harryPotter.getISBN();

        String expectedMessage = "You have no book to return with ISBN " + inputISBN;

        //When
        String actualMessage = bookRepository.postponeDueDateWithSevenDays(username, inputISBN);

        //Then
        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("testing method postponeDueDate - should return correct message for refused postponement")
    @Test
    void postponeDueDate_ShouldReturnMessageAlreadyPostponedToLimit_ValidInput(){
        //Given
        bookRepository.addBookToLibrary(gameOfThrones);
        userRepository.addUser(validUser);

        String username = validUser.getUsername();
        String bookISBN = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookISBN);
        bookRepository.borrowBook(username, bookISBN);

        bookRepository.postponeDueDateWithSevenDays(username, bookISBN);
        bookRepository.postponeDueDateWithSevenDays(username, bookISBN);

        String expectedMessage = "You have already postponed your due date with 14 days. " +
                "The date will not be postponed.";

        //When
        String actualMessage = bookRepository.postponeDueDateWithSevenDays(username, bookISBN);

        //Then
        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("testing method postponeDueDate - should return correct message with the new date")
    @Test
    void postponeDueDate_ShouldReturnSuccessfulMessage_ValidInput(){
        //Given
        bookRepository.addBookToLibrary(gameOfThrones);
        userRepository.addUser(validUser);

        String username = validUser.getUsername();
        String bookISBN = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookISBN);
        bookRepository.borrowBook(username, bookISBN);

        String expectedMessage = "Due date postponed to: " + LocalDate.now().plusDays(21).toString();

        //When
        String actualMessage = bookRepository.postponeDueDateWithSevenDays(username, bookISBN);

        //Then
        assertEquals(expectedMessage, actualMessage);

    }

    @DisplayName("method addBookToUserHistory should return empty list.")
    @Test
    void addBookToUserHistory_ShouldReturnEmptyListOfBooks() {
        //Given
        int expectedHistorySize = 0;

        //When
        //Then
        assertEquals(expectedHistorySize, validUser.getHistory().getUsedBooks().size());
    }

    @DisplayName("method addBookToUserHistory should return a list of one book.")
    @Test
    void addBookToUserHistory_ShouldReturnListOfBooks_SizeOne_ForValidInput() {
        //Given
        userRepository.addUser(validUser);
        bookRepository.addBookToLibrary(gameOfThrones);

        String username = validUser.getUsername();
        String bookIsbn = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookIsbn);

        int expectedHistorySize = 1;
        PaperBook expectedBook = gameOfThrones;

        //When
        bookRepository.borrowBook(username, bookIsbn);

        //Then
        assertEquals(expectedBook, validUser.getHistory().getUsedBooks().get(0));
        assertEquals(expectedHistorySize, validUser.getHistory().getUsedBooks().size());
    }

    @DisplayName("method borrowBook should return book not offered to user message")
    @Test
    void borrowBook_ShouldReturnMessageBookNotOfferedToUser_ForValidInput() {
        //Given
        userRepository.addUser(validUser);
        bookRepository.addBookToLibrary(gameOfThrones);
        bookRepository.addBookToLibrary(harryPotter);

        String username = validUser.getUsername();
        String bookIsbn = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookIsbn);

        String expectedMessage = "There is no book with ISBN 1234-6 offered to user " + username + ".";

        //When
        String actualMessage = bookRepository.borrowBook(username, "1234-6");

        //Then
        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("method borrowBook should return no offers for user message")
    @Test
    void borrowBook_ShouldReturnMessageUserHasNoOffers_ForValidInput() {
        //Given
        userRepository.addUser(validUser);
        bookRepository.addBookToLibrary(gameOfThrones);

        String username = validUser.getUsername();
        String bookIsbn = gameOfThrones.getISBN();

        String expectedMessage = "User "+username +" has no offered books yet.";

        //When
        String actualMessage = bookRepository.borrowBook(username, bookIsbn);

        //Then
        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("method borrowBook should should throw exception for ISBN - valid but not matching offered book")
    @Test
    void borrowBook_ShouldThrowCustomException_ValidISBN_NotMatchingOfferedBook() {
        //Given
        userRepository.addUser(validUser);
        bookRepository.addBookToLibrary(gameOfThrones);

        String username = validUser.getUsername();
        String bookIsbn = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookIsbn);

        String expectedMessage = "No paper book with this ISBN exist in the library.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.borrowBook(username, "0000-0"));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("method borrowBook should should throw exception for ISBN - not matching pattern ####-# (digits only)")
    @Test
    void borrowBook_ShouldThrowCustomException_InvalidISBN_NotMatchingPattern() {
        //Given
        userRepository.addUser(validUser);
        bookRepository.addBookToLibrary(gameOfThrones);

        String username = validUser.getUsername();
        String bookIsbn = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookIsbn);

        String expectedMessage = "Provided ISBN does not match pattern ####-# (digits only)";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.borrowBook(username, "asd4asd5"));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("method borrowBook should should throw exception for ISBN - containing letters")
    @Test
    void borrowBook_ShouldThrowCustomException_InvalidISBN_NotOnlyDigits() {
        //Given
        userRepository.addUser(validUser);
        bookRepository.addBookToLibrary(gameOfThrones);

        String username = validUser.getUsername();
        String bookIsbn = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookIsbn);

        String expectedMessage = "Provided ISBN does not match pattern ####-# (digits only)";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.borrowBook(username, "asd4-5"));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("method borrowBook should should throw exception for ISBN - null")
    @Test
    void borrowBook_ShouldThrowCustomException_InvalidISBN_Null() {
        //Given
        userRepository.addUser(validUser);
        bookRepository.addBookToLibrary(gameOfThrones);

        String username = validUser.getUsername();
        String bookIsbn = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookIsbn);

        String expectedMessage = "ISBN is missing or not valid.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.borrowBook(username, null));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("method borrowBook should should throw exception for ISBN empty string")
    @Test
    void borrowBook_ShouldThrowCustomException_InvalidISBN_EmptyString() {
        //Given
        userRepository.addUser(validUser);
        bookRepository.addBookToLibrary(gameOfThrones);

        String username = validUser.getUsername();
        String bookIsbn = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookIsbn);

        String expectedMessage = "ISBN is missing or not valid.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.borrowBook(username, ""));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("method borrowBook should should throw exception for ISBN blank string")
    @Test
    void borrowBook_ShouldThrowCustomException_InvalidISBN_BlankString() {
        //Given
        userRepository.addUser(validUser);
        bookRepository.addBookToLibrary(gameOfThrones);

        String username = validUser.getUsername();
        String bookIsbn = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookIsbn);

        String expectedMessage = "ISBN is missing or not valid.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.borrowBook(username, "  "));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("method borrowBook should should throw exception for username blank string")
    @Test
    void borrowBook_ShouldThrowCustomException_InvalidUsername_BlankString() {
        //Given
        userRepository.addUser(validUser);
        bookRepository.addBookToLibrary(gameOfThrones);

        String username = validUser.getUsername();
        String bookIsbn = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookIsbn);

        String expectedMessage = "Provided string for username is not valid.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.borrowBook("   ", bookIsbn));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("method borrowBook should should throw exception for username empty string")
    @Test
    void borrowBook_ShouldThrowCustomException_InvalidUsername_EmptyString() {
        //Given
        userRepository.addUser(validUser);
        bookRepository.addBookToLibrary(gameOfThrones);

        String username = validUser.getUsername();
        String bookIsbn = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookIsbn);

        String expectedMessage = "Provided string for username is not valid.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.borrowBook("", bookIsbn));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("method borrowBook should should throw exception for username null")
    @Test
    void borrowBook_ShouldThrowCustomException_InvalidUsername_Null() {
        //Given
        userRepository.addUser(validUser);
        bookRepository.addBookToLibrary(gameOfThrones);

        String username = validUser.getUsername();
        String bookIsbn = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookIsbn);

        String expectedMessage = "Provided string for username is not valid.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.borrowBook(null, bookIsbn));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("method borrowBook should return success message for valid input.")
    @Test
    void borrowBook_ShouldReturnSuccessMessage_ForValidInput() {
        //Given
        userRepository.addUser(validUser);
        bookRepository.addBookToLibrary(gameOfThrones);

        String username = validUser.getUsername();
        String bookIsbn = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookIsbn);

        String expectedMessage = "User " + username + " have successfully borrowed book with ISBN number: " + bookIsbn;

        //When
        String actualMessage = bookRepository.borrowBook(username, bookIsbn);

        //Then
        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("request a book from the repository with valid input and check if copies are decreased by one " +
            "for completed request.")
    @Test
    void removeOneCopyFromLibrary_ShouldDecreaseBookCopiesByOne_CompleteBookRequest() {
        //Given
        userRepository.addUser(validUser);
        bookRepository.addBookToLibrary(gameOfThrones);

        String username = validUser.getUsername();
        String bookISBN = gameOfThrones.getISBN();

        int currentBookCopies = gameOfThrones.getCurrentlyAvailable();

        //When
        bookRepository.requestBook(username, bookISBN);

        //Then
        assertEquals(currentBookCopies - 1, gameOfThrones.getCurrentlyAvailable());
    }

    @DisplayName("method requestBook should return user is banned message for valid input and overdue book.")
    @Test
    void requestBook_ShouldReturnMessageUserIsBanned_ForValidInput_BookOvedue() {
        //Given
        userRepository.addUser(validUser);
        bookRepository.addBookToLibrary(gameOfThrones);

        String username = validUser.getUsername();
        String bookIsbn = gameOfThrones.getISBN();

        bookRepository.requestBook(username, bookIsbn);
        bookRepository.borrowBook(username, bookIsbn);

        bookRepository.changeDay(15);

        String expectedMessage = "User " + username + " is banned form the library for delayed books!";

        //When
        String actualMessage = bookRepository.requestBook(username, bookIsbn);

        //Then
        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("request a book from the repository with invalid Isbn - null - Throws CustomException")
    @Test
    void requestBook_ShouldThrowCustomException_Input_ISBNNull() {
        //Given
        gameOfThrones.setCurrentlyAvailable(0);
        bookRepository.addBookToLibrary(gameOfThrones);

        userRepository.addUser(validUser);
        String username = validUser.getUsername();

        String expectedMessage = "ISBN is missing or not valid.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.requestBook(username, null));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("request a book from the repository with invalid ISBN containing letters - Throws CustomException")
    @Test
    void requestBook_ShouldThrowCustomException_Input_ISBNContainingLetters() {
        //Given
        gameOfThrones.setCurrentlyAvailable(0);
        bookRepository.addBookToLibrary(gameOfThrones);

        userRepository.addUser(validUser);

        String username = validUser.getUsername();
        String bookISBN = "lk12-3";

        String expectedMessage = "Provided ISBN does not match pattern ####-# (digits only)";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.requestBook(username, bookISBN));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("request a book from the repository with invalid ISBN pattern - Throws CustomException")
    @Test
    void requestBook_ShouldThrowCustomException_Input_ISBNInvalidPattern() {
        //Given
        gameOfThrones.setCurrentlyAvailable(0);
        bookRepository.addBookToLibrary(gameOfThrones);

        userRepository.addUser(validUser);

        String username = validUser.getUsername();
        String bookISBN = "111283";

        String expectedMessage = "Provided ISBN does not match pattern ####-# (digits only)";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.requestBook(username, bookISBN));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("request a book from the repository with valid input and get success message for" +
            "pending request.")
    @Test
    void requestBook_ShouldReturnMessageFor_PendingBookRequest() {
        //Given
        gameOfThrones.setCurrentlyAvailable(0);
        bookRepository.addBookToLibrary(gameOfThrones);

        userRepository.addUser(validUser);

        String username = validUser.getUsername();
        String bookISBN = gameOfThrones.getISBN();

        String expectedMessage = "You are 1 in line for that book.\n" +
                "Estimated date the book will become available: " + LocalDate.now().plusDays(21).toString();

        //When
        String actualMessage = bookRepository.requestBook(username, bookISBN);

        //Then
        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("request a book from the repository with valid input and get success message " +
            "for completed request.")
    @Test
    void requestBook_ShouldReturnMessageFor_CompleteBookRequest() {
        //Given
        bookRepository.addBookToLibrary(gameOfThrones);
        userRepository.addUser(validUser);

        String username = "validUsername";
        String bookISBN = "1234-5";

        String expectedMessage = "You are first in line and there is available book in stock. " +
                "You have 3 days to borrow the book.";

        //When
        String actualMessage = bookRepository.requestBook(username, bookISBN);

        //Then
        assertEquals(expectedMessage, actualMessage);
    }

    @DisplayName("call method getDueDate on collection of forms with valid existing username" +
            "but valid not existing ISBN to simulate not found input")
    @Test
    void getDueDate_ShouldThrowCustomException_NoRecordFoundForISBN() {
        //Given
        bookRepository.addBookToLibrary(gameOfThrones);
        bookRepository.addBookToLibrary(harryPotter);
        userRepository.addUser(validUser);

        String bookISBN = gameOfThrones.getISBN();
        String username = validUser.getUsername();

        LocalDate startDate = LocalDate.of(2000, 1, 1);

        UserRegistryForm sampleValidForm = new UserRegistryForm(username, bookISBN, startDate, 29);

        Map<String, List<UserRegistryForm>> formCollection = new LinkedHashMap<>();
        formCollection.put(username, List.of(sampleValidForm));

        String inputIsbn = harryPotter.getISBN();

        String expectedMessage = "No such book for this user.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.getDueDate(username, inputIsbn, formCollection));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("call method getDueDate on collection of forms with valid not existing username to simulate not found input")
    @Test
    void getDueDate_ShouldThrowCustomException_NoRecordFoundForUsername() {
        //Given
        bookRepository.addBookToLibrary(gameOfThrones);
        userRepository.addUser(validUser);

        String bookISBN = gameOfThrones.getISBN();
        String username = validUser.getUsername();

        LocalDate startDate = LocalDate.of(2000, 1, 1);

        UserRegistryForm sampleValidForm = new UserRegistryForm(username, bookISBN, startDate, 29);

        Map<String, List<UserRegistryForm>> formCollection = new LinkedHashMap<>();
        formCollection.put(username, List.of(sampleValidForm));

        String expectedMessage = "Provided username is less than 3 symbols or user don't exists.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.getDueDate("wrongUsername", bookISBN, formCollection));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("call method getDueDate on collection of forms - forms is empty list")
    @Test
    void getDueDate_ShouldThrowCustomException_InvalidInput_FormCollectionEmpty() {
        //Given
        bookRepository.addBookToLibrary(gameOfThrones);
        userRepository.addUser(validUser);

        String username = validUser.getUsername();
        String bookISBN = gameOfThrones.getISBN();

        Map<String, List<UserRegistryForm>> formCollection = new LinkedHashMap<>();

        String expectedMessage = "Provided form collection is missing or empty.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.getDueDate(username, bookISBN, formCollection));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("call method getDueDate on collection of forms with username = blank string")
    @Test
    void getDueDate_ShouldThrowCustomException_InvalidInput_FormCollectionNull() {
        //Given
        bookRepository.addBookToLibrary(gameOfThrones);
        userRepository.addUser(validUser);

        String username = validUser.getUsername();
        String bookISBN = gameOfThrones.getISBN();

        String expectedMessage = "Provided form collection is missing or empty.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.getDueDate(username, bookISBN, null));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("call method getDueDate on collection of forms with username = blank string")
    @Test
    void getDueDate_ShouldThrowCustomException_InvalidInput_UsernameBlankString() {
        //Given
        String username = "validUsername";
        String bookISBN = "1111-1";
        LocalDate startDate = LocalDate.of(2000, 1, 1);

        UserRegistryForm sampleValidForm = new UserRegistryForm(username, bookISBN, startDate, 29);

        Map<String, List<UserRegistryForm>> formCollection = new LinkedHashMap<>();
        formCollection.put(username, List.of(sampleValidForm));

        String expectedMessage = "Provided string for username is not valid.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.getDueDate("  ", "1111-1", formCollection));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("call method getDueDate on collection of forms with username = empty string")
    @Test
    void getDueDate_ShouldThrowCustomException_InvalidInput_UsernameEmptyString() {
        //Given
        String username = "validUsername";
        String bookISBN = "1111-1";
        LocalDate startDate = LocalDate.of(2000, 1, 1);

        UserRegistryForm sampleValidForm = new UserRegistryForm(username, bookISBN, startDate, 29);

        Map<String, List<UserRegistryForm>> formCollection = new LinkedHashMap<>();
        formCollection.put(username, List.of(sampleValidForm));

        String expectedMessage = "Provided string for username is not valid.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.getDueDate("", "1111-1", formCollection));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("call method getDueDate on collection of forms with username = null")
    @Test
    void getDueDate_ShouldThrowCustomException_InvalidInput_UsernameNull() {
        //Given
        String username = "validUsername";
        String bookISBN = "1111-1";
        LocalDate startDate = LocalDate.of(2000, 1, 1);

        UserRegistryForm sampleValidForm = new UserRegistryForm(username, bookISBN, startDate, 29);

        Map<String, List<UserRegistryForm>> formCollection = new LinkedHashMap<>();
        formCollection.put(username, List.of(sampleValidForm));

        String expectedMessage = "Provided string for username is not valid.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.getDueDate(null, "1111-1", formCollection));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("when provided with null method freeCopies() should throw exception")
    @Test
    void freeCopies_ShouldThrowCustomException_Input_Null() {
        //Given
        String expectedMessage = "Must provide an object of type PaperBook.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.freeCopies(null));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("check if returns correct amount of paper book copies in the library")
    @Test
    void freeCopies_ShouldBe_Five() {
        //When
        bookRepository.addBookToLibrary(gameOfThrones);

        //Then
        assertEquals(5, bookRepository.freeCopies(gameOfThrones));
    }

    @DisplayName("add one book to library and check if size of library is one")
    @Test
    void getAllBooksInLibrary_ListShouldBeWithSizeOne() {
        //When
        bookRepository.addBookToLibrary(gameOfThrones);

        //Then
        assertEquals(1, bookRepository.getAllBooksInLibrary().size());
    }

    @DisplayName("Testing if the add methods adds one of each three types of books.")
    @Test
    void addBookToLibrary_ShouldAddThreeBooksToLibrary_Input_PaperBook_EBook_DownloadableEBook() {
        // When
        bookRepository.addBookToLibrary(gameOfThrones);
        bookRepository.addBookToLibrary(harryPotter);
        bookRepository.addBookToLibrary(dayEarthStoodStill);

        //Then
        assertEquals(3, bookRepository.getAllBooksInLibrary().size());
    }

    @DisplayName("addBook method throws exception for null input")
    @Test
    void addBookToLibrary_ShouldThrowCustomException_Input_Null() {
        //Given
        String expectedMessage = "Book can not be null. Library takes only books.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.addBookToLibrary(null));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }
}
