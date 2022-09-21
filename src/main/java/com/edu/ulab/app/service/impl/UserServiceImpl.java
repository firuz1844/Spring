package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.exception.WrongUserException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.impl.BookStorageImpl;
import com.edu.ulab.app.storage.impl.UserStorageImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static Long idCounter = 1L;
    private final UserStorageImpl userStorage;
    private final BookStorageImpl bookStorage;
    private final UserMapper userMapper;


    @Autowired
    public UserServiceImpl(UserStorageImpl storage, BookStorageImpl bookStorage, UserMapper userMapper) {
        this.userStorage = storage;
        this.bookStorage = bookStorage;
        this.userMapper = userMapper;

    }

    // Сгенерить id
    // создать пользователя
    // вернуть сохраненного пользователя со всеми необходимыми полями id
    @Override
    public UserDto createUser(UserDto userDto) {
        checkUserDtoForNull(userDto);
        checkUserName(userDto);
        userDto.setId(idCounter++);

        UserEntity userEntity = userMapper.userDtoToEntity(userDto);
        userStorage.addUser(userEntity);
        log.info("User added to the storage: {}", userDto.getId());
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        checkUserExistence(userId);
        checkUserDtoForNull(userDto);
        checkUserName(userDto);
        userDto.setId(userId);
        UserEntity userEntity = userMapper.userDtoToEntity(userDto);
        userStorage.updateUser(userEntity);
        UserEntity updatedUserEntity = userStorage.getUser(userId);
        log.info("User updated in the storage: {}", userDto.getId());
        return userMapper.userEntityToDto(updatedUserEntity);
    }

    @Override
    public UserDto getUserById(Long userId) {
        checkUserIdForNull(userId);
        checkUserExistence(userId);
        UserEntity userEntity = userStorage.getUser(userId);
        log.info("Got user from the storage: {}", userId);
        return userMapper.userEntityToDto(userEntity);
    }

    @Override
    public void deleteUserById(Long userId) {
        checkUserExistence(userId);
        if (userId == null) {
            return;
        }

        userStorage.removeUser(userId);
        log.info("User deleted from the storage: {}", userId);
    }

    public boolean isUserExist(Long userId) {
        return (userId != null) && (userStorage.containsUser(userId));
    }

    @Override
    public void clearUserBooks(Long userId) {
        checkUserExistence(userId);
        if (userId == null) {
            return;
        }
        bookStorage.removeUserBooks(userId);
        log.info("Books of the User deleted from the storage: {}", userId);
    }

    private void checkUserExistence(Long id) {
        if (!isUserExist(id)) {
            throw new NotFoundException(
                    String.format("User with id %s not found", id)
            );
        }
    }

    private void checkUserIdForNull(Long id) {
        if (id == null) {
            throw new WrongUserException("User id is null");
        }
    }

    private void checkUserDtoForNull(UserDto userDto) {
        if (userDto == null) {
            throw new WrongUserException("User is null");
        }
    }

    private void checkUserName(UserDto userDto) {
        if (userDto.getFullName().length() > 10) {
            throw new WrongUserException("User has too long FullName. Max length = 10");
        }
    }
}

