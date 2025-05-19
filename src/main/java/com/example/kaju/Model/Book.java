package com.example.kaju.Model;

import com.example.kaju.Dtos.BookDto;
import lombok.Data;
import java.util.List;

@Data
public class Book {
    private long id;
    private Long version = 0L;
    private String title;
    private String author;
    private String isbn;
    private String description;
    private List<Borrow> borrows;

    public Book() {
        this.version = 0L;
    }

    public Book(String title, String author, String isbn, String description) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.version = 0L;
    }

    public Book(BookDto bookDto) {
        this.id = bookDto.getId();
        this.title = bookDto.getTitle();
        this.author = bookDto.getAuthor();
        this.isbn = bookDto.getIsbn();
        this.description = bookDto.getDescription();
        this.version = 0L;
    }

    public BookDto viewAsBookDto() {
        return new BookDto(id, title, author, isbn, description);
    }
}
