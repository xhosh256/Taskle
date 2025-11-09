package xhosh.dev.Taskle.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xhosh.dev.Taskle.entity.TagName;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskReadDto {
    Integer id;
    String title;
    String description;
    List<TagName> tags;

    // TODO: add audit to this response dto
}
