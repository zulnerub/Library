package model.book.impl;

import enums.BookTags;
import enums.BookGenre;
import model.book.Book;
import model.user.impl.Author;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * An Object that emulates a paper book, containing number of copies in stock.
 * And other attributes characteristic for a real book like - isbn, author/s, title, summary etc.
 */
@XmlRootElement(name = "paper-book")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaperBook extends Book {
    @XmlAttribute(name = "currently-available")
    private int currentlyAvailable;
    @XmlAttribute(name = "total-copies")
    private int totalCopies;

    public PaperBook(String ISBN, String title, String summary,
                     List<Author> authors,
                     BookGenre genre,
                     List<BookTags> tags,
                     int currentlyAvailable, int totalCopies) {
        super(ISBN, title, summary, authors, genre, tags);
        this.currentlyAvailable = currentlyAvailable;
        this.totalCopies = totalCopies;
    }

    /**
     * @return Get the value of the books free at the moment.
     */
    public int getCurrentlyAvailable() {
        return currentlyAvailable;
    }

    /**
     * Sets the modified amount of copies of the book after borrowing or returning it.
     *
     * @param currentlyAvailable New amount of free copies after borrow or return operations.
     */
    public void setCurrentlyAvailable(int currentlyAvailable) {
        this.currentlyAvailable = currentlyAvailable;
    }
}
