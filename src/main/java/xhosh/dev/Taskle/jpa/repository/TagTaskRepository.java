package xhosh.dev.Taskle.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xhosh.dev.Taskle.entity.TaskTag;

public interface TagTaskRepository extends JpaRepository<TaskTag, Integer> {

}
