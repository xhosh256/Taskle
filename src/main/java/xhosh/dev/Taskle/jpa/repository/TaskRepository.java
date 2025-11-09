package xhosh.dev.Taskle.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import xhosh.dev.Taskle.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>,
        QuerydslPredicateExecutor<Task> {
}
