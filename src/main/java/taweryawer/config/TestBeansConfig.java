package taweryawer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import taweryawer.repository.UserRepository;
import taweryawer.repository.impl.UserRepositoryImpl;

@Configuration
@EnableTransactionManagement
public class TestBeansConfig {

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }


}
