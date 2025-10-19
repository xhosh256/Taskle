package xhosh.dev.Taskle.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xhosh.dev.Taskle.dto.auth.UserDetailsDto;
import xhosh.dev.Taskle.dto.response.UserReadDto;
import xhosh.dev.Taskle.dto.user.UserUpdateDto;
import xhosh.dev.Taskle.entity.User;
import xhosh.dev.Taskle.jpa.repository.UserRepository;
import xhosh.dev.Taskle.mapper.createupdate.UserCreateUpdateMapper;
import xhosh.dev.Taskle.mapper.read.UserReadMapper;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private UserReadMapper userReadMapper;
    private UserCreateUpdateMapper userCreateUpdateMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username %s not found".formatted(username)));


        return new UserDetailsDto(user);
    }

    public UserReadDto findMe() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username %s not found".formatted(username)));
        return userReadMapper.map(user);
    }

    public UserReadDto findByUsername(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username %s not found".formatted(username)));
        return userReadMapper.map(user);
    }

    @Transactional
    public UserReadDto update(UserUpdateDto updateDto) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();

        var toUpdate = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username %s not found".formatted(username)));

        var updated = userCreateUpdateMapper.map(updateDto, toUpdate);
        userRepository.flush();
        return userReadMapper.map(updated);
    }

    @Transactional
    public void delete() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username %s not found".formatted(username)));
        userRepository.delete(user);
    }
}
