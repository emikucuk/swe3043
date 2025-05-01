package com.example.kaju.Dtos;

import com.example.kaju.Model.Borrow;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BorrowDto {
    private long id;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean isReturned;
    private UserDto userDto;
    private BookDto bookDto;

    public BorrowDto(long id, LocalDate borrowDate, LocalDate returnDate, boolean isReturned) {
        this.id = id;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.isReturned = isReturned;
    }

    public BorrowDto(long id, LocalDate borrowDate, LocalDate returnDate, boolean isReturned, UserDto userDto, BookDto bookDto) {
        this.id = id;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.isReturned = isReturned;
        this.userDto = userDto;
        this.bookDto = bookDto;
    }
}
