package xhosh.dev.Taskle.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xhosh.dev.Taskle.dto.tasks.TaskCreateDto;
import xhosh.dev.Taskle.dto.tasks.TaskFilter;
import xhosh.dev.Taskle.dto.tasks.TaskUpdateDto;
import xhosh.dev.Taskle.dto.response.TaskReadDto;
import xhosh.dev.Taskle.entity.Task;
import xhosh.dev.Taskle.entity.User;
import xhosh.dev.Taskle.exception.exceptions.task.TaskNotFoundException;
import xhosh.dev.Taskle.jpa.repository.TaskRepository;
import xhosh.dev.Taskle.jpa.repository.UserRepository;
import xhosh.dev.Taskle.mapper.createupdate.TaskCreateUpdateMapper;
import xhosh.dev.Taskle.mapper.read.TaskReadMapper;

import static xhosh.dev.Taskle.entity.QTask.task;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class TaskService {

    private final String ID_PREFIX = "tasks::v1::";

    private TaskRepository taskRepository;
    private TaskReadMapper taskReadMapper;
    private UserRepository userRepository;
    private TaskCreateUpdateMapper taskCreateUpdateMapper;

    public Page<TaskReadDto> findAll(TaskFilter filter, Pageable pageable) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();

        BooleanExpression predicate = task.user.username.eq(username);

        if(filter.getTitle() != null && !filter.getTitle().isBlank()) {
            predicate = predicate.and(task.title.like("%" + filter.getTitle() + "%"));
        }

        if(filter.getTags() != null && !filter.getTags().isEmpty()) {
            for (var tag: filter.getTags()) {
                predicate = predicate.and(task.tags.any().tag.name.eq(tag));
            }
        }

        Page<Task> tasks = taskRepository.findAll(predicate, pageable);
        return tasks.map(taskReadMapper::map);
    }

    @Cacheable(value = "tasks", key = "'v1::' + #taskId", cacheManager = "longTTLCacheManager")
    public TaskReadDto findById(Integer taskId) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        return taskReadMapper.map(task);
    }

    @Transactional
    public TaskReadDto create(TaskCreateDto task) {
        Task entity = taskCreateUpdateMapper.map(task);
        var username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        user.addTask(entity);
        taskRepository.saveAndFlush(entity);
        return taskReadMapper.map(entity);
    }

    @Transactional
    @CachePut(value = "tasks", key = "'v1::' + #taskId", cacheManager = "shortTTLCacheManager")
    public TaskReadDto update(Integer taskId, TaskUpdateDto task) {

        var entity = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        Task updated = taskCreateUpdateMapper.map(entity, task);
        taskRepository.flush();
        return taskReadMapper.map(updated);
    }

    @Transactional
    @CacheEvict(value = "tasks", key = "'v1::' + #taskId")
    public void delete(Integer taskId) {

        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskRepository.delete(task);
    }
}
