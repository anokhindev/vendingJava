package com.anokhin.vending.auth;

import com.anokhin.vending.auth.dto.LoginRequest;
import com.anokhin.vending.auth.dto.RegisterRequest;
import com.anokhin.vending.auth.entity.User;
import com.anokhin.vending.auth.entity.Role;
import com.anokhin.vending.auth.repository.UserRepository;
import com.anokhin.vending.common.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<ApiResponse<Object>> register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(HttpStatus.BAD_REQUEST, "Пользователь существует", null));
        }

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // TODO: Захешировать пароль
        user.setRole(Role.BUYER);

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED,"Пользователь зарегистрирован", null));
    }

    public ResponseEntity<ApiResponse<Object>> login(LoginRequest request) {

        return userRepository.findByUsername(request.getUsername())
                .map(user -> {
                    if (user.getPassword().equals(request.getPassword())) { // TODO: Проверка хеша
                        return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(new ApiResponse<>(HttpStatus.OK, "Пользователь существует", null));
                    } else {
                        return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(new ApiResponse<>(HttpStatus.BAD_REQUEST, "Неверный пароль", null));
                    }
                })
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(HttpStatus.BAD_REQUEST, "Пользователь не найден", null)));
    }
}
