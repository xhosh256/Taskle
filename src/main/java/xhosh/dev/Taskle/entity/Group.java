package xhosh.dev.Taskle.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "groups")
@Data
public class Group extends Auditable
        implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupTask> tasks = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
        user.getGroups().add(this);
    }

    public void addTask(Task task) {
        var taskGroup = new GroupTask();
        taskGroup.setTask(task);
        taskGroup.setGroup(this);
        task.getGroups().add(taskGroup);
        this.tasks.add(taskGroup);
    }

    public void removeTask(GroupTask groupTask) {
        tasks.remove(groupTask);
    }
}
