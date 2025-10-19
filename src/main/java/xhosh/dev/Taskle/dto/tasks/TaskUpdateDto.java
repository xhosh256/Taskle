package xhosh.dev.Taskle.dto.tasks;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import xhosh.dev.Taskle.entity.TagName;

import java.util.List;

@Value
public class TaskUpdateDto {

    @JsonProperty(required = true)
    @NotNull
    @NotBlank(message = "task title cannot be blank")
    @Size(max = 128, message = "task title must be at most 128 symbols")
    String title;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank(message = "task description cannot be blank")
    @Size(max = 1024, message = "task title must be at most 1024 symbols")
    String description;

    List<TagName> tags;
}
