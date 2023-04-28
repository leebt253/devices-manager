package net.posco.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        LocalDateTime dateCreated = LocalDateTime.now();
        LocalDateTime dateModified = LocalDateTime.now();
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("Super");
        user.setEmail("admin@posco.net");
        user.setPassword("$2a$12$jnPdtxANa6n1mZElLr7rvuVUlVEMTups6CSv9jjYlZZzebdmksQa.");
        user.setRole("Admin");
        user.setEnabled(true);
        user.setDateCreated(dateCreated);
        user.setDateModified(dateModified);
        User saveUser = userRepository.save(user);
        assertThat(saveUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testUpdateUser() {
        User user = userRepository.findById(1).get();
        user.setEmail("super.admin@posco.net");
        userRepository.save(user);
    }

    @Test
    public void testDeleteUser() {
        User user = userRepository.findById(2).get();
        userRepository.delete(user);
    }

    @Test
    public void testListAllUser() {
        Iterable<User> listUsers = userRepository.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }

}
