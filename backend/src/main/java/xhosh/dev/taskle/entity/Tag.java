package xhosh.dev.taskle.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import xhosh.dev.taskle.entity.enumeration.TagName;
import xhosh.dev.taskle.entity.enumeration.TaskTag;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tags")
public class Tag implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TagName name;

    @OneToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<TaskTag> tasks = new ArrayList<>();
}
