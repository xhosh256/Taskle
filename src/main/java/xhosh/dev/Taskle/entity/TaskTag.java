package xhosh.dev.Taskle.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TaskTag implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
