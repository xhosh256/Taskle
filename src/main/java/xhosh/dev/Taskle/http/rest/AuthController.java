package xhosh.dev.Taskle.http.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xhosh.dev.Taskle.dto.auth.LoginDto;
import xhosh.dev.Taskle.dto.response.LoginResponseDto;
import xhosh.dev.Taskle.dto.auth.UserCreateDto;
import xhosh.dev.Taskle.dto.response.UserReadDto;
import xhosh.dev.Taskle.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@ResponseBody
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    public UserReadDto register(@Valid @RequestBody UserCreateDto user) {
        return authService.create(user);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginDto user) {
        return authService.verify(user);
    }
}
