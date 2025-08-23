package xhosh.dev.taskle.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xhosh.dev.taskle.dto.UserCreateEditDto;
import xhosh.dev.taskle.dto.UserReadDto;
import xhosh.dev.taskle.mapper.UserCreateEditDtoMapper;
import xhosh.dev.taskle.mapper.UserReadDtoMapper;
import xhosh.dev.taskle.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private UserRepository userRepository;
    private UserReadDtoMapper userReadDtoMapper;
    private UserCreateEditDtoMapper userCreateEditDtoMapper;

    public List<UserReadDto> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(userReadDtoMapper::map)
                .toList();
    }

//    private List<UserReadDto> findAll(UserFilter filter) {
//
//    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository
                .findById(id)
                .map(userReadDtoMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto createDto) {
        return Optional.of(createDto)
                .map(userCreateEditDtoMapper::map)
                .map(userRepository::save)
                .map(userReadDtoMapper::map)
                .orElse(null);
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto editDto) {
        return userRepository.findById(id)
                .map(u -> userCreateEditDtoMapper.map(editDto, u))
                .map(userRepository::saveAndFlush)
                .map(userReadDtoMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(u ->
                {
                    userRepository.delete(u);
                    userRepository.flush();
                    return true;
                }).orElse(false);
    }
}
