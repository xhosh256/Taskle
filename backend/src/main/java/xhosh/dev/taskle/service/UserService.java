package xhosh.dev.taskle.service;

import org.springframework.stereotype.Service;
import xhosh.dev.taskle.dto.UserCreateEditDto;
import xhosh.dev.taskle.dto.UserReadDto;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public List<UserReadDto> findAll() {
        return null;
    }

//    private List<UserReadDto> findAll(UserFilter filter) {
//
//    }

    public Optional<UserReadDto> findById(Long id) {
        return Optional.empty();
    }

    public UserReadDto create(UserCreateEditDto createDto) {
        return null;
    }

    public Optional<UserReadDto> update(Long id, UserCreateEditDto editDto) {
        return Optional.empty();
    }

    public boolean delete(Long id) {
        return false;
    }
}
