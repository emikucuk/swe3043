package com.example.kaju.Model;

import com.example.kaju.Dtos.BorrowDto;
import lombok.Data;
import java.time.LocalDate;

@Data
public class Borrow {
    private long id;
    private Long version = 0L;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean isReturned;
    private User user;
    private Book book;

    public Borrow() {
        this.version = 0L;
    }

    public Borrow(LocalDate borrowDate, LocalDate returnDate, boolean isReturned, User user, Book book) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.isReturned = isReturned;
        this.user = user;
        this.book = book;
        this.version = 0L;
    }

    public Borrow(BorrowDto borrowDto, User user, Book book) {
        this.id = borrowDto.getId();
        this.borrowDate = borrowDto.getBorrowDate();
        this.returnDate = borrowDto.getReturnDate();
        this.isReturned = borrowDto.isReturned();
        this.user = user;
        this.book = book;
        this.version = 0L;
    }

    public BorrowDto viewAsBorrowDto() {
        return new BorrowDto(id, borrowDate, returnDate, isReturned);
    }

    public BorrowDto viewAsBorrowDtoWithUserAndBook() {
        return new BorrowDto(id, borrowDate, returnDate, isReturned, user.viewAsUserDto(), book.viewAsBookDto());
    }
}
