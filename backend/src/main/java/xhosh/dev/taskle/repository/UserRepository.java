package xhosh.dev.taskle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xhosh.dev.taskle.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
