package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.web.request.UserRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    com.edu.ulab.app.dto.UserDto userRequestToUserDto(UserRequest userRequest);

    UserRequest userDtoToUserRequest(UserDto userDto);

    UserEntity userDtoToEntity(UserDto userDto);

    UserDto userEntityToDto(UserEntity userEntity);

    UserEntity updateUserEntity(UserEntity update, @MappingTarget UserEntity target);
}
