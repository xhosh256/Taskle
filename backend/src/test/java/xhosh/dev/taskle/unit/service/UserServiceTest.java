package xhosh.dev.taskle.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xhosh.dev.taskle.dto.UserCreateEditDto;
import xhosh.dev.taskle.dto.UserReadDto;
import xhosh.dev.taskle.entity.User;
import xhosh.dev.taskle.mapper.UserCreateEditDtoMapper;
import xhosh.dev.taskle.mapper.UserReadDtoMapper;
import xhosh.dev.taskle.repository.UserRepository;
import xhosh.dev.taskle.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserReadDtoMapper userReadDtoMapper;
    @Mock
    private UserCreateEditDtoMapper userCreateEditDtoMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void findAll() {
        User user1 = getUser(1L, "lenchik");

        User user2 = getUser(2L, "ivan");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        when(userReadDtoMapper.map(user1)).thenReturn(getUserReadDto(1L, "lenchik"));
        when(userReadDtoMapper.map(user2)).thenReturn(getUserReadDto(2L, "ivan"));

        List<UserReadDto> users = userService.findAll();

        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertThat(users).hasSize(2);
        assertThat(users)
                .extracting(UserReadDto::getUsername)
                .containsExactlyInAnyOrder("lenchik", "ivan");

        verify(userRepository).findAll();
        verify(userReadDtoMapper).map(user1);
        verify(userReadDtoMapper).map(user2);
    }

    @Test
    void findById() {
        User user = getUser(1L, "lenchik");

        when(userReadDtoMapper.map(user)).thenReturn(getUserReadDto(1L, "lenchik"));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<UserReadDto> lenchik = userService.findById(1L);

        assertTrue(lenchik.isPresent());
        lenchik.ifPresent(u -> assertEquals(1L, u.getId()));
        lenchik.ifPresent(u -> assertEquals("lenchik", u.getUsername()));

        verify(userRepository).findById(1L);
        verify(userReadDtoMapper).map(user);
    }

    @Test
    void update() {
        var user = getUser(1L, "lenchik");
        var editDto = getUserCreateEditDto("ivan");
        var updatedUser = getUser(1L, "ivan");
        var updatedUserReadDto = getUserReadDto(1L, "ivan");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userCreateEditDtoMapper.map(editDto, user)).thenReturn(updatedUser);
        when(userRepository.saveAndFlush(updatedUser)).thenReturn(updatedUser);
        when(userReadDtoMapper.map(updatedUser)).thenReturn(updatedUserReadDto);

        Optional<UserReadDto> updated = userService.update(1L, editDto);

        assertTrue(updated.isPresent());
        updated.ifPresent(u -> assertEquals(1L, u.getId()));
        updated.ifPresent(u -> assertEquals("ivan", u.getUsername()));

        verify(userRepository).saveAndFlush(updatedUser);
        verify(userReadDtoMapper).map(updatedUser);
        verify(userCreateEditDtoMapper).map(editDto, user);
        verify(userRepository).findById(1L);
    }

    @Test
    void delete() {
        var user = getUser(1L, "lenchik");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean result = userService.delete(1L);
        assertTrue(result);

        verify(userRepository).findById(1L);
        verify(userRepository).delete(user);
        verify(userRepository).flush();
    }

    private User getUser(Long id, String username) {
        var user = new User();
        user.setId(id);
        user.setUsername(username);
        return user;
    }

    private UserReadDto getUserReadDto(Long id, String username) {
        return new UserReadDto(id, username, null, null, null, null);
    }

    private UserCreateEditDto getUserCreateEditDto(String username) {
        return new UserCreateEditDto(username, null, null, null, null, null);
    }
}
