package xhosh.dev.Taskle.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xhosh.dev.Taskle.dto.group.GroupCreateDto;
import xhosh.dev.Taskle.dto.group.GroupReadDto;
import xhosh.dev.Taskle.dto.response.TaskReadDto;
import xhosh.dev.Taskle.entity.*;
import xhosh.dev.Taskle.exception.exceptions.group.GroupNotFoundException;
import xhosh.dev.Taskle.exception.exceptions.task.TaskNotFoundException;
import xhosh.dev.Taskle.jpa.repository.GroupRepository;
import xhosh.dev.Taskle.jpa.repository.GroupTaskRepository;
import xhosh.dev.Taskle.jpa.repository.TaskRepository;
import xhosh.dev.Taskle.jpa.repository.UserRepository;
import xhosh.dev.Taskle.mapper.createupdate.GroupCreateMapper;
import xhosh.dev.Taskle.mapper.read.GroupReadMapper;
import xhosh.dev.Taskle.mapper.read.TaskReadMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private GroupCreateMapper groupCreateMapper;
    private GroupReadMapper groupReadMapper;
    private TaskRepository taskRepository;
    private GroupTaskRepository groupTaskRepository;
    private JPAQueryFactory queryFactory;
    private TaskReadMapper taskReadMapper;

    @Transactional
    public GroupReadDto create(GroupCreateDto group) {
        String auth = SecurityContextHolder
                .getContext().getAuthentication().getName();
        var user = userRepository.findByUsername(auth)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        Group entity = groupCreateMapper.map(group);
        entity.setUser(user);

        groupRepository.saveAndFlush(entity);
        return groupReadMapper.map(entity);
    }

    @Transactional
    public void delete(Integer id) {
        var group = groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group not found"));

        groupRepository.delete(group);
    }

    public Map<String, List<GroupReadDto>> findAll() {
        Map<String, List<GroupReadDto>> response = new HashMap<>();

        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        List<Group> groupEntities = groupRepository.findAllByUsername(username);

        response.put("groups", groupEntities.stream()
                .map(groupReadMapper::map).toList());
        return response;
    }

    public GroupReadDto findById(Integer id) {
        var group = groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group not found"));
        return groupReadMapper.map(group);
    }

    @Transactional
    public void addTask(Integer groupId, Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group not found"));

        group.addTask(task);
    }

    @Transactional
    public void deleteTask(Integer groupId, Integer taskId) {

        GroupTask groupTask = groupTaskRepository.findByTask_IdAndGroup_Id(taskId, groupId)
                .orElseThrow(() -> new NoSuchElementException("No Such relation between task and group"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group not found"));

        task.removeGroup(groupTask);
        group.removeTask(groupTask);
    }

    public List<TaskReadDto> findAllNotInGroup(Integer groupId) {
        String auth = SecurityContextHolder
                .getContext().getAuthentication().getName();

        QTask task = QTask.task;
        QGroupTask groupTask = QGroupTask.groupTask;

        return queryFactory
                .selectFrom(task)
                .where(task.user.username.eq(auth)
                        .and(task.notIn(
                                JPAExpressions
                                        .select(groupTask.task)
                                        .from(groupTask)
                                        .where(groupTask.group.id.eq(groupId))
                        ))
                )
                .fetch()
                .stream()
                .map(taskReadMapper::map)
                .toList();
    }
}
