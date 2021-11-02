package model.book.impl;

import enums.BookTags;
import enums.BookGenre;
import model.book.Book;
import model.book.Electronic;
import model.user.impl.Author;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Object that represents an online book with possibility to be read online.
 */
@XmlRootElement(name = "ebook")
@XmlAccessorType(XmlAccessType.FIELD)
public class EBook extends Book implements Electronic {

    @XmlAttribute(name = "online-link")
    private String onlineLink;

    public EBook(String ISBN, String title, String summary,
                 List<Author> authors,
                 BookGenre genre,
                 List<BookTags> categories,
                 String onlineLink) {
        super(ISBN, title, summary, authors, genre, categories);
        this.onlineLink = onlineLink;
    }

    /**
     * @return String representation of the book's link for online reading.
     */
    @Override
    public String getOnlineLink() {
        return onlineLink;
    }
}
