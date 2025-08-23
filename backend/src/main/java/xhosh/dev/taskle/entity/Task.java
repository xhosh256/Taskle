package xhosh.dev.taskle.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import xhosh.dev.taskle.entity.enumeration.Status;
import xhosh.dev.taskle.entity.enumeration.TaskDifficulty;
import xhosh.dev.taskle.entity.enumeration.TaskTag;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "tasks")
@NoArgsConstructor
public class Task extends Auditable implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private TaskDifficulty difficulty;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "tasks", fetch = FetchType.LAZY)
    private List<TaskTag> tags = new ArrayList<>();
}
