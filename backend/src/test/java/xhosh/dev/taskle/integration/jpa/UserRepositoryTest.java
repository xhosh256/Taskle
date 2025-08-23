package xhosh.dev.taskle.integration.jpa;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xhosh.dev.taskle.entity.Account;
import xhosh.dev.taskle.entity.Profile;
import xhosh.dev.taskle.entity.User;
import xhosh.dev.taskle.entity.enumeration.Role;
import xhosh.dev.taskle.integration.IntegrationTest;
import xhosh.dev.taskle.repository.UserRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    void saveAndReadTest() {
        var user = createUserEntity();

        userRepository.save(user);
        assertNotNull(user.getId());
        Long id = user.getId();
        entityManager.clear();

        var userFromDB = userRepository.findById(id);
        assertNotNull(userFromDB);
        userFromDB.ifPresent(u -> assertEquals("xhosh256",
                                                          u.getUsername()));
        userFromDB.ifPresent(u -> assertEquals("12345qwerty",
                                                          u.getPassword()));
    }

    @Test
    void saveAndDeleteTest() {
        var user = createUserEntity();
        userRepository.save(user);
        assertNotNull(user.getId());
        Long id = user.getId();
        entityManager.clear();

        var userFromDb = userRepository.findById(id);
        assertTrue(userFromDb.isPresent());
        userRepository.delete(userFromDb.get());
        entityManager.flush();
        entityManager.clear();

        userFromDb = userRepository.findById(id);
        assertFalse(userFromDb.isPresent());
    }

    @Test
    void saveAndUpdate() {
        var user = createUserEntity();
        userRepository.save(user);
        assertNotNull(user.getId());
        Long id = user.getId();
        entityManager.clear();

        var userFromDb = userRepository.findById(id);
        userFromDb.ifPresent(u -> u.setRole(Role.USER));
        entityManager.flush();
        entityManager.clear();

        userFromDb = userRepository.findById(id);
        userFromDb.ifPresent(u -> assertEquals(Role.USER, u.getRole()));
    }

    private User createUserEntity() {
        Profile profile = new Profile();
        profile.setFirstname("lll");
        profile.setLastname("kkk");
        profile.setBirthDate(LocalDate.now());

        var user = new User();
        user.setRole(Role.ADMIN);
        user.setProfile(profile);
        user.setUsername("xhosh256");
        user.setPassword("12345qwerty");

        return user;
    }
}
