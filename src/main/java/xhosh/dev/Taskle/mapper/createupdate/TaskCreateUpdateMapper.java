package xhosh.dev.Taskle.mapper.createupdate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xhosh.dev.Taskle.dto.tasks.TaskCreateDto;
import xhosh.dev.Taskle.dto.tasks.TaskUpdateDto;
import xhosh.dev.Taskle.entity.Task;
import xhosh.dev.Taskle.jpa.repository.TagRepository;
import xhosh.dev.Taskle.mapper.Mapper;

@Component
@AllArgsConstructor
public class TaskCreateUpdateMapper implements Mapper<TaskCreateDto, Task> {

    private TagRepository tagRepository;

    @Override
    public Task map(TaskCreateDto obj) {
        Task task = new Task();
        task.setTitle(obj.getTitle());
        task.setDescription(obj.getDescription());
        if(obj.getTags() != null) {
            for (var tag: obj.getTags()) {
                var tagEntity = tagRepository.findByName(tag);
                task.addTag(tagEntity);
            }
        }


        return task;
    }

    public Task map(Task toUpdate, TaskUpdateDto task) {
        toUpdate.setTitle(task.getTitle());
        toUpdate.setDescription(task.getDescription());
        if(task.getTags() != null) {
            for (var tag : task.getTags()) {
                var tagEntity = tagRepository.findByName(tag);
                toUpdate.addTag(tagEntity);
            }
        }
        return toUpdate;
    }
}
