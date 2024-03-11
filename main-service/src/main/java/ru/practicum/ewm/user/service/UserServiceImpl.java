package ru.practicum.ewm.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.UserMapper;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public UserDto createUser(NewUserRequest userRequest) {
        User user = UserMapper.toUser(userRequest);

        log.info("Создание пользователя - {}", user);
        return UserMapper.toUserDto(repository.save(user));
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        PageRequest page = PageRequest.of(from / size, size);
        List<User> users = ids == null ? repository.findAll(page).getContent() : repository.findAllByIdIn(ids, page);

        log.info("Возврат пользователей - {}", users);
        return users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(long userId) {
        if (!repository.existsById(userId)) {
            throw new NotFoundException("User", userId);
        }

        log.info("Удаление пользователя по id - {}", userId);
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
