package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.NewUserRequest;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.model.User;

import java.util.List;

public interface UserService {

    UserDto createUser(NewUserRequest userRequest);

    List<UserDto> getUsers(List<Long> ids, int from, int size);

    User returnIfExists(Long userId);

    void deleteUser(long userId);
}
