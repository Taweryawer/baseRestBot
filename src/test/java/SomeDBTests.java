import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import taweryawer.Main;
import taweryawer.config.TestBeansConfig;
import taweryawer.entities.User;
import taweryawer.repository.UserRepository;

import static org.assertj.core.api.Assertions.*;
import javax.transaction.Transactional;

@ContextConfiguration(classes = {Main.class, TestBeansConfig.class})
@DataJpaTest
@ExtendWith({SpringExtension.class})
public class SomeDBTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("getUser")
    public void getUser() throws Exception {
        User user = userRepository.getUserByTelegramId("test");
        assertThat(user.getMachineId()).isEqualTo("test");
    }
}
