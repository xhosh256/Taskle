package xhosh.dev.Taskle.aop;

import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import xhosh.dev.Taskle.exception.exceptions.group.GroupCountExceeded;
import xhosh.dev.Taskle.exception.exceptions.group.GroupNotFoundException;
import xhosh.dev.Taskle.exception.exceptions.group.TaskAlreadyAddedException;
import xhosh.dev.Taskle.exception.exceptions.task.TaskNotFoundException;
import xhosh.dev.Taskle.jpa.repository.GroupRepository;
import xhosh.dev.Taskle.jpa.repository.TaskRepository;
import xhosh.dev.Taskle.jpa.repository.UserRepository;

@Aspect
@AllArgsConstructor
@Component
public class GroupAspect {

    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private TaskRepository taskRepository;

    @Pointcut("target(xhosh.dev.Taskle.http.rest.GroupController)")
    public void isGroupController() {}

    @Pointcut("@annotation(xhosh.dev.Taskle.aop.annotations.CheckSpace)")
    private void checkSpace(){}

    @Before("isGroupController() && checkSpace()")
    public void checkSpaceBefore() throws GroupCountExceeded {
        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication().getName();

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        if(!(user.getGroups().size() < 5)) {
            throw new GroupCountExceeded("You've created too much groups");
        }
    }

    @Pointcut("@annotation(xhosh.dev.Taskle.aop.annotations.CheckIfAdded)")
    public void checkIfAdded() {}

    @Around("isGroupController() && checkIfAdded() && args(groupId, taskId, ..)")
    public Object checkIfTaskWasAlreadyAddedToGroup(ProceedingJoinPoint pjp, Integer groupId, Integer taskId) throws Throwable {
        var group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group not found"));

        for (var groupTask: group.getTasks()) {
            if (groupTask.getTask().getId().equals(taskId)) {
                throw new TaskAlreadyAddedException("Task has been already added yet");
            }
        }

        return pjp.proceed();
    }
}
