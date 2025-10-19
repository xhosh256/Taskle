package xhosh.dev.Taskle.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import xhosh.dev.Taskle.dto.group.GroupCreateDto;
import xhosh.dev.Taskle.dto.group.GroupReadDto;
import xhosh.dev.Taskle.entity.Group;
import xhosh.dev.Taskle.entity.User;
import xhosh.dev.Taskle.jpa.repository.GroupRepository;
import xhosh.dev.Taskle.jpa.repository.UserRepository;
import xhosh.dev.Taskle.mapper.createupdate.GroupCreateMapper;
import xhosh.dev.Taskle.mapper.read.GroupReadMapper;
import xhosh.dev.Taskle.service.GroupService;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GroupCreateMapper groupCreateMapper;

    @Mock
    private GroupReadMapper groupReadMapper;

    @InjectMocks
    private GroupService groupService;

    private User testUser;

    @BeforeEach
    void setUp() {
        Authentication auth = mock(Authentication.class);
        MockitoAnnotations.openMocks(this);
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn("user1");
        SecurityContextHolder.setContext(context);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("user1");
        testUser.setPassword("pass");
    }

    @Test
    void create() {
        GroupCreateDto createDto =
                new GroupCreateDto("group1");
        Group groupEntity = new Group();
        groupEntity.setName("group1");
        GroupReadDto readDto =
                new GroupReadDto(1, "group1", new ArrayList<>());

        when(userRepository.findByUsername("user1"))
                .thenReturn(Optional.of(testUser));
        when(groupCreateMapper.map(createDto))
                .thenReturn(groupEntity);
        when(groupRepository.saveAndFlush(groupEntity))
                .thenReturn(groupEntity);
        when(groupReadMapper.map(groupEntity))
                .thenReturn(readDto);

        var res = groupService.create(createDto);

        assertNotNull(res);
        assertEquals(1, res.getId());
        assertEquals("group1", res.getName());
        assertEquals(0, res.getTasks().size());

        verify(userRepository).findByUsername("user1");
        verify(groupCreateMapper).map(createDto);
        verify(groupRepository).saveAndFlush(groupEntity);
        verify(groupReadMapper).map(groupEntity);
    }

    @Test
    void delete() {
        Group groupEntity = new Group();
        groupEntity.setName("group1");

        when(groupRepository.findById(1))
                .thenReturn(Optional.of(groupEntity));

        groupService.delete(1);
        verify(groupRepository).delete(groupEntity);
    }

    @Test
    void findById() {
        Group groupEntity = new Group();
        groupEntity.setName("group1");
        GroupReadDto readDto =
                new GroupReadDto(1, "group1", new ArrayList<>());


        when(groupRepository.findById(1))
                .thenReturn(Optional.of(groupEntity));
        when(groupReadMapper.map(groupEntity))
                .thenReturn(readDto);

        var res = groupService.findById(1);
        assertNotNull(res);
        assertNotNull(res);
        assertEquals(1, res.getId());
        assertEquals("group1", res.getName());
        assertEquals(0, res.getTasks().size());


        verify(groupReadMapper).map(groupEntity);
    }
}
