package xhosh.dev.Taskle.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
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

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final String ID_PREDIX = "users:v1:";

    private UserRepository userRepository;
    private UserReadMapper userReadMapper;
    private UserCreateUpdateMapper userCreateUpdateMapper;
    private RedisTemplate<String, UserReadDto> userReadDtoRedisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username %s not found".formatted(username)));


        return new UserDetailsDto(user);
    }

    public UserReadDto findMe() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        final String ID = ID_PREDIX + username;

        var cachedUserReadDto = userReadDtoRedisTemplate.opsForValue().get(ID);

        if(cachedUserReadDto != null) {
            log.info("Fetched user read dto from cache: {}", ID);
            return cachedUserReadDto;
        }

        log.info("Failed retrieving user from cache");
        var userReadDto = userRepository.findByUsername(username)
                .map(userReadMapper::map)
                .orElseThrow(() -> new UsernameNotFoundException("Username %s not found".formatted(username)));
        userReadDtoRedisTemplate.opsForValue().set(ID, userReadDto, 10, TimeUnit.MINUTES);
        log.info("Successfully put user in cache: {}", ID);
        return userReadDto;
    }

    public UserReadDto findByUsername(String username) {
        final String ID = ID_PREDIX + username;

        var cachedUserReadDto = userReadDtoRedisTemplate.opsForValue().get(ID);

        if(cachedUserReadDto != null) {
            log.info("Fetched user read dto from cache: {}", ID);
            return cachedUserReadDto;
        }

        log.info("Failed retrieving user from cache");
        var userReadDto = userRepository.findByUsername(username)
                .map(userReadMapper::map)
                .orElseThrow(() -> new UsernameNotFoundException("Username %s not found".formatted(username)));
        userReadDtoRedisTemplate.opsForValue().set(ID, userReadDto, 10, TimeUnit.MINUTES);
        log.info("Successfully put user in cache: {}", ID);
        return userReadDto;
    }

    @Transactional
    public UserReadDto update(UserUpdateDto updateDto) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        final String ID = ID_PREDIX + username;

        var toUpdate = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username %s not found".formatted(username)));
        var updated = userCreateUpdateMapper.map(updateDto, toUpdate);
        userRepository.flush();
        var userReadDto = userReadMapper.map(updated);

        log.info("Updated cache for: {}", userReadDto);
        userReadDtoRedisTemplate.opsForValue().set(ID, userReadDto, 10, TimeUnit.MINUTES);
        return userReadDto;
    }

    @Transactional
    public void delete() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        final String ID = ID_PREDIX + username;

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username %s not found".formatted(username)));
        userRepository.delete(user);

        userReadDtoRedisTemplate.delete(ID);
    }
}