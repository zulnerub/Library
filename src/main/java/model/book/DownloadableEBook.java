package model.book;

import enums.BookCategory;
import enums.BookGenre;
import model.user.Author;

import java.util.List;

public class DownloadableEBook extends Book implements Electronic, Downloadable{
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

    @Override
    public String getDownLoadLink() {
        return downloadLink;
    }

    @Override
    public String getOnlineLink() {
        return onlineLink;
    }
}
