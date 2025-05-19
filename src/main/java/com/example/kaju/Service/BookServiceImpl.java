package com.example.kaju.Service;

import com.example.kaju.Dtos.BookDto;
import com.example.kaju.Exception.ErrorMessages;
import com.example.kaju.Exception.ResourceAlreadyExistsException;
import com.example.kaju.Exception.ResourceNotFoundException;
import com.example.kaju.Model.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookServiceImpl implements BookService {

    private final Map<Long, Book> books = new ConcurrentHashMap<>();

    @Override
    public BookDto getBookById(long id) {
        Book book = books.get(id);
        if (book == null) {
            throw new ResourceNotFoundException(ErrorMessages.BOOK_NOT_FOUND + ": " + id);
        }
        return book.viewAsBookDto();
    }

    @Override
    public List<BookDto> getAllBooks() {
        return books.values()
                .stream()
                .map(Book::viewAsBookDto)
                .toList();
    }

    @Override
    public BookDto createBook(Book book) {
        if (books.containsKey(book.getId())) {
            throw new ResourceAlreadyExistsException(ErrorMessages.BOOK_ALREADY_EXISTS + ": " + book.getId());
        }
        books.put(book.getId(), book);
        return book.viewAsBookDto();
    }

    @Override
    public BookDto updateBook(long id, Book updatedBook) {
        Book existingBook = books.get(id);
        if (existingBook == null) {
            throw new ResourceNotFoundException(ErrorMessages.BOOK_NOT_FOUND + ": " + id);
        }
        
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setDescription(updatedBook.getDescription());
        
        books.put(id, existingBook);
        return existingBook.viewAsBookDto();
    }

    @Override
    public void deleteBook(long id) {
        if (!books.containsKey(id)) {
            throw new ResourceNotFoundException(ErrorMessages.BOOK_NOT_FOUND + ": " + id);
        }
        books.remove(id);
    }
} 