package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.mapper.EntityUpdaterMapper;
import com.edu.ulab.app.storage.BookStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookStorageImpl implements BookStorage {
    private final static Map<Long, BookEntity> bookMap = new HashMap<>();
    private final EntityUpdaterMapper entityUpdaterMapper;
    private final UserStorageImpl userStorage;

    @Autowired
    public BookStorageImpl(EntityUpdaterMapper entityUpdater, UserStorageImpl userStorage) {
        this.entityUpdaterMapper = entityUpdater;
        this.userStorage = userStorage;
    }

    @Override
    public boolean containsBook(Long bookId) {
        return bookMap.containsKey(bookId);
    }

    /**
     * Book добавляется как в bookMap, так и в список, который храниться в User .
     */
    @Override
    public void addBook(BookEntity book) {
        Long bookId = book.getId();
        Long userId = book.getUserId();
        UserEntity user = userStorage.getUser(userId);

        bookMap.put(bookId, book);
        user.getBooks().add(book);
    }

    /**
     * Обновление полей объекта происходит через Mapstruct.
     */
    @Override
    public void updateBook(BookEntity newBook) {
        Long bookId = newBook.getId();
        BookEntity currentBook = getBook(bookId);
        BookEntity updatedBook = entityUpdaterMapper.updateBookEntity(newBook, currentBook);
        addBook(updatedBook);
    }

    @Override
    public BookEntity getBook(Long bookId) {
        return bookMap.get(bookId);
    }

    /**
     * Book удаляется как из bookMap, так и из списка, который храниться в User .
     */
    @Override
    public void removeBook(Long bookId) {
        BookEntity book = getBook(bookId);
        Long userId = book.getUserId();
        UserEntity user =  userStorage.getUser(userId);

        bookMap.remove(bookId);
        user.getBooks().remove(book);
    }

    @Override
    public List<BookEntity> getBooksByUser(Long userId) {
        return userStorage.getUser(userId).getBooks();
    }

    @Override
    public void removeUserBooks(Long userId) {
        userStorage.getUser(userId).getBooks().clear();
    }
}
