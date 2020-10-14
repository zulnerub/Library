package controller;

import enums.BookGenre;
import enums.BookTags;
import model.book.Book;
import model.user.impl.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BookControllerTests {

    UserRepository userRepository = new UserRepository();
    BookController bookController = new BookController(userRepository);


    @BeforeEach
    void init(){
        LocalDate ld = LocalDate.of(1965, 1, 1);
        LocalDate sLd = LocalDate.of(1972,3,22);

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

    @Test
    void shouldReturn_EmptyList_ForInput_BlankString(){
        //When
        List<Book> result = bookController.searchBookByAuthorsFirstName("   ");

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void shouldReturn_EmptyList_ForInput_EmptyString(){
        //When
        List<Book> result = bookController.searchBookByAuthorsFirstName("");

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void shouldReturn_EmptyList_ForInput_Null(){
        //When
        List<Book> result = bookController.searchBookByAuthorsFirstName(null);

        //Then
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void shouldReturn_ListSizeOne_WithAuthorFirstNameContaining_George(){
        //When
        List<Book> result = bookController.searchBookByAuthorsFirstName("George");
        String authorName = result.get(0).getAuthors().get(0).getFirstName();

        //Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("George", authorName);
    }

    @Test
    void shouldReturn_ListSizeThree_WithAuthorFirstNameContaining_O(){
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
}
