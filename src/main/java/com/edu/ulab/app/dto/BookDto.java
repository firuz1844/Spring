package com.edu.ulab.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {
    private Long id;
    private Long userId;
    private String title;
    private String author;
    private long pageCount;
}
