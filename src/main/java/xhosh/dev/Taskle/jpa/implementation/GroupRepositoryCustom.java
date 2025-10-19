package xhosh.dev.Taskle.jpa.implementation;

import xhosh.dev.Taskle.entity.Group;

import java.util.List;

public interface GroupRepositoryCustom {
    List<Group> findAllByUsername(String username);
}
