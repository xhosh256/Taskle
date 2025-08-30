package xhosh.dev.taskle.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xhosh.dev.taskle.dto.*;
import xhosh.dev.taskle.entity.QUser;
import xhosh.dev.taskle.mapper.UserCreateEditDtoMapper;
import xhosh.dev.taskle.mapper.UserReadDtoMapper;
import xhosh.dev.taskle.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

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

    public Page<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(filter.getFirstname(), QUser.user.profile.firstname::containsIgnoreCase)
                .add(filter.getLastname(), QUser.user.profile.lastname::containsIgnoreCase)
                .add(filter.getBirthDate(), QUser.user.profile.birthDate::before)
                .build();

        return userRepository.findAll(predicate, pageable)
                .map(userReadDtoMapper::map);
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository
                .findById(id)
                .map(userReadDtoMapper::map);
    }

//    @Transactional
//    public UserReadDto create(UserCreateEditDto createDto) {
//        return Optional.of(createDto)
//                .map(userCreateEditDtoMapper::map)
//                .map(userRepository::save)
//                .map(userReadDtoMapper::map)
//                .orElse(null);
//    }

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User with username %s not found".formatted(username));
        }

        return new UserDetailsDto(user.get());
    }
}
