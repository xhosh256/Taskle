package xhosh.dev.Taskle.unit;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import xhosh.dev.Taskle.entity.User;
import xhosh.dev.Taskle.jpa.repository.TaskRepository;
import xhosh.dev.Taskle.jpa.repository.UserRepository;
import xhosh.dev.Taskle.mapper.createupdate.TaskCreateUpdateMapper;
import xhosh.dev.Taskle.mapper.read.TaskReadMapper;
import xhosh.dev.Taskle.service.TaskService;


import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskReadMapper taskReadMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskCreateUpdateMapper taskCreateUpdateMapper;

    @InjectMocks
    private TaskService taskService;

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


//    @Test
//    void create() {
//        var createDto = new TaskCreateDto("title", "desc");
//        var taskEntity = new Task();
//        taskEntity.setId(1);
//        taskEntity.setTitle("title");
//        taskEntity.setDescription("desc");
//        var taskReadDto = new TaskReadDto(1, "title", "desc");
//        when(taskCreateUpdateMapper.map(createDto))
//                .thenReturn(taskEntity);
//
//        when(userRepository.findByUsername("user1"))
//                .thenReturn(Optional.of(testUser));
//
//        when(taskRepository.saveAndFlush(taskEntity))
//                .thenReturn(taskEntity);
//        when(taskReadMapper.map(taskEntity))
//                .thenReturn(taskReadDto);
//
//        taskService.create(createDto);
//
//        assertEquals(1, testUser.getTasks().size());
//
//        verify(userRepository).findByUsername("user1");
//        verify(taskCreateUpdateMapper).map(createDto);
//        verify(taskRepository).saveAndFlush(taskEntity);
//        verify(taskReadMapper).map(taskEntity);
//    }
//
//    @Test
//    void update() {
//        var updateDto = new TaskUpdateDto( "title2", "desc");
//
//        var taskEntity = new Task();
//        taskEntity.setId(1);
//        taskEntity.setTitle("title");
//        taskEntity.setDescription("desc");
//
//        var updated = new Task();
//        updated.setId(1);
//        updated.setTitle(updateDto.getTitle());
//        updated.setDescription(updateDto.getDescription());
//        var taskReadDto = new TaskReadDto(2, updated.getTitle(), updated.getDescription());
//
//
//
//        when(taskRepository.findById(1))
//                .thenReturn(Optional.of(taskEntity));
//        when(taskCreateUpdateMapper.map(taskEntity, updateDto))
//                .thenReturn(updated);
//
//        when(taskReadMapper.map(updated))
//                .thenReturn(taskReadDto);
//
//        var res = taskService.update(1, updateDto);
//
//        assertNotNull(res);
//        assertEquals("title2", res.getTitle());
//
//
//        verify(taskRepository).findById(1);
//        verify(taskCreateUpdateMapper).map(taskEntity, updateDto);
//        verify(taskReadMapper).map(updated);
//    }
}
