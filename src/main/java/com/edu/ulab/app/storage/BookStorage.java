package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.BookEntity;

import java.util.List;

/**
 * API, через которое можно обращаться к хранилищу, работающему с данными,
 * которые описывают абстаракцию сущности 'Book', имеющую поле id.
 */
public interface BookStorage {

    boolean containsBook(Long bookId);

    void addBook(BookEntity book);

    void updateBook(BookEntity book);

    BookEntity getBook(Long bookId);

    void removeBook(Long bookId);

    List<BookEntity> getBooksByUser(Long userId);

    void removeUserBooks(Long userId);
}
