package xhosh.dev.taskle.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xhosh.dev.taskle.dto.UserCreateEditDto;
import xhosh.dev.taskle.dto.UserLoginDto;
import xhosh.dev.taskle.dto.UserReadDto;
import xhosh.dev.taskle.mapper.UserCreateEditDtoMapper;
import xhosh.dev.taskle.mapper.UserReadDtoMapper;
import xhosh.dev.taskle.repository.UserRepository;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private UserReadDtoMapper userReadDtoMapper;
    private UserCreateEditDtoMapper createEditDtoMapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @Transactional
    public UserReadDto register(UserCreateEditDto user) {
        return Optional.of(user)
                .map(createEditDtoMapper::map)
                .map(entity -> {
                    entity.setPassword(passwordEncoder.encode(entity.getPassword()));
                    return entity;})
                .map(userRepository::saveAndFlush)
                .map(userReadDtoMapper::map)
                .orElse(null);
    }

    public String verify(UserLoginDto loginDto) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );

            return jwtUtil.generateToken(loginDto.getUsername());
        } catch (Exception e) {
            return "Incorrect username or password";
        }
    }
}
