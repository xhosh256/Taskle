package xhosh.dev.Taskle.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xhosh.dev.Taskle.entity.Tag;
import xhosh.dev.Taskle.entity.TagName;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Tag findByName(TagName name);
}