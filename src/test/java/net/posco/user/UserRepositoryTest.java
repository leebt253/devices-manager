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
    private UserRepository userRepo;

    @Test
    public void testCreateUserWithOneRole() {
        User userAdmin = new User("trunglb", "Trung1234", "admin");
        User saveUser = userRepo.save(userAdmin);
        assertThat(saveUser.getId()).isGreaterThan(0);
    }

}
