package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.mapper.EntityUpdaterMapper;
import com.edu.ulab.app.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserStorageImpl implements UserStorage {
    private final static Map<Long, UserEntity> userMap = new HashMap<>();

    private final EntityUpdaterMapper entityUpdaterMapper;

    @Autowired
    public UserStorageImpl(EntityUpdaterMapper entityUpdater) {
        this.entityUpdaterMapper = entityUpdater;
    }

    @Override
    public boolean containsUser(Long userId) {
        return userMap.containsKey(userId);
    }

    @Override
    public void addUser(UserEntity user) {
        userMap.put(user.getId(), user);
    }

    /**
     * Обновление полей объекта происходит через Mapstruct.
     */
    @Override
    public void updateUser(UserEntity newUser) {
        Long userId = newUser.getId();
        UserEntity currentUser = getUser(userId);
        UserEntity updatedUser = entityUpdaterMapper.updateUserEntity(newUser, currentUser);
        addUser(updatedUser);
    }

    @Override
    public UserEntity getUser(Long userId) {
        return userMap.get(userId);
    }

    /**
     * Каскадное удаление User -> Books
     */
    @Override
    public void removeUser(Long userId) {
        UserEntity user = getUser(userId);
        userMap.remove(userId);
    }
}
