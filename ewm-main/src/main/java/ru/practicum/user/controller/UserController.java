package ru.practicum.user.controller;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.UserDroRequest;
import ru.practicum.user.dto.UserDtoResponse;
import ru.practicum.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RequestMapping("admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDtoResponse> create(@Valid @RequestBody UserDroRequest userDroRequest){
        log.debug("POST create() with {}", userDroRequest);
        UserDtoResponse e = userService.create(userDroRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(e);
    }

    @GetMapping
    public ResponseEntity<List<UserDtoResponse>> find(
            @RequestParam(value = "ids", required = false) List<Long> userIds,
            @PositiveOrZero @RequestParam(value = "from",defaultValue = "0") Integer from,
            @Positive @RequestParam(value = "size", defaultValue = "10") Integer size){
        log.debug("GET find() with userIds: {}, from: {}, size: {}",userIds,from,size);
        List<UserDtoResponse> e = userService.find(userIds,from,size);
        return ResponseEntity.status(HttpStatus.OK).body(e);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> remove(@PathVariable("userId") Long userId){
        log.debug("DELETE remove() with userId: {}", userId);
        userService.remove(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
