package net.posco.user;

import static org.assertj.core.api.Assertions.assertThat;
import net.posco.entities.User;
import net.posco.repositories.UserRepository;
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
        User user = new User(
                "lebao.trung@posco.net",
                "Trung",
                "Le Bao",
                "Trung1234",
                "Admin",
                true);
        User saveUser = userRepository.save(user);
        assertThat(saveUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testUpdateUser() {
        User user = userRepository.findById(1).get();
        user.setEmail("lebao.trung@posco.net");
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
