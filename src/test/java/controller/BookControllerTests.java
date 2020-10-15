package controller;

import enums.BookGenre;
import enums.BookTags;
import model.book.Book;
import model.user.impl.Author;
import org.junit.jupiter.api.*;
import repository.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
                BookGenre.FANTASY,
                Arrays.asList(BookTags.STORY, BookTags.HOBBY),
                5);

        bookController.addEBook(
                "1234-6",
                "Harry Potter",
                "A book about magic and magicians. For kids of all ages - small or big.",
                Collections.singletonList(joanRolling),
                BookGenre.FANTASY,
                Arrays.asList(BookTags.STORY, BookTags.CHILDREN),
                "http://harrypotter.online.read.com");

        bookController.addDownloadableEBook(
                "1234-7",
                "The day the earth stood still",
                "A book about aliens and post apocaliptic world scenarious.",
                Collections.singletonList(joanRolling),
                BookGenre.SCI_FI,
                Collections.singletonList(BookTags.LEARNING),
                "http://earthstood.online.read.com",
                "http://stillearth.online.download.com");
    }

    @DisplayName("Search for book by author first name - blank string.")
    @Test
    void searchByFirstName_ShouldReturn_EmptyList_ForInput_BlankString() {
        //When
        List<Book> result = bookController.searchBookByAuthorsFirstName("   ");

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by author first name - empty string.")
    @Test
    void searchByFirstName_ShouldReturn_EmptyList_ForInput_EmptyString() {
        //When
        List<Book> result = bookController.searchBookByAuthorsFirstName("");

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName(("Search for book by author first name - null"))
    @Test
    void searchByFirstName_ShouldReturn_EmptyList_ForInput_Null() {
        //When
        List<Book> result = bookController.searchBookByAuthorsFirstName(null);

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by author first name - 'George'")
    @Test
    void searchByFirstName_ShouldReturn_ListSizeOne_ForInput_George() {
        //When
        List<Book> result = bookController.searchBookByAuthorsFirstName("George");
        String authorName = result.get(0).getAuthors().get(0).getFirstName();

        //Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("George", authorName);
    }

    @DisplayName("Search for book by first name - 'o'")
    @Test
    void searchByFirstName_ShouldReturn_ListSizeThree_ForInput_O() {
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
    void searchByLastName_ShouldReturn_EmptyList_ForInput_BlankString() {
        //When
        List<Book> result = bookController.searchBookByAuthorsLastName("   ");

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by author last name - empty string")
    @Test
    void searchByLastName_ShouldReturn_EmptyList_ForInput_EmptyString() {
        //When
        List<Book> result = bookController.searchBookByAuthorsLastName("");

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by author last name - null")
    @Test
    void searchByLastName_ShouldReturn_EmptyList_ForInput_Null() {
        //When
        List<Book> result = bookController.searchBookByAuthorsLastName(null);

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by author last name - 'Martin'")
    @Test
    void searchByLastName_ShouldReturn_ListSizeOne_ForInput_Martin() {
        //When
        List<Book> result = bookController.searchBookByAuthorsLastName("Martin");
        String authorName = result.get(0).getAuthors().get(0).getLastName();

        //Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Martin", authorName);
    }

    @DisplayName("Search for book by author last name - 'i'")
    @Test
    void searchByLastName_ShouldReturn_ListSizeThree_ForInput_I() {
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
    void searchByFullName_ShouldReturn_EmptyList_ForInput_BlankString() {
        //When
        List<Book> result = bookController.searchBookByAuthorsFullName("   ");

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by author full name - empty string")
    @Test
    void searchByFullName_ShouldReturn_EmptyList_ForInput_EmptyString() {
        //When
        List<Book> result = bookController.searchBookByAuthorsFullName("");

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by author full name - null")
    @Test
    void searchByFullName_ShouldReturn_EmptyList_ForInput_Null() {
        //When
        List<Book> result = bookController.searchBookByAuthorsFullName(null);

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by author full name - George Martin")
    @Test
    void searchByFullName_ShouldReturn_ListSizeOne_ForInput_GeorgeMartin() {
        //When
        List<Book> result = bookController.searchBookByAuthorsFullName("George Martin");
        String authorName = result.get(0).getAuthors().get(0).getFullName();

        //Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("George Martin", authorName);
    }

    @DisplayName("Search for book by author full name - o") // change input
    @Test
    void searchByFullName_ShouldReturn_ListSizeThree_ForInput_O() {
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
    void searchByGenreName_ShouldReturnEmptyList_ForInputNull() {
        //Then
        Assertions.assertThrows(NullPointerException.class, () -> bookController.searchByBookGenre(null));
    }

    @DisplayName("Search for book by title name - empty string")
    @Test
    void searchByTitleName_ShouldReturnEmptyList_ForInputEmptyString() {
        //When
        List<Book> result = bookController.searchByBookTitle("");

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by title name - blank string")
    @Test
    void searchByTitleName_ShouldReturnEmptyList_ForInputBlankString() {
        //When
        List<Book> result = bookController.searchByBookTitle("   ");

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @DisplayName("Search for book by title name - null")
    @Test
    void searchByTitleName_ShouldReturnEmptyList_ForInputNull() {
        //When
        List<Book> result = bookController.searchByBookTitle(null);

        //Then
        Assertions.assertEquals(0, result.size());
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
    void searchByTitleName_ShouldReturnEmptyList_ForInputExactTitleExisting() {
        //When
        List<Book> result = bookController.searchByBookTitle("Game of thrones");
        String titleResult = result.get(0).getTitle();

        //Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Game of thrones", titleResult);
    }

    @DisplayName("Search for book by title name - part of existing title")
    @Test
    void searchByTitleName_ShouldReturnEmptyList_ForInputPartOfExistingTitle() {
        //When
        List<Book> result = bookController.searchByBookTitle("hrone");
        String titleResult = result.get(0).getTitle();

        //Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Game of thrones", titleResult);
    }

    // tags

    @DisplayName("Search for book by title name - part of existing title")
    @Test
    void searchByTitleName_ShouldReturnEmptyList_ForInputPartOfExistingTitle() {
        //When
        List<Book> result = bookController.searchByBookTitle("hrone");
        String titleResult = result.get(0).getTitle();

        //Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Game of thrones", titleResult);
    }

}
