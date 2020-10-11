package model.book.impl;

import enums.BookCategory;
import enums.BookGenre;
import model.book.Book;
import model.book.Downloadable;
import model.book.Electronic;
import model.user.impl.Author;

import java.util.List;

/**
 * Object that represents an online book with possibility to be read and downloaded.
 */
public class DownloadableEBook extends Book implements Electronic, Downloadable {
    private String onlineLink;
    private String downloadLink;

    public DownloadableEBook(String ISBN, String title, String summary,
                             List<Author> authors,
                             List<BookGenre> genres,
                             List<BookCategory> categories, String onlineLink, String downloadLink) {
        super(ISBN, title, summary, authors, genres, categories);
        this.onlineLink = onlineLink;
        this.downloadLink = downloadLink;
    }

    /**
     * @return String representation of the book's download link.
     */
    @Override
    public String getDownLoadLink() {
        return downloadLink;
    }

    /**
     * @return String representation of the book's link for online reading.
     */
    @Override
    public String getOnlineLink() {
        return onlineLink;
    }
}
