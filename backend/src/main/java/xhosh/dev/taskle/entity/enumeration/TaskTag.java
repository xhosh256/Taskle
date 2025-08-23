package xhosh.dev.taskle.entity.enumeration;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import xhosh.dev.taskle.entity.BaseEntity;
import xhosh.dev.taskle.entity.Tag;
import xhosh.dev.taskle.entity.Task;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class TaskTag implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private List<Tag> tags = new ArrayList<>();
}
