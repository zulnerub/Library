package model.book;

import enums.BookCategory;
import enums.BookGenre;
import model.user.Author;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Book {
    private String ISBN;
    private String title;
    private String summary;
    private List<Author> authors;
    private List<BookGenre> genres;
    private List<BookCategory> categories;

    public Book(String ISBN, String title, String summary,
                List<Author> authors,
                List<BookGenre> genres,
                List<BookCategory> categories) {
        this.ISBN = ISBN;
        this.title = title;
        this.summary = summary;
        this.authors = authors;
        this.genres = genres;
        this.categories = categories;
    }

    public String getTitle(){
        return title;
    }

    public List<String> getCategoriesNames(){
        return categories.stream()
                .map(BookCategory::getSimpleName)
                .collect(Collectors.toList());
    }

    public List<String> getGenreNames(){
        return genres.stream()
                .map(BookGenre::getSimpleName)
                .collect(Collectors.toList());
    }
}
