package com.edu.ulab.app.mapper;

import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EntityUpdaterMapper {
    UserEntity updateUserEntity(UserEntity update, @MappingTarget UserEntity target);

    BookEntity updateBookEntity(BookEntity update, @MappingTarget BookEntity target);
}
