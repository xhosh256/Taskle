package xhosh.dev.Taskle.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xhosh.dev.Taskle.entity.GroupTask;

import java.util.Optional;

public interface GroupTaskRepository extends JpaRepository<GroupTask, Integer> {

    Optional<GroupTask> findByTask_IdAndGroup_Id(Integer taskId, Integer groupId);
}
