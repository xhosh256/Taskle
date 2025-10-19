package xhosh.dev.Taskle.unit;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xhosh.dev.Taskle.dto.response.UserReadDto;
import xhosh.dev.Taskle.dto.user.UserUpdateDto;
import xhosh.dev.Taskle.entity.Role;
import xhosh.dev.Taskle.entity.User;
import xhosh.dev.Taskle.jpa.repository.UserRepository;
import xhosh.dev.Taskle.mapper.createupdate.UserCreateUpdateMapper;
import xhosh.dev.Taskle.mapper.read.UserReadMapper;
import xhosh.dev.Taskle.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCreateUpdateMapper userCreateUpdateMapper;

    @Mock
    private UserReadMapper userReadMapper;

    @InjectMocks
    private UserService userService;

    private User testUser;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);

        var user = new User();
        user.setUsername("TEST");
        user.setPassword("testpassword");
        user.setRole(Role.USER);
        this.testUser = user;
    }

    @Test
    void update() {
        when(userRepository.findByUsername("TEST"))
                .thenReturn(Optional.of(this.testUser));
        UserUpdateDto updateDto = new UserUpdateDto("test1", "somelastname");
        User updated = new User();
        updated.setUsername("test1");
        updated.setPassword("somepassword");
        when(userCreateUpdateMapper.map(updateDto, this.testUser))
                .thenReturn(updated);
        when(userReadMapper.map(updated))
                .thenReturn(new UserReadDto(1L, "test1", "somepassword", null, null));


        var userReadDto = userService.update(updateDto);

        assertNotNull(userReadDto);
        assertEquals("test1", userReadDto.getUsername());
        verify(userRepository).findByUsername("TEST");
        verify(userCreateUpdateMapper).map(updateDto, testUser);
        verify(userReadMapper).map(updated);
    }

    @Test
    void delete() {
        when(userRepository.findByUsername("TEST"))
                .thenReturn(Optional.of(this.testUser));

        userService.delete();

        verify(userRepository).findByUsername("TEST");
        verify(userRepository).delete(this.testUser);
    }
}
