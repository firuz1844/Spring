package com.edu.ulab.app.entity;

import lombok.Data;

@Data
public class BookEntity {
    private Long id;
    private Long userId;
    private String title;
    private String author;
    private Long pageCount;
}
