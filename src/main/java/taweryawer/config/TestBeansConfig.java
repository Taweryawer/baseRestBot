package taweryawer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import taweryawer.repository.FoodRepository;
import taweryawer.repository.OrderPieceRepository;
import taweryawer.repository.OrderRepository;
import taweryawer.repository.UserRepository;
import taweryawer.repository.impl.FoodRepositoryImpl;
import taweryawer.repository.impl.OrderPieceRepositoryImpl;
import taweryawer.repository.impl.OrderRepositoryImpl;
import taweryawer.repository.impl.UserRepositoryImpl;
import taweryawer.service.OrderService;
import taweryawer.service.impl.OrderServiceImpl;

import javax.sql.DataSource;

@Configuration
public class TestBeansConfig {


    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl();
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepositoryImpl();
    }

    @Bean
    public OrderPieceRepository orderPieceRepository() {
        return new OrderPieceRepositoryImpl();
    }

    @Bean
    public FoodRepository foodRepository() {
        return new FoodRepositoryImpl();
    }
}
