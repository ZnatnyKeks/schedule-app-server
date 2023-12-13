package com.ru.dating.controllers;

import com.ru.dating.entities.user.UserDto;
import com.ru.dating.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    UserService service;

    @PostMapping("/next-pair")
    public ResponseEntity<UserDto> getNextPair(@PathVariable String userId) {
        return ResponseEntity.ok(service.getNextPair(userId));
    }
}
