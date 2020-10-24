package taweryawer.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.statemachine.data.jpa.JpaRepositoryStateMachinePersist;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Configuration
@PropertySource(value = "classpath:${spring.profiles.active}.properties", encoding = "UTF-8")
public class BeansConfig {

    private JpaStateMachineRepository jpaStateMachineRepository;

    @Autowired
    public BeansConfig(JpaStateMachineRepository jpaStateMachineRepository) {
        this.jpaStateMachineRepository = jpaStateMachineRepository;
    }

    @Bean
    public JpaRepositoryStateMachinePersist<UserState, UserEvent> jpaRepositoryStateMachinePersist() {
        return new JpaRepositoryStateMachinePersist<>(jpaStateMachineRepository);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
