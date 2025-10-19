package xhosh.dev.Taskle.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xhosh.dev.Taskle.dto.auth.LoginDto;
import xhosh.dev.Taskle.dto.auth.UserCreateDto;
import xhosh.dev.Taskle.dto.response.LoginResponseDto;
import xhosh.dev.Taskle.dto.response.UserReadDto;
import xhosh.dev.Taskle.entity.User;
import xhosh.dev.Taskle.jpa.repository.UserRepository;
import xhosh.dev.Taskle.mapper.createupdate.UserCreateUpdateMapper;
import xhosh.dev.Taskle.mapper.read.UserReadMapper;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private UserCreateUpdateMapper userCreateUpdateMapper;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private UserReadMapper userReadMapper;
    private AuthenticationManager authManager;
    private JwtUtil jwtUtil;


    @Transactional
    public UserReadDto create(UserCreateDto user) {
        log.info("REGISTER DTO: {}", user);

        User userEntity = userCreateUpdateMapper.map(user);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userRepository.saveAndFlush(userEntity);
        return userReadMapper.map(userEntity);
    }

    @Transactional
    public LoginResponseDto verify(LoginDto user) {

        Authentication auth =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        var userInfo = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found")).getProfile();
        return new LoginResponseDto(
                userInfo.getId(),
                userInfo.getUser().getUsername(),
                userInfo.getFirstname(),
                userInfo.getLastname(),
                jwtUtil.generateToken(auth.getName())
        );
    }
}
