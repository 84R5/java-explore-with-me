package ru.practicum.user.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDtoResponse;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.util.PageCalculate;
import ru.practicum.util.ValidateManager;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Override
    public List<UserDtoResponse> find(List<Long> userIds, Integer from, Integer size) {
        Pageable pageable = PageCalculate.getPage(from, size);

        Page<User> page = Objects.nonNull(userIds) && !userIds.isEmpty()
                ? userRepository.findAllByIdIn(userIds, pageable)
                : userRepository.findAll(pageable);
        log.debug("Found {}: {}.", User.class.getSimpleName(), page.getTotalElements());

        return page.stream().map(UserMapper::userToUserDtoResponse).collect(Collectors.toList());
    }

    @Override
    public UserDtoResponse create(NewUserRequest newUserRequest) {

        if (userRepository.existsByName(newUserRequest.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User name is already used.");
        }
        return UserMapper.userToUserDtoResponse(userRepository.save(UserMapper.toUser(newUserRequest)));
    }

    @Override
    public void remove(Long userId) {
        ValidateManager.checkId(userRepository, userId);
        userRepository.deleteById(userId);
    }
}