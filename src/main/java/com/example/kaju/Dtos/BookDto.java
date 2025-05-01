package com.example.kaju.Dtos;

import com.example.kaju.Model.Book;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private long id;
    private String title;
    private String author;
    private String isbn;
    private String description;
}
