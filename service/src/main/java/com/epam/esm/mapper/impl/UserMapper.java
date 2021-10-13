package com.epam.esm.mapper.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapper implements Mapper<User, UserDto> {
    @Override
    public UserDto mapToDto(User entity) {
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setName(entity.getName());
        return userDto;
    }

    @Override
    public User mapToEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        return user;
    }
}
