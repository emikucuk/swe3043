package com.example.kaju.Service;

import com.example.kaju.Dtos.BorrowDto;
import com.example.kaju.Exception.ErrorMessages;
import com.example.kaju.Exception.ResourceAlreadyExistsException;
import com.example.kaju.Exception.ResourceNotFoundException;
import com.example.kaju.Model.Book;
import com.example.kaju.Model.Borrow;
import com.example.kaju.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BorrowServiceImpl implements BorrowService {

    private final Map<Long, Borrow> borrows = new ConcurrentHashMap<>();
    
    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private BookServiceImpl bookService;

    @Override
    public BorrowDto getBorrowById(long id) {
        Borrow borrow = borrows.get(id);
        if (borrow == null) {
            throw new ResourceNotFoundException(ErrorMessages.BORROW_NOT_FOUND + ": " + id);
        }
        return borrow.viewAsBorrowDtoWithUserAndBook();
    }

    @Override
    public List<BorrowDto> getAllBorrows() {
        return borrows.values()
                .stream()
                .map(Borrow::viewAsBorrowDtoWithUserAndBook)
                .toList();
    }

    @Override
    public BorrowDto createBorrow(Borrow borrow) {
        if (borrows.containsKey(borrow.getId())) {
            throw new ResourceAlreadyExistsException(ErrorMessages.BORROW_ALREADY_EXISTS + ": " + borrow.getId());
        }
        
        // Verify user and book exist
        userService.getUserById(borrow.getUser().getId());
        bookService.getBookById(borrow.getBook().getId());
        
        borrows.put(borrow.getId(), borrow);
        return borrow.viewAsBorrowDtoWithUserAndBook();
    }

    @Override
    public BorrowDto updateBorrow(long id, Borrow updatedBorrow) {
        Borrow existingBorrow = borrows.get(id);
        if (existingBorrow == null) {
            throw new ResourceNotFoundException(ErrorMessages.BORROW_NOT_FOUND + ": " + id);
        }
        
        // Verify user and book exist if they are being updated
        if (updatedBorrow.getUser() != null) {
            userService.getUserById(updatedBorrow.getUser().getId());
        }
        if (updatedBorrow.getBook() != null) {
            bookService.getBookById(updatedBorrow.getBook().getId());
        }
        
        existingBorrow.setBorrowDate(updatedBorrow.getBorrowDate());
        existingBorrow.setReturnDate(updatedBorrow.getReturnDate());
        existingBorrow.setReturned(updatedBorrow.isReturned());
        if (updatedBorrow.getUser() != null) {
            existingBorrow.setUser(updatedBorrow.getUser());
        }
        if (updatedBorrow.getBook() != null) {
            existingBorrow.setBook(updatedBorrow.getBook());
        }
        
        borrows.put(id, existingBorrow);
        return existingBorrow.viewAsBorrowDtoWithUserAndBook();
    }

    @Override
    public void deleteBorrow(long id) {
        if (!borrows.containsKey(id)) {
            throw new ResourceNotFoundException(ErrorMessages.BORROW_NOT_FOUND + ": " + id);
        }
        borrows.remove(id);
    }
} 