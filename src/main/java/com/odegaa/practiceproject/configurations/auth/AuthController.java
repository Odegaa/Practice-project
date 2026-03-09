package com.odegaa.practiceproject.configurations.auth;

import com.odegaa.practiceproject.advice.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "0. Authentication & Registration",
        description = "Endpoints for user login and registration. These do not require a JWT token.")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "User Login",
            description = "Authenticates user and returns a JWT token. Used by: " +
                    "ALL ROLES (Director, HR, Employee and e.t.c.)."
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody @Valid AuthDTO authDTO) {
        return ResponseEntity.ok(authService.login(authDTO));
    }
}
