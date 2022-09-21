package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.UserEntity;

/**
 * API, через которое можно обращаться к хранилищу, работающему с данными,
 * которые описывают абстаракцию сущности 'User', имеющую поле id.
 */
public interface UserStorage {

    boolean containsUser(Long userId);

    void addUser(UserEntity user);

    void updateUser(UserEntity user);

    UserEntity getUser(Long userId);

    void removeUser(Long userId);

}
