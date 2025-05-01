package com.example.kaju.Controller;

import com.example.kaju.Dtos.BorrowDto;
import com.example.kaju.Model.Borrow;
import com.example.kaju.Service.BorrowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Autowired
    private BorrowServiceImpl borrowService;

    @GetMapping("/all")
    public ResponseEntity<List<BorrowDto>> getAllBorrows() {
        return new ResponseEntity<>(borrowService.getAllBorrows(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<BorrowDto> getBorrow(@PathVariable long id) {
        if(id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BorrowDto borrow = borrowService.getBorrowById(id);
        if (borrow != null) {
            return new ResponseEntity<>(borrow, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BorrowDto> createBorrow(@RequestBody Borrow borrow) {
        BorrowDto createdBorrow = borrowService.createBorrow(borrow);
        return new ResponseEntity<>(createdBorrow, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BorrowDto> updateBorrow(@PathVariable long id, @RequestBody Borrow borrow) {
        BorrowDto updatedBorrow = borrowService.updateBorrow(id, borrow);
        return new ResponseEntity<>(updatedBorrow, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteBorrow(@PathVariable long id) {
        if(id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        borrowService.deleteBorrow(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
} 