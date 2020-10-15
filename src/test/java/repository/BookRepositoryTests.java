package repository;

import model.book.Book;
import model.book.impl.DownloadableEBook;
import model.book.impl.EBook;
import model.book.impl.PaperBook;
import model.user.impl.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static enums.BookGenre.FANTASY;
import static enums.BookGenre.SCI_FI;
import static enums.BookTags.*;
import static enums.BookTags.LEARNING;

public class BookRepositoryTests {

    private UserRepository userRepository = new UserRepository();
    private BookRepository bookRepository = new BookRepository(userRepository);

    @BeforeEach
    void init() {
        LocalDate ld = LocalDate.of(1965, 1, 1);
        LocalDate sLd = LocalDate.of(1972, 3, 22);

        Author georgeMartin = new Author("George", "Martin", ld, null);
        Author joanRolling = new Author("Joan", "Rolling", sLd, null);

        Book gameOfThrones = new PaperBook(
                "1234-5",
                "Game of thrones",
                "Very interesting book about internal and  external royal family affairs.",
                Collections.singletonList(georgeMartin),
                FANTASY,
                Arrays.asList(STORY, HOBBY),
                5,
                5);

        Book harryPotter = new EBook(
                "1234-6",
                "Harry Potter",
                "A book about magic and magicians. For kids of all ages - small or big.",
                Collections.singletonList(joanRolling),
                FANTASY,
                Arrays.asList(STORY, CHILDREN),
                "http://harrypotter.online.read.com");

        Book dayEarthStoodStill = new DownloadableEBook(
                "1234-7",
                "The day the earth stood still",
                "A book about aliens and post apocaliptic world scenarious.",
                Collections.singletonList(joanRolling),
                SCI_FI,
                Collections.singletonList(LEARNING),
                "http://earthstood.online.read.com",
                "http://stillearth.online.download.com");

    }


    @DisplayName("Testing if the add methods adds one of each three types of books.")
    @Test
    void addBookToLibrary_ShouldAddThreeBooksToLibrary_Input_PaperBook_EBook_DownloadableEBook(){
        LocalDate ld = LocalDate.of(1965, 1, 1);
        LocalDate sLd = LocalDate.of(1972, 3, 22);

        Author georgeMartin = new Author("George", "Martin", ld, null);
        Author joanRolling = new Author("Joan", "Rolling", sLd, null);

        Book gameOfThrones = new PaperBook(
                "1234-5",
                "Game of thrones",
                "Very interesting book about internal and  external royal family affairs.",
                Collections.singletonList(georgeMartin),
                FANTASY,
                Arrays.asList(STORY, HOBBY),
                5,
                5);

        Book harryPotter = new EBook(
                "1234-6",
                "Harry Potter",
                "A book about magic and magicians. For kids of all ages - small or big.",
                Collections.singletonList(joanRolling),
                FANTASY,
                Arrays.asList(STORY, CHILDREN),
                "http://harrypotter.online.read.com");

        Book dayEarthStoodStill = new DownloadableEBook(
                "1234-7",
                "The day the earth stood still",
                "A book about aliens and post apocaliptic world scenarious.",
                Collections.singletonList(joanRolling),
                SCI_FI,
                Collections.singletonList(LEARNING),
                "http://earthstood.online.read.com",
                "http://stillearth.online.download.com");

        bookRepository.addBookToLibrary(gameOfThrones);

        Assertions.assertEquals(1, bookRepository.getAllBooksInLibrary().size());
    }
}
