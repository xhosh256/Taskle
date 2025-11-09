package xhosh.dev.Taskle.http.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xhosh.dev.Taskle.aop.annotations.IsOwnTask;
import xhosh.dev.Taskle.dto.tasks.TaskCreateDto;
import xhosh.dev.Taskle.dto.tasks.TaskFilter;
import xhosh.dev.Taskle.dto.tasks.TaskUpdateDto;
import xhosh.dev.Taskle.dto.response.TaskReadDto;
import xhosh.dev.Taskle.service.TaskService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
@ResponseBody
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    @GetMapping
    public Page<TaskReadDto> findAll(
            TaskFilter filter,
            @PageableDefault(size = 5, sort = "createdAt") Pageable pageable) {

        return taskService.findAll(filter, pageable);
    }

    @PostMapping
    public TaskReadDto create(@Valid @RequestBody TaskCreateDto task) {
        return taskService.create(task);
    }

    @GetMapping("/{taskId}")
    @IsOwnTask
    public TaskReadDto findById(@PathVariable("taskId") Integer taskId) {
        return taskService.findById(taskId);
    }

    @PutMapping("/{taskId}")
    @IsOwnTask
    public TaskReadDto update(@PathVariable("taskId") Integer taskId,
                              @Valid @RequestBody TaskUpdateDto task) {

        return taskService.update(taskId, task);
    }

    @DeleteMapping("/{taskId}")
    @IsOwnTask
    public ResponseEntity<Map<String, String>> delete(@PathVariable("taskId") Integer taskId) {

        taskService.delete(taskId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "TASK WAS SUCCESSFULLY DELETED");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/remain")
    public ResponseEntity<Map<String, Integer>> getRemain() {
        Integer tasks = taskService.getRemain();
        Map<String, Integer> response = new HashMap<>();
        response.put("count", tasks);
        return ResponseEntity.ok(response);
    }
}
