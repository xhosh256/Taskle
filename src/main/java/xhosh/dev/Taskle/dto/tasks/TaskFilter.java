package xhosh.dev.Taskle.dto.tasks;

import lombok.Value;
import xhosh.dev.Taskle.entity.TagName;

import java.util.List;

@Value
public class TaskFilter {
    String title;
    List<TagName> tags;

    // TODO: add filter with status
}
