package xhosh.dev.Taskle.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tasks")
@Data
public class Task extends Auditable implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<TaskTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<GroupTask> groups = new ArrayList<>();

    // solved, in-process, etc.
    // private Status status;


    // to create separate container w/ favourite tasks
    // private Boolean favourite;

    public void addTag(Tag tag) {
        TaskTag taskTag = new TaskTag();
        taskTag.setTask(this);
        taskTag.setTag(tag);
        tag.getTasks().add(taskTag);
        this.tags.add(taskTag);
    }

    public void removeGroup(GroupTask groupTask) {
        groups.remove(groupTask);
    }

    // TODO: add status(solved, just-created etc)
}
