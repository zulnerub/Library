package controller;

import exception.CustomException;
import model.book.Book;
import model.user.impl.Author;
import org.junit.jupiter.api.*;
import repository.UserRepository;

import java.time.LocalDate;
import java.util.*;

import static enums.BookGenre.*;
import static enums.BookTags.*;
import static org.junit.jupiter.api.Assertions.*;

public class BookControllerTests {

    UserRepository userRepository = new UserRepository();
    BookController bookController = new BookController(userRepository);


    @BeforeEach
    void init() {
        LocalDate ld = LocalDate.of(1965, 1, 1);
        LocalDate sLd = LocalDate.of(1972, 3, 22);

        Author georgeMartin = new Author("George", "Martin", ld, null);
        Author joanRolling = new Author("Joan", "Rolling", sLd, null);

        bookController.addPaperBook(
                "1234-5",
                "Game of thrones",
                "Very interesting book about internal and  external royal family affairs.",
                Collections.singletonList(georgeMartin),
                FANTASY,
                Arrays.asList(STORY, HOBBY),
                5);

        bookController.addEBook(
                "1234-6",
                "Harry Potter",
                "A book about magic and magicians. For kids of all ages - small or big.",
                Collections.singletonList(joanRolling),
                FANTASY,
                Arrays.asList(STORY, CHILDREN),
                "http://harrypotter.online.read.com");

        bookController.addDownloadableEBook(
                "1234-7",
                "The day the earth stood still",
                "A book about aliens and post apocaliptic world scenarious.",
                Collections.singletonList(joanRolling),
                SCI_FI,
                Collections.singletonList(LEARNING),
                "http://earthstood.online.read.com",
                "http://stillearth.online.download.com");

    }

    @DisplayName("Search for book by author first name - blank string.")
    @Test
    void searchByFirstName_ShouldThrowCustomException_ForInput_BlankString() {
        //Given
        String expectedMessage = "String input can't be null, empty or only white spaces.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookController.searchBookByAuthorsFirstName("   "));

        //Then
        Assertions.assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Search for book by author first name - empty string.")
    @Test
    void searchByFirstName_ShouldThrowCustomException_ForInput_EmptyString() {
        //Given
        String expectedMessage = "String input can't be null, empty or only white spaces.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookController.searchBookByAuthorsFirstName(""));

        //Then
        Assertions.assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName(("Search for book by author first name - null"))
    @Test
    void searchByFirstName_ShouldThrowCustomException_ForInput_Null() {
        //Given
        String expectedMessage = "String input can't be null, empty or only white spaces.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookController.searchBookByAuthorsFirstName(null));

        //Then
        Assertions.assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Search for book by author first name - 'George'")
    @Test
    void searchByFirstName_ShouldReturn_OneBook_ForInput_George() {
        //When
        List<Book> result = bookController.searchBookByAuthorsFirstName("George");
        String authorName = result.get(0).getAuthors().get(0).getFirstName();

        //Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("George", authorName);
    }

    @DisplayName("Search for book by first name - 'o'")
    @Test
    void searchByFirstName_ShouldReturn_ThreeBooks_ForInput_O() {
        //When
        List<Book> result = bookController.searchBookByAuthorsFirstName("o");
        String authorNameBookOne = result.get(0).getAuthors().get(0).getFirstName();
        String authorNameBookTwo = result.get(1).getAuthors().get(0).getFirstName();
        String authorNameBookThree = result.get(2).getAuthors().get(0).getFirstName();

        //Then
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("George", authorNameBookOne);
        Assertions.assertEquals("Joan", authorNameBookTwo);
        Assertions.assertEquals("Joan", authorNameBookThree);
    }

    // last name test

    @DisplayName("Search for book by author last name - blank string")
    @Test
    void searchByLastName_ShouldThrowCustomException_ForInput_BlankString() {
        //Given
        String expectedMessage = "String input can't be null, empty or only white spaces.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookController.searchBookByAuthorsLastName("   "));

        //Then
        Assertions.assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Search for book by author last name - empty string")
    @Test
    void searchByLastName_ShouldThrowCustomException_ForInput_EmptyString() {
        //Given
        String expectedMessage = "String input can't be null, empty or only white spaces.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookController.searchBookByAuthorsLastName(""));

        //Then
        Assertions.assertTrue(expectedMessage.contains(exception.getMessage()));

    }

    @DisplayName("Search for book by author last name - null")
    @Test
    void searchByLastName_ThrowCustomException_ForInput_Null() {
        //Given
        String expectedMessage = "String input can't be null, empty or only white spaces.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookController.searchBookByAuthorsLastName(null));

        //Then
        Assertions.assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Search for book by author last name - 'Martin'")
    @Test
    void searchByLastName_ShouldReturn_OneBook_ForInput_Martin() {
        //When
        List<Book> result = bookController.searchBookByAuthorsLastName("Martin");
        String authorName = result.get(0).getAuthors().get(0).getLastName();

        //Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Martin", authorName);
    }

    @DisplayName("Search for book by author last name - 'i'")
    @Test
    void searchByLastName_ShouldReturn_ThreeBooks_ForInput_I() {
        //When
        List<Book> result = bookController.searchBookByAuthorsLastName("i");
        String authorNameBookOne = result.get(0).getAuthors().get(0).getLastName();
        String authorNameBookTwo = result.get(1).getAuthors().get(0).getLastName();
        String authorNameBookThree = result.get(2).getAuthors().get(0).getLastName();

        //Then
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("Martin", authorNameBookOne);
        Assertions.assertEquals("Rolling", authorNameBookTwo);
        Assertions.assertEquals("Rolling", authorNameBookThree);
    }

    // full  name test

    @DisplayName("Search for book by author full name - blank string")
    @Test
    void searchByFullName_ThrowCustomException_ForInput_BlankString() {
        //Given
        String expectedMessage = "String input can't be null, empty or only white spaces.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookController.searchBookByAuthorsFullName("   "));

        //Then
        Assertions.assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Search for book by author full name - empty string")
    @Test
    void searchByFullName_ShouldThrowCustomException_ForInput_EmptyString() {
        //Given
        String expectedMessage = "String input can't be null, empty or only white spaces.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookController.searchBookByAuthorsFullName(""));

        //Then
        Assertions.assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Search for book by author full name - null")
    @Test
    void searchByFullName_ShouldThrowCustomException_ForInput_Null() {
        //Given
        String expectedMessage = "String input can't be null, empty or only white spaces.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookController.searchBookByAuthorsFullName(null));

        //Then
        Assertions.assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Search for book by author full name - George Martin")
    @Test
    void searchByFullName_ShouldReturn_OneBook_ForInput_GeorgeMartin() {
        //When
        List<Book> result = bookController.searchBookByAuthorsFullName("George Martin");
        String authorName = result.get(0).getAuthors().get(0).getFullName();

        //Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("George Martin", authorName);
    }

    @DisplayName("Search for book by author full name - o") // change input
    @Test
    void searchByFullName_ShouldReturn_ThreeBooks_ForInput_O() {
        //When
        List<Book> result = bookController.searchBookByAuthorsFullName("o");
        String authorNameBookOne = result.get(0).getAuthors().get(0).getFullName();
        String authorNameBookTwo = result.get(1).getAuthors().get(0).getFullName();
        String authorNameBookThree = result.get(2).getAuthors().get(0).getFullName();

        //Then
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("George Martin", authorNameBookOne);
        Assertions.assertEquals("Joan Rolling", authorNameBookTwo);
        Assertions.assertEquals("Joan Rolling", authorNameBookThree);
    }

    //genre test

    @DisplayName("Search for book by genre name - sci_fi")
    @Test
    void searchByGenreName_ShouldReturnOneCorrectBook_ForInputCorrectGenre() {
        //When
        List<Book> result = bookController.searchByBookGenre("sci_fi");
        String bookIsbnExpected = "1234-7";

        //Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(bookIsbnExpected, result.get(0).getISBN());
    }

    @DisplayName("Search for book by genre name - drama")
    @Test
    void searchByGenreName_ShouldReturnEmptyList_ForInputCorrectGenre() {
        //When
        List<Book> result = bookController.searchByBookGenre("drama");

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by genre name - ma")
    @Test
    void searchByGenreName_ShouldReturnEmptyList_ForInputNotExistingGenre() {
        //When
        List<Book> result = bookController.searchByBookGenre("ma");

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by genre name - null")
    @Test
    void searchByGenreName_ShouldThrowCustomException_ForInputNull() {
        //Given
        String expectedMessage = "Please input name for the genre. Genre can't be null.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookController.searchByBookGenre(null));

        //Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Search for book by title name - empty string")
    @Test
    void searchByTitleName_ShouldThrowCustomException_ForInputEmptyString() {
        //Given
        String expectedMessage = "String input can't be null, empty or only white spaces.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookController.searchByBookTitle(""));

        //Then
        Assertions.assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Search for book by title name - blank string")
    @Test
    void searchByTitleName_ShouldThrowCustomException_ForInputBlankString() {
        //Given
        String expectedMessage = "String input can't be null, empty or only white spaces.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookController.searchByBookTitle("    "));

        //Then
        Assertions.assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Search for book by title name - null")
    @Test
    void searchByTitleName_ShouldThrowCustomException_ForInputNull() {
        //Given
        String expectedMessage = "String input can't be null, empty or only white spaces.";

        //When
        Exception exception = assertThrows(CustomException.class, () -> bookController.searchByBookTitle(null));

        //Then
        Assertions.assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Search for book by title name - Title not exists")
    @Test
    void searchByTitleName_ShouldReturnEmptyList_ForInputNotExistingTitle() {
        //When
        List<Book> result = bookController.searchByBookTitle("nogame of nothrones");

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by title name - existing exact title")
    @Test
    void searchByTitleName_ShouldReturnOneBook_ForInputExactTitleExisting() {
        //When
        List<Book> result = bookController.searchByBookTitle("Game of thrones");
        String titleResult = result.get(0).getTitle();

        //Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Game of thrones", titleResult);
    }

    @DisplayName("Search for book by title name - part of existing title")
    @Test
    void searchByTitleName_ShouldReturnOneBook_ForInputPartOfExistingTitle() {
        //When
        List<Book> result = bookController.searchByBookTitle("hrone");
        String titleResult = result.get(0).getTitle();

        //Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Game of thrones", titleResult);
    }

    // tags

    @DisplayName("Search for book by tag names - null")
    @Test
    void searchByTagNames_ShouldReturnEmptyList_ForInputNull() {
        //When
        List<Book> result = bookController.searchByBookTags(null);

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by tag names - empty string")
    @Test
    void searchByTagNames_ShouldReturnEmptyList_ForInputEmptyString() {
        //When
        List<Book> result = bookController.searchByBookTags("");

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by tag names - blank string")
    @Test
    void searchByTagNames_ShouldReturnEmptyList_ForInputBlankString() {
        //When
        List<Book> result = bookController.searchByBookTags("   ");

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by tag names - non existing tag")
    @Test
    void searchByTagNames_ShouldReturnEmptyList_ForInput_NonExistingTag() {
        //When
        List<Book> result = bookController.searchByBookTags("non existing tag");

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by tag names - learning")
    @Test
    void searchByTagNames_ShouldReturnOneBook_ForInput_LearningToLowerCase() {
        //When
        List<Book> result = bookController.searchByBookTags("learning");

        //Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.get(0).getBookTags().contains(LEARNING.name()));
    }

    @DisplayName("Search for book by tag names - learning")
    @Test
    void searchByTagNames_ShouldReturnOneBook_ForInput_LEARNINGToUpperCase() {
        //When
        List<Book> result = bookController.searchByBookTags("learning");

        //Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.get(0).getBookTags().contains(LEARNING.name()));
    }

    @DisplayName("Search for book by tag names - learning")
    @Test
    void searchByTagNames_ShouldReturnTwoBooks_ForInput_Story() {
        //When
        List<Book> result = bookController.searchByBookTags("story");

        //Then
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.get(0).getBookTags().contains(STORY.name()));
        Assertions.assertTrue(result.get(1).getBookTags().contains(STORY.name()));
    }

    // tests isStringValid method -> isISBNInvalid -> isBookValid -> addPaperBook

    @DisplayName("Inverted validation for a string literal, ISBN input - null")
    @Test
    void isStringInvalid_ShouldThrowCustomException_ForInput_NullISBN() {
        // Given
        String title = "Sample title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author georgeMartin = new Author("Sample", "Correct", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(georgeMartin);

        String expectedMessage = "ISBN can't be null.";

        // When
        Exception exception = assertThrows(CustomException.class,
                () -> bookController.addPaperBook(
                        null,
                        title,
                        summary,
                        authors,
                        HORROR,
                        List.of(STORY),
                        1));

        // Then
        assertTrue(expectedMessage.contains(exception.getMessage()));

    }

    @DisplayName("Inverted validation for a string literal, ISBN input - empty string")
    @Test
    void isStringInvalid_ShouldThrowCustomException_ForInput_ISBNEmptyString() {
        // Given
        String isbn = "";
        String title = "Sample title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author georgeMartin = new Author("Sample", "Correct", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(georgeMartin);

        String expectedMessage = "String input can't be null, empty or only white spaces.";

        // When
        Exception exception = assertThrows(CustomException.class,
                () -> bookController.addPaperBook(
                        isbn,
                        title,
                        summary,
                        authors,
                        HORROR,
                        Collections.singletonList(STORY),
                        1));

        // Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Inverted validation for a string literal, ISBN input - blank string")
    @Test
    void isStringInvalid_ShouldThrowCustomException_ForInput_ISBNBlankString() {
        // Given
        String isbn = "   ";
        String title = "Sample title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author georgeMartin = new Author("Sample", "Correct", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(georgeMartin);

        String expectedMessage = "String input can't be null, empty or only white spaces.";

        // When
        Exception exception = assertThrows(CustomException.class,
                () -> bookController.addPaperBook(
                        isbn,
                        title,
                        summary,
                        authors,
                        HORROR,
                        Collections.singletonList(STORY),
                        1));

        // Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    // tests isISBNInvalid method -> isBookValid -> addPaperBook

    @DisplayName("Inverted validation for a string literal, ISBN input - " +
            "not matching pattern ####-# (all digits)")
    @Test
    void isISBNInvalid_ShouldThrowCustomException_ForInput_ISBNNotMatchingPattern() {
        // Given
        String isbn = "asdds3";
        String title = "Sample title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author georgeMartin = new Author("Sample", "Correct", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(georgeMartin);

        String expectedMessage = "The provided ISBN was not valid. " +
                "The ISBN should consist of 5 numbers and a '-' symbol in the format '####-#'.";

        // When
        Exception exception = assertThrows(CustomException.class,
                () -> bookController.addPaperBook(
                        isbn,
                        title,
                        summary,
                        authors,
                        HORROR,
                        List.of(STORY),
                        1));

        // Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Inverted validation for a string literal, ISBN input - " +
            "containing letters and numbers in correct pattern format")
    @Test
    void isISBNInvalid_ShouldThrowCustomException_ForInput_ISBNContainingLetters() {
        // Given
        String isbn = "9sd0-a";
        String title = "Sample title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author georgeMartin = new Author("Sample", "Correct", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(georgeMartin);

        String expectedMessage = "The provided ISBN was not valid. " +
                "The ISBN should consist of 5 numbers and a '-' symbol in the format '####-#'.";

        // When
        Exception exception = assertThrows(CustomException.class,
                () -> bookController.addPaperBook(
                        isbn,
                        title,
                        summary,
                        authors,
                        HORROR,
                        Collections.singletonList(STORY),
                        1));

        // Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    // tests isTitleInvalid method -> isBookValid -> addPaperBook

    @DisplayName("Inverted validation for a string literal, title input length is three symbols")
    @Test
    void isTitleInvalid_ShouldThrowCustomException_ForInput_Title_TooSmall(){
        // Given
        String isbn = "1111-1";
        String title = "aaa";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author georgeMartin = new Author("Sample", "Correct", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(georgeMartin);

        String expectedMessage = "The provided book title is not valid. " +
                "The title should be at least 3 symbols long.";

        // When
        Exception exception = assertThrows(CustomException.class,
                () -> bookController.addPaperBook(
                        isbn,
                        title,
                        summary,
                        authors,
                        HORROR,
                        Collections.singletonList(STORY),
                        1));

        // Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Inverted validation for a string literal, title input length is longer then three symbols")
    @Test
    void isTitleInvalid_ShouldPrintBookCreatedMessage_ForInput_Title_ValidTitle(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author georgeMartin = new Author("Sample", "Correct", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(georgeMartin);

        // When
        String message = bookController.addPaperBook(
                isbn,
                title,
                summary,
                authors,
                HORROR,
                Collections.singletonList(STORY),
                1);

        // Then
        Assertions.assertEquals("Book: " + title + " added successfully to library.", message);
    }

    // tests isSummaryInvalid method -> isBookValid -> addPaperBook

    @DisplayName("Inverted validation for a string literal, summary input length is less than 51 symbols")
    @Test
    void isSummaryInvalid_ShouldThrowCustomException_ForInput_Summary_TooSmall(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Invalid Summary is less than 51 symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author georgeMartin = new Author("Sample", "Correct", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(georgeMartin);

        String expectedMessage = "The provided summary is not valid. " +
                "The summary must be at least 50 symbols in length.";

        // When
        Exception exception = assertThrows(CustomException.class,
                () -> bookController.addPaperBook(
                        isbn,
                        title,
                        summary,
                        authors,
                        HORROR,
                        Collections.singletonList(STORY),
                        1));

        // Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Inverted validation for a string literal, summary input length is long more than 50 symbols - Valid input")
    @Test
    void isSummaryInvalid_ShouldPrintBookCreatedMessage_ForInput_ValidSummary(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author georgeMartin = new Author("Sample", "Correct", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(georgeMartin);

        // When
        String message = bookController.addPaperBook(
                isbn,
                title,
                summary,
                authors,
                HORROR,
                Collections.singletonList(STORY),
                1);

        // Then
        Assertions.assertEquals("Book: " + title + " added successfully to library.", message);
    }

    // tests areAuthorsInvalid method -> isBookValid -> addPaperBook

    @DisplayName("Inverted validation for a list of authors, list is null")
    @Test
    void areAuthorsInvalid_ShouldThrowCustomException_ForInput_Authors_EmptyList(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        List<Author> authors = new ArrayList<>();

        String expectedMessage = "The provided author/authors is/are not valid. " +
                "Please provide at least one, valid author to add a book to the library.";

        // When
        Exception exception = assertThrows(CustomException.class,
                () -> bookController.addPaperBook(
                        isbn,
                        title,
                        summary,
                        authors,
                        HORROR,
                        Collections.singletonList(STORY),
                        1));

        // Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Inverted validation for a list of authors, list is null")
    @Test
    void areAuthorsInvalid_ShouldThrowCustomException_ForInput_Authors_Null(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        String expectedMessage = "The provided author/authors is/are not valid. " +
                "Please provide at least one, valid author to add a book to the library.";

        // When
        Exception exception = assertThrows(CustomException.class,
                () -> bookController.addPaperBook(
                        isbn,
                        title,
                        summary,
                        null,
                        HORROR,
                        Collections.singletonList(STORY),
                        1));

        // Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Inverted validation for a list of authors, list is one author")
    @Test
    void areAuthorsInvalid_ShouldReturnBookCreatedMessage_ForInput_Authors_OneAuthorValid(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author georgeMartin = new Author("Sample", "Correct", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(georgeMartin);

        // When
        String message = bookController.addPaperBook(
                isbn,
                title,
                summary,
                authors,
                HORROR,
                Collections.singletonList(STORY),
                1);

        // Then
        Assertions.assertEquals("Book: " + title + " added successfully to library.", message);
    }

    @DisplayName("Inverted validation for a list of authors, list is two author")
    @Test
    void areAuthorsInvalid_ShouldReturnBookCreatedMessage_ForInput_Authors_TwoAuthorsValid(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author georgeMartin = new Author("Sample", "Correct", ld, null);
        Author elinPelin = new Author("Elin", "Pelin", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(georgeMartin);
        authors.add(elinPelin);

        // When
        String message = bookController.addPaperBook(
                isbn,
                title,
                summary,
                authors,
                HORROR,
                Collections.singletonList(STORY),
                1);

        // Then
        Assertions.assertEquals("Book: " + title + " added successfully to library.", message);
    }

    // tests areAuthorsInvalid method -> isBookValid -> addPaperBook

    @DisplayName("Inverted validation for genre, input - null")
    @Test
    void isGenreInvalid_ShouldThrowCustomException_ForInput_Null(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author elinPelin = new Author("Elin", "Pelin", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(elinPelin);

        String expectedMessage = "The provided genre/s of the book are not valid. " +
                "Please provide at least one valid book genre.";

        // When
        Exception exception = assertThrows(CustomException.class,
                () -> bookController.addPaperBook(
                        isbn,
                        title,
                        summary,
                        authors,
                        null,
                        Collections.singletonList(STORY),
                        1));

        // Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Inverted validation for genre, input - valid genre")
    @Test
    void isGenreInvalid_ShouldReturnBookCreatedMessage_ForInput_ValidGenre(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author elinPelin = new Author("Elin", "Pelin", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(elinPelin);

        // When

        String message = bookController.addPaperBook(
                isbn,
                title,
                summary,
                authors,
                SCI_FI,
                Collections.singletonList(STORY),
                1);

        // Then
        Assertions.assertEquals(message, "Book: " + title + " added successfully to library.", message);
    }

    // areBookTagsInvalid method -> isBookValid -> addPaperBook

    @DisplayName("Inverted validation for book tags, input - valid book tags")
    @Test
    void areBookTagsInvalid_ShouldReturnBookCreatedMessage_ForInput_ValidBookTags(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author elinPelin = new Author("Elin", "Pelin", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(elinPelin);

        // When

        String message = bookController.addPaperBook(
                isbn,
                title,
                summary,
                authors,
                SCI_FI,
                Arrays.asList(STORY, HOBBY),
                1);

        // Then
        Assertions.assertEquals(message, "Book: " + title + " added successfully to library.", message);
    }

    @DisplayName("Inverted validation for book tags, input - null")
    @Test
    void areBookTagsInvalid_ShouldThrowCustomException_ForInput_Null(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author elinPelin = new Author("Elin", "Pelin", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(elinPelin);

        String expectedMessage = "The provided book tags are not valid. " +
                "Please provide at least one valid tag to add a book to the library.";

        // When
        Exception exception = assertThrows(CustomException.class,
                () -> bookController.addPaperBook(
                        isbn,
                        title,
                        summary,
                        authors,
                        SCI_FI,
                        null,
                        1));

        // Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    @DisplayName("Inverted validation for book tags, input - empty list")
    @Test
    void areBookTagsInvalid_ShouldThrowCustomException_ForInput_EmptyList(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author elinPelin = new Author("Elin", "Pelin", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(elinPelin);

        String expectedMessage = "The provided book tags are not valid. " +
                "Please provide at least one valid tag to add a book to the library.";

        // When
        Exception exception = assertThrows(CustomException.class,
                () -> bookController.addPaperBook(
                        isbn,
                        title,
                        summary,
                        authors,
                        SCI_FI,
                        new ArrayList<>(),
                        1));

        // Then
        assertTrue(expectedMessage.contains(exception.getMessage()));
    }

    // areCopiesAtLeastOne method -> addPaperBook

    @DisplayName("Testing areCopiesAtLeastOne method in addPaperBookMethod, input 0")
    @Test
    void areCopiesAtLeastOne_ShouldReturnBookCreatedMessage_ForInput_Zero(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author elinPelin = new Author("Elin", "Pelin", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(elinPelin);

        // When
        String message = bookController.addPaperBook(
                isbn,
                title,
                summary,
                authors,
                SCI_FI,
                Collections.singletonList(HOBBY),
                0);

        // Then
        Assertions.assertEquals("Adding book failed.", message);
    }

    @DisplayName("Testing areCopiesAtLeastOne method in addPaperBookMethod, input 1")
    @Test
    void areCopiesAtLeastOne_ShouldReturnBookCreatedMessage_ForInput_One(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author elinPelin = new Author("Elin", "Pelin", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(elinPelin);

        // When
        String message = bookController.addPaperBook(
                isbn,
                title,
                summary,
                authors,
                SCI_FI,
                Collections.singletonList(HOBBY),
                1);

        // Then
        Assertions.assertEquals("Book: Valid title added successfully to library.", message);
    }

    @DisplayName("Testing areCopiesAtLeastOne method in addPaperBookMethod, input 100")
    @Test
    void areCopiesAtLeastOne_ShouldReturnBookCreatedMessage_ForInput_Hundred(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author elinPelin = new Author("Elin", "Pelin", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(elinPelin);

        // When
        String message = bookController.addPaperBook(
                isbn,
                title,
                summary,
                authors,
                SCI_FI,
                Collections.singletonList(HOBBY),
                100);

        // Then
        Assertions.assertEquals("Book: Valid title added successfully to library.", message);
    }

    // isLinkValid method -> addEbook

    @DisplayName("Testing isLinkValid method in addEBook method, input valid link")
    @Test
    void isLinkValid_ShouldReturnBookCreatedMessage_ForInput_ValidLink(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author elinPelin = new Author("Elin", "Pelin", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(elinPelin);

        String readLink = "http://harrypotter.online.read.com";

        // When
        String message = bookController.addEBook(
                isbn,
                title,
                summary,
                authors,
                FANTASY,
                Arrays.asList(STORY, CHILDREN),
                readLink
                );

        // Then
        Assertions.assertEquals("Book: " + title + " added successfully to library.", message);
    }

    @DisplayName("Testing areCopiesAtLeastOne method in addPaperBookMethod, input null")
    @Test
    void isLinkValid_ShouldReturnFailedAddingBookMessage_ForInput_Null(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author elinPelin = new Author("Elin", "Pelin", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(elinPelin);

        // When
        String message = bookController.addEBook(
                isbn,
                title,
                summary,
                authors,
                FANTASY,
                Arrays.asList(STORY, CHILDREN),
                null
        );

        // Then
        Assertions.assertEquals("Adding book failed.", message);
    }

    @DisplayName("Testing areCopiesAtLeastOne method in addPaperBookMethod, input link with incorrect pattern")
    @Test
    void isLinkValid_ShouldReturnFailedAddingBookMessage_ForInput_IncorrectLinkPattern(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author elinPelin = new Author("Elin", "Pelin", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(elinPelin);

        String readLink = "asdasdas://asdasd/asda.asdas.";

        // When
        String message = bookController.addEBook(
                isbn,
                title,
                summary,
                authors,
                FANTASY,
                Arrays.asList(STORY, CHILDREN),
                readLink
        );

        // Then
        Assertions.assertEquals("Adding book failed.", message);
    }

    // addDownloadableEbook

    @DisplayName("Testing isLinkValid method in addEBook method, input valid link")
    @Test
    void addDownloadableEBook_ShouldReturnBookCreatedMessage_ForInput_ValidLink(){
        // Given
        String isbn = "1111-1";
        String title = "Valid title";
        String summary = "Sample text for summary to reach valid length. Still too short so writing a few more symbols.";

        LocalDate ld = LocalDate.of(2000, 2, 2);
        Author elinPelin = new Author("Elin", "Pelin", ld, null);
        List<Author> authors = new ArrayList<>();
        authors.add(elinPelin);

        String readLink = "http://harrypotter.online.read.com";
        String downloadLink = "http://harrypotter.online.read.com";

        // When
        String message = bookController.addDownloadableEBook(
                isbn,
                title,
                summary,
                authors,
                FANTASY,
                Arrays.asList(STORY, CHILDREN),
                readLink,
                downloadLink
        );

        // Then
        Assertions.assertEquals("Book: " + title + " added successfully to library.", message);
    }
}
