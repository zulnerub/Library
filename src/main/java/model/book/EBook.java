package model.book;

import enums.BookCategory;
import enums.BookGenre;
import model.user.Author;

import java.util.List;

public class EBook extends Book implements Electronic{
    private String onlineLink;

    public EBook(String ISBN, String title, String summary,
                 List<Author> authors,
                 List<BookGenre> genres,
                 List<BookCategory> categories,
                 String onlineLink) {
        super(ISBN, title, summary, authors, genres, categories);
        this.onlineLink = onlineLink;
    }

    @Override
    public String getOnlineLink() {
        return onlineLink;
    }
}
