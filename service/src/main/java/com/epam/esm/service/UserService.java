package com.epam.esm.service;

import com.epam.esm.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll(int page, int size);
    UserDto find(Long id);
}
