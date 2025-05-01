package com.example.kaju.Service;

import com.example.kaju.Model.Book;
import com.example.kaju.Dtos.BookDto;
import java.util.List;

public interface BookService {
    BookDto getBookById(long id);
    List<BookDto> getAllBooks();
    BookDto createBook(Book book);
    BookDto updateBook(long id, Book book);
    void deleteBook(long id);
} 