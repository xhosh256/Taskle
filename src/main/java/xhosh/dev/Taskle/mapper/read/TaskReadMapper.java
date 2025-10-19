package xhosh.dev.Taskle.mapper.read;

import org.springframework.stereotype.Component;
import xhosh.dev.Taskle.dto.response.TaskReadDto;
import xhosh.dev.Taskle.entity.TagName;
import xhosh.dev.Taskle.entity.Task;
import xhosh.dev.Taskle.entity.TaskTag;
import xhosh.dev.Taskle.mapper.Mapper;

import java.util.List;

@Component
public class TaskReadMapper implements Mapper<Task, TaskReadDto> {


    @Override
    public TaskReadDto map(Task obj) {
        return new TaskReadDto(
                obj.getId(),
                obj.getTitle(),
                obj.getDescription(),
                getTags(obj.getTags())
        );
    }

    public List<TagName> getTags(List<TaskTag> tasktags) {
        return tasktags.stream()
                .map(taskTag -> taskTag.getTag().getName())
                .toList();
    }
}
