package com.example.kaju.Service;

import com.example.kaju.Model.Borrow;
import com.example.kaju.Dtos.BorrowDto;
import java.util.List;

public interface BorrowService {
    BorrowDto getBorrowById(long id);
    List<BorrowDto> getAllBorrows();
    BorrowDto createBorrow(Borrow borrow);
    BorrowDto updateBorrow(long id, Borrow borrow);
    void deleteBorrow(long id);
} 