package xhosh.dev.Taskle.http.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xhosh.dev.Taskle.aop.annotations.*;
import xhosh.dev.Taskle.dto.group.GroupCreateDto;
import xhosh.dev.Taskle.dto.group.GroupReadDto;
import xhosh.dev.Taskle.dto.response.TaskReadDto;
import xhosh.dev.Taskle.entity.QGroup;
import xhosh.dev.Taskle.service.GroupService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/groups")
@ResponseBody
@AllArgsConstructor
public class GroupController {

    private GroupService groupService;

    @GetMapping
    public Map<String, List<GroupReadDto>> findAll() {
        return groupService.findAll();
    }

    @GetMapping("/{groupId}")
    @IsOwnGroup
    public GroupReadDto findById(@PathVariable("groupId") Integer groupId) {
        return groupService.findById(groupId);
    }

    @PostMapping
    @CheckSpace
    public GroupReadDto create(@RequestBody GroupCreateDto group) {
        return groupService.create(group);
    }

    @DeleteMapping("/{groupId}")
    @IsOwnGroup
    public ResponseEntity<Map<String, String>> delete(@PathVariable("groupId") Integer groupId) {
        groupService.delete(groupId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "GROUP WAS SUCCESSFULLY DELETED");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{groupId}/tasks/{taskId}")
    @IsTaskAndGroupOwner
    @CheckIfAdded
    public ResponseEntity<Map<String, String>> addTask(@PathVariable("groupId") Integer groupId, @PathVariable("taskId") Integer taskId) {
        groupService.addTask(groupId, taskId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "TASK WAS SUCCESSFULLY ADDED");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{groupId}/tasks/{taskId}")
    @IsTaskAndGroupOwner
    @IsTaskInGroup
    public ResponseEntity<Map<String, String>> deleteTask(@PathVariable("groupId") Integer groupId, @PathVariable("taskId") Integer taskId) {
        groupService.deleteTask(groupId, taskId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "TASK WAS SUCCESSFULLY REMOVED");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{groupId}/tasks")
    @IsOwnGroup
    public List<TaskReadDto> findAllNotInGroup(@PathVariable("groupId") Integer groupId) {
        return groupService.findAllNotInGroup(groupId);
    }

}