package com.example.kaju.Service;

import com.example.kaju.Dtos.BorrowDto;
import com.example.kaju.Exception.ErrorMessages;
import com.example.kaju.Exception.ResourceAlreadyExistsException;
import com.example.kaju.Exception.ResourceNotFoundException;
import com.example.kaju.Model.Book;
import com.example.kaju.Model.Borrow;
import com.example.kaju.Model.User;
import com.example.kaju.Repository.BookRepository;
import com.example.kaju.Repository.BorrowRepository;
import com.example.kaju.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BorrowDto getBorrowById(long id) {
        return borrowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BORROW_NOT_FOUND + ": " + id))
                .viewAsBorrowDtoWithUserAndBook();
    }

    @Override
    public List<BorrowDto> getAllBorrows() {
        return borrowRepository.findAll()
                .stream()
                .map(Borrow::viewAsBorrowDtoWithUserAndBook)
                .toList();
    }

    @Override
    @Transactional
    public BorrowDto createBorrow(Borrow borrow) {
        if(borrowRepository.findById(borrow.getId()).isPresent()) {
            throw new ResourceAlreadyExistsException(ErrorMessages.BORROW_ALREADY_EXISTS + ": " + borrow.getId());
        }

        // Get the user and book entities
        User user = userRepository.findById(borrow.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND + ": " + borrow.getUser().getId()));
        
        Book book = bookRepository.findById(borrow.getBook().getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BOOK_NOT_FOUND + ": " + borrow.getBook().getId()));

        // Set the user and book references
        borrow.setUser(user);
        borrow.setBook(book);

        return borrowRepository.save(borrow).viewAsBorrowDtoWithUserAndBook();
    }

    @Override
    @Transactional
    public BorrowDto updateBorrow(long id, Borrow updatedBorrow) {
        Borrow existingBorrow = borrowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BORROW_NOT_FOUND + ": " + id));
        
        // Get the user and book entities
        User user = userRepository.findById(updatedBorrow.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND + ": " + updatedBorrow.getUser().getId()));
        
        Book book = bookRepository.findById(updatedBorrow.getBook().getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BOOK_NOT_FOUND + ": " + updatedBorrow.getBook().getId()));

        existingBorrow.setBorrowDate(updatedBorrow.getBorrowDate());
        existingBorrow.setReturnDate(updatedBorrow.getReturnDate());
        existingBorrow.setReturned(updatedBorrow.isReturned());
        existingBorrow.setUser(user);
        existingBorrow.setBook(book);
        
        return borrowRepository.save(existingBorrow).viewAsBorrowDtoWithUserAndBook();
    }

    @Override
    @Transactional
    public void deleteBorrow(long id) {
        Borrow borrow = borrowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BORROW_NOT_FOUND + ": " + id));
        borrowRepository.delete(borrow);
    }
} 