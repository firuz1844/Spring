package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.web.request.BookRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    com.edu.ulab.app.dto.BookDto bookRequestToBookDto(BookRequest bookRequest);

    BookRequest bookDtoToBookRequest(BookDto bookDto);

    BookEntity bookDtoToEntity(BookDto bookDto);

    BookDto bookEntityToDto(BookEntity bookEntity);


}
