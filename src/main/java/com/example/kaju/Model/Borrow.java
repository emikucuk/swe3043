package com.example.kaju.Model;

import com.example.kaju.Dtos.BorrowDto;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "borrows")
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private Long version = 0L;

    @Column(nullable = false)
    private LocalDate borrowDate;

    @Column
    private LocalDate returnDate;

    @Column(nullable = false)
    private boolean isReturned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
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
