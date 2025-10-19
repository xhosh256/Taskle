package xhosh.dev.Taskle.dto.group;

import lombok.Value;
import xhosh.dev.Taskle.dto.response.TaskReadDto;

import java.util.List;

@Value
public class GroupReadDto {
    Integer id;
    String name;
    List<TaskReadDto> tasks;
}
