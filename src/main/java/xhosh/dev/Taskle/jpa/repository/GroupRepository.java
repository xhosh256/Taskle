package xhosh.dev.Taskle.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xhosh.dev.Taskle.entity.Group;
import xhosh.dev.Taskle.jpa.implementation.GroupRepositoryCustom;


public interface GroupRepository extends JpaRepository<Group, Integer>,
        GroupRepositoryCustom {
}
