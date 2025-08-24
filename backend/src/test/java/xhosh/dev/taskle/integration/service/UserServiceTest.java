package xhosh.dev.taskle.integration.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import xhosh.dev.taskle.dto.UserCreateEditDto;
import xhosh.dev.taskle.entity.enumeration.Role;
import xhosh.dev.taskle.integration.IntegrationTest;
import xhosh.dev.taskle.service.UserService;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void findAll() {
        var users = userService.findAll();

        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertThat(users).hasSize(10);
    }

    @Test
    void findById() {
        var user = userService.findById(1L);

        assertTrue(user.isPresent());
        user.ifPresent(u -> assertEquals(1L, u.getId()));
        user.ifPresent(u -> assertEquals("admin", u.getUsername()));
        user.ifPresent(u -> assertEquals("Super", u.getFirstname()));
        user.ifPresent(u -> assertEquals("Admin", u.getLastname()));
        user.ifPresent(u -> assertEquals(LocalDate.of(1990, 1, 1), u.getBirthDate()));
        user.ifPresent(u -> assertEquals(Role.ADMIN, u.getRole()));
    }

    @Test
    @Rollback
    void create() {
        var user = userService.create(
                new UserCreateEditDto(
                        "pidor",
                        "123qqwe",
                        "pidoras",
                        "gondon",
                        LocalDate.now(),
                        Role.ADMIN)
        );

        assertNotNull(user);
    }

    @Test
    @Rollback
    void update() {
        var user = userService.update(2L,
                new UserCreateEditDto(
                        "pidor",
                        "123qqwe",
                        "pidoras",
                        "gondon",
                        LocalDate.now(),
                        Role.ADMIN)
        );
        entityManager.flush();
        entityManager.clear();

        var updated = userService.findById(2L);
        assertTrue(updated.isPresent());
        assertEquals("pidor", updated.get().getUsername());
        assertEquals("pidoras", updated.get().getFirstname());
        assertEquals("gondon", updated.get().getLastname());
        assertEquals(Role.ADMIN, updated.get().getRole());
    }

    @Test
    @Rollback
    void delete() {
        assertTrue(userService.delete(2L));
    }
}