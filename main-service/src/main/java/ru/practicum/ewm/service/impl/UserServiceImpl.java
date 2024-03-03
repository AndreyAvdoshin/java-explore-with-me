package ru.practicum.ewm.service.impl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.NewUserRequest;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.UniqueViolationException;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.model.UserMapper;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public UserDto createUser(NewUserRequest userRequest) {
        User user = UserMapper.toUser(userRequest);
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        PageRequest page = PageRequest.of(from / size, size);
        Page<User> users = ids == null ? repository.findAll(page) : repository.findAllByIdIn(ids, page);
        return users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(long userId) {
        if (!repository.existsById(userId)) {
            throw new NotFoundException("User", userId);
        }
        repository.deleteById(userId);
    }

    @Override
    public User returnIfExists(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User", userId));
    }

    @Override
    public void checkExistingUser(Long userId) {
        boolean exist = repository.existsById(userId);
        if (!exist) {
            throw new NotFoundException("User", userId);
        }
    }
}
