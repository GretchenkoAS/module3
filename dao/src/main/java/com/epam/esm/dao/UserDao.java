package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAll(int page, int size);
    Optional<User> findOne(Long id);
}
