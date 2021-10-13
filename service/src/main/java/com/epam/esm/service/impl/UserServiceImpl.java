package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import com.epam.esm.mapper.impl.UserMapper;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String ID = "id";
    private final UserDao userDao;
    private final UserMapper mapper;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserMapper mapper) {
        this.userDao = userDao;
        this.mapper = mapper;
    }

    @Override
    public List<UserDto> findAll(int page, int size) {
        List<User> users = userDao.findAll(page, size);
        return users.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());    }

    @Override
    public UserDto find(Long id) {
        Optional<User> userOptional = userDao.findOne(id);
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND, ID, id);
        }
        return mapper.mapToDto(userOptional.get());    }
}
