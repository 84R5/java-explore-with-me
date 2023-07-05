package ru.practicum.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.user.dto.UserDroRequest;
import ru.practicum.user.dto.UserDtoResponse;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.util.PageCalc;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDtoResponse create(UserDroRequest userDroRequest) {
        User user = userRepository.save(UserMapper.userRequestDtoToUser(userDroRequest));
        log.debug("New {} save: {}", user.getClass().getSimpleName(), user);
        return UserMapper.userToUserDtoResponse(user);
    }

    @Override
    public List<UserDtoResponse> find(List<Long> userIds, Integer from, Integer size) {
        Pageable pageable = PageCalc.getPage(from, size);

        Page<User> page = Objects.nonNull(userIds) && !userIds.isEmpty()
                ? userRepository.findById(userIds, pageable)
                : userRepository.findAll(pageable);
        log.debug("Found {}: {}.", User.class.getSimpleName(), page.getTotalElements());
        return page.stream().map(UserMapper::userToUserDtoResponse).collect(Collectors.toList());
    }

    @Override
    public void remove(Long userId) {
        userRepository.deleteById(userId);
    }
}
