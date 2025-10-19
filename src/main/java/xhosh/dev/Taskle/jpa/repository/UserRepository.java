package xhosh.dev.Taskle.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xhosh.dev.Taskle.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
