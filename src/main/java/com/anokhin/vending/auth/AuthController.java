package com.anokhin.vending.auth;

import com.anokhin.vending.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.anokhin.vending.auth.dto.LoginRequest;
import com.anokhin.vending.auth.dto.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }
}
