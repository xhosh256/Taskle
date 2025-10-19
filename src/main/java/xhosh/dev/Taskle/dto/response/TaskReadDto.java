package xhosh.dev.Taskle.dto.response;

import lombok.Value;
import xhosh.dev.Taskle.entity.TagName;

import java.util.List;

@Value
public class TaskReadDto {
    Integer id;
    String title;
    String description;
    List<TagName> tags;

    // TODO: add audit to this response dto
}
