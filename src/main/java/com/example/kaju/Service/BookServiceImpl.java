package com.example.kaju.Service;

import com.example.kaju.Dtos.BookDto;
import com.example.kaju.Exception.ErrorMessages;
import com.example.kaju.Exception.ResourceAlreadyExistsException;
import com.example.kaju.Exception.ResourceNotFoundException;
import com.example.kaju.Model.Book;
import com.example.kaju.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookDto getBookById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BOOK_NOT_FOUND + ": " + id))
                .viewAsBookDto();
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(Book::viewAsBookDto)
                .toList();
    }

    @Override
    @Transactional
    public BookDto createBook(Book book) {
        if(bookRepository.findById(book.getId()).isPresent()) {
            throw new ResourceAlreadyExistsException(ErrorMessages.BOOK_ALREADY_EXISTS + ": " + book.getId());
        }
        return bookRepository.save(book).viewAsBookDto();
    }

    @Override
    @Transactional
    public BookDto updateBook(long id, Book updatedBook) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BOOK_NOT_FOUND + ": " + id));
        
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setDescription(updatedBook.getDescription());
        
        return bookRepository.save(existingBook).viewAsBookDto();
    }

    @Override
    @Transactional
    public void deleteBook(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BOOK_NOT_FOUND + ": " + id));
        bookRepository.delete(book);
    }
} 