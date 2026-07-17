package dev.arthuroliv.esmaltei.security;

import dev.arthuroliv.esmaltei.dto.request.LoginRequest;
import dev.arthuroliv.esmaltei.dto.response.LoginResponse;
import dev.arthuroliv.esmaltei.security.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }
}
