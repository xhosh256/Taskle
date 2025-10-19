package xhosh.dev.Taskle.aop;

import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;
import xhosh.dev.Taskle.exception.exceptions.group.GroupAccessDeniedException;
import xhosh.dev.Taskle.exception.exceptions.group.GroupNotFoundException;
import xhosh.dev.Taskle.exception.exceptions.task.TaskAccessException;
import xhosh.dev.Taskle.exception.exceptions.task.TaskNotFoundException;
import xhosh.dev.Taskle.jpa.repository.GroupRepository;
import xhosh.dev.Taskle.jpa.repository.TaskRepository;

@Aspect
@AllArgsConstructor
@Component
public class SecurityAspect {

    private TaskRepository taskRepository;
    private GroupRepository groupRepository;

    @Pointcut("@annotation(xhosh.dev.Taskle.aop.annotations.IsOwnTask)")
    public void checkOwner() {}

    @Around("target(xhosh.dev.Taskle.http.rest.TaskController) && checkOwner() && args(taskId, ..)")
    public Object checkIfOwner(ProceedingJoinPoint point, Integer taskId) throws Throwable {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if(task.getUser().getUsername().equals(auth.getName())) {
            return point.proceed();
        }

        throw new TaskAccessException("Trying to get access to alien task");
    }

    @Pointcut("@annotation(xhosh.dev.Taskle.aop.annotations.IsOwnGroup)")
    public void checkGroupOwner() {}

    @Around("target(xhosh.dev.Taskle.http.rest.GroupController) && checkGroupOwner() && args(groupId, ..)")
    public Object checkIfGroupOwner(ProceedingJoinPoint pjp, Integer groupId) throws Throwable {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        var group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group not found"));

        if(group.getUser().getUsername().equals(auth.getName())) {
            return pjp.proceed();
        }

        throw new GroupAccessDeniedException("Trying to access to alien group");
    }

    @Pointcut("@annotation(xhosh.dev.Taskle.aop.annotations.IsTaskAndGroupOwner)")
    public void checkAnyOwner() {}

    @Around("target(xhosh.dev.Taskle.http.rest.GroupController) && checkAnyOwner() && args(groupId, taskId, ..)")
    public Object checkIfAnyOwner(ProceedingJoinPoint pjp, Integer groupId, Integer taskId) throws Throwable {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        var group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group not found"));
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if(auth.getName().equals(group.getUser().getUsername()) && auth.getName().equals(task.getUser().getUsername())) {
            return pjp.proceed();
        }

        throw new GroupAccessDeniedException("trying to access align resource");
    }
}

