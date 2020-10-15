package repository;

import exception.CustomException;
import model.book.Book;
import model.book.impl.DownloadableEBook;
import model.book.impl.EBook;
import model.book.impl.PaperBook;
import model.common.UserRegistryForm;
import model.user.impl.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static enums.BookGenre.FANTASY;
import static enums.BookGenre.SCI_FI;
import static enums.BookTags.*;
import static enums.BookTags.LEARNING;
import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryTests {

    UserRepository userRepository = new UserRepository();
    BookRepository bookRepository = new BookRepository(userRepository);

    LocalDate georgeBirthDate = LocalDate.of(1965, 1, 1);
    LocalDate joanBirthDate = LocalDate.of(1972, 3, 22);

    Author georgeMartin = new Author("George", "Martin", georgeBirthDate, null);
    Author joanRolling = new Author("Joan", "Rolling", joanBirthDate, null);

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

    @DisplayName("call method getDueDate on collection of forms with valid existing username" +
            "but valid not existing ISBN to simulate not found input")
    @Test
    void getDueDate_ShouldThrowCustomException_NoRecordFoundForISBN(){
        //Given
        String username = "validUsername";
        String bookISBN = "1111-1";
        LocalDate startDate = LocalDate.of(2000,1,1);

        UserRegistryForm sampleValidForm = new UserRegistryForm(username, bookISBN, startDate, 29);

        Map<String, List<UserRegistryForm>> formCollection = new LinkedHashMap<>();
        formCollection.put(username, List.of(sampleValidForm));

        String expectedMessage = "No such book for this user.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.getDueDate(username, "0000-0", formCollection));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("call method getDueDate on collection of forms with valid not existing username to simulate not found input")
    @Test
    void getDueDate_ShouldThrowCustomException_NoRecordFoundForUsername(){
        //Given
        String username = "validUsername";
        String bookISBN = "1111-1";
        LocalDate startDate = LocalDate.of(2000,1,1);

        UserRegistryForm sampleValidForm = new UserRegistryForm(username, bookISBN, startDate, 29);

        Map<String, List<UserRegistryForm>> formCollection = new LinkedHashMap<>();
        formCollection.put(username, List.of(sampleValidForm));

        String expectedMessage = "Username has no records.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.getDueDate("wrongUsername", bookISBN, formCollection));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("call method getDueDate on collection of forms with username = blank string")
    @Test
    void getDueDate_ShouldThrowCustomException_InvalidInput_FormCollectionEmpty(){
        //Given
        String username = "validUsername";
        String bookISBN = "1111-1";

        Map<String, List<UserRegistryForm>> formCollection = new LinkedHashMap<>();

        String expectedMessage = "Provided form collection is missing or empty.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.getDueDate(username, bookISBN, formCollection));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("call method getDueDate on collection of forms with username = blank string")
    @Test
    void getDueDate_ShouldThrowCustomException_InvalidInput_FormCollectionNull(){
        //Given
        String username = "validUsername";
        String bookISBN = "1111-1";

        String expectedMessage = "Provided form collection is missing or empty.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.getDueDate(username, bookISBN, null));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("call method getDueDate on collection of forms with username = blank string")
    @Test
    void getDueDate_ShouldThrowCustomException_InvalidInput_UsernameBlankString(){
        //Given
        String username = "validUsername";
        String bookISBN = "1111-1";
        LocalDate startDate = LocalDate.of(2000,1,1);

        UserRegistryForm sampleValidForm = new UserRegistryForm(username, bookISBN, startDate, 29);

        Map<String, List<UserRegistryForm>> formCollection = new LinkedHashMap<>();
        formCollection.put(username, List.of(sampleValidForm));

        String expectedMessage = "Username is not valid.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.getDueDate("  ", "1111-1", formCollection));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("call method getDueDate on collection of forms with username = empty string")
    @Test
    void getDueDate_ShouldThrowCustomException_InvalidInput_UsernameEmptyString(){
        //Given
        String username = "validUsername";
        String bookISBN = "1111-1";
        LocalDate startDate = LocalDate.of(2000,1,1);

        UserRegistryForm sampleValidForm = new UserRegistryForm(username, bookISBN, startDate, 29);

        Map<String, List<UserRegistryForm>> formCollection = new LinkedHashMap<>();
        formCollection.put(username, List.of(sampleValidForm));

        String expectedMessage = "Username is not valid.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.getDueDate("", "1111-1", formCollection));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("call method getDueDate on collection of forms with username = null")
    @Test
    void getDueDate_ShouldThrowCustomException_InvalidInput_UsernameNull(){
        //Given
        String username = "validUsername";
        String bookISBN = "1111-1";
        LocalDate startDate = LocalDate.of(2000,1,1);

        UserRegistryForm sampleValidForm = new UserRegistryForm(username, bookISBN, startDate, 29);

        Map<String, List<UserRegistryForm>> formCollection = new LinkedHashMap<>();
        formCollection.put(username, List.of(sampleValidForm));

        String expectedMessage = "Username is not valid.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.getDueDate(null, "1111-1", formCollection));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("when provided with null method freeCopies() should throw exception")
    @Test
    void freeCopies_ShouldThrowCustomException_Input_Null(){
        //Given
        String expectedMessage = "Must provide an object of type PaperBook.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookRepository.freeCopies(null));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("check if returns correct amount of paper book copies in the library")
    @Test
    void freeCopies_ShouldBe_Five(){
        //When
        bookRepository.addBookToLibrary(gameOfThrones);

        //Then
        assertEquals(5, bookRepository.freeCopies(gameOfThrones));
    }

    @DisplayName("add one book to library and check if size of library is one")
    @Test
    void getAllBooksInLibrary_ListShouldBeWithSizeOne(){
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
