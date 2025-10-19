package xhosh.dev.Taskle.mapper.read;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xhosh.dev.Taskle.dto.group.GroupReadDto;
import xhosh.dev.Taskle.dto.response.TaskReadDto;
import xhosh.dev.Taskle.entity.Group;
import xhosh.dev.Taskle.entity.GroupTask;
import xhosh.dev.Taskle.entity.Task;
import xhosh.dev.Taskle.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class GroupReadMapper implements Mapper<Group, GroupReadDto> {

    private TaskReadMapper taskReadMapper;

    @Override
    public GroupReadDto map(Group obj) {
        return new GroupReadDto(
                obj.getId(),
                obj.getName(),
                mapTasks(obj.getTasks()));
    }

    private List<TaskReadDto> mapTasks(List<GroupTask> groupTasks) {
        List<TaskReadDto> tasks = new ArrayList<>();

        for(var groupTask: groupTasks) {
            Task task = groupTask.getTask();
            tasks.add(taskReadMapper.map(task));
        }

        return tasks;
    }
}
