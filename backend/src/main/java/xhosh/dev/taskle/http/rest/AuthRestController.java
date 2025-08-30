package xhosh.dev.taskle.http.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import xhosh.dev.taskle.dto.UserCreateEditDto;
import xhosh.dev.taskle.dto.UserLoginDto;
import xhosh.dev.taskle.dto.UserReadDto;
import xhosh.dev.taskle.service.AuthService;

@RestController
@AllArgsConstructor
public class AuthRestController {

    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto register(@RequestBody UserCreateEditDto createDto) {
        return authService.register(createDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDto loginDto) {
        return authService.verify(loginDto);
    }

}
