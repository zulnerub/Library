package model.common;

import model.book.Book;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Book> usedBooks = new ArrayList<>();

    public History() {
    }

    public void addUsedBook(Book book){
        usedBooks.add(book);
    }

    public List<Book> getUsedBooks(){
        return usedBooks;
    }
}
