package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.exception.WrongBookException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.impl.BookStorageImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private static Long idCounter = 1L;
    private final BookStorageImpl storage;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookStorageImpl storage, BookMapper bookMapper) {
        this.storage = storage;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        checkBookDtoForNull(bookDto);
        bookDto.setId(idCounter++);

        BookEntity bookEntity = bookMapper.bookDtoToEntity(bookDto);
        storage.addBook(bookEntity);
        log.info("Book added to the storage: {}", bookDto.getId());
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        checkBookDtoForNull(bookDto);
        Long bookId = bookDto.getId();
        bookDto.setId(bookId);
        BookEntity bookEntity = bookMapper.bookDtoToEntity(bookDto);
        storage.updateBook(bookEntity);
        BookEntity updatedBookEntity = storage.getBook(bookId);
        log.info("Book updated in the storage: {}", bookDto.getId());
        return bookMapper.bookEntityToDto(updatedBookEntity);
    }

    @Override
    public BookDto getBookById(Long bookId) {
        checkBookExistence(bookId);
        checkBookIdForNull(bookId);
        BookEntity bookEntity = storage.getBook(bookId);
        log.info("Got book from the storage: {}", bookId);
        return bookMapper.bookEntityToDto(bookEntity);
    }

    @Override
    public void deleteBookById(Long bookId) {
        checkBookExistence(bookId);
        if (bookId == null) {
            return;
        }
        storage.removeBook(bookId);
        log.info("Book deleted from the storage: {}", bookId);
    }

    public boolean isBookExist(Long bookId) {
        return (bookId != null) && (storage.containsBook(bookId));
    }

    @Override
    public List<BookDto> getBooksByUser(Long userId) {
        if (userId == null) {
            return new ArrayList<>();
        }
        List<BookEntity> entityList = storage.getBooksByUser(userId);
        List<BookDto> bookList =  entityList
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookEntityToDto)
                .toList();
        log.info("Got books for user in the storage: {}", userId);
        return bookList;
    }

    private void checkBookExistence(Long id) {
        if (!isBookExist(id)) {
            throw new NotFoundException(
                    String.format("Book with id %s not found", id)
            );
        }
    }
    private void checkBookIdForNull(Long id) {
        if (id == null) {
            throw new WrongBookException("Book id is null");
        }
    }
    private void checkBookDtoForNull(BookDto bookDto) {
        if (bookDto == null) {
            throw new WrongBookException("User is null");
        }
    }
}
