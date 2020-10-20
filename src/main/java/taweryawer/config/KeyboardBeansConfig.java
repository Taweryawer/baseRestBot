package taweryawer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import taweryawer.service.ReplyKeyboardBuilder;

@Configuration
public class KeyboardBeansConfig {

    @Autowired
    private ReplyKeyboardBuilder builder;

    @Bean
    @Qualifier("main")
    public ReplyKeyboardMarkup mainKeyboard() {
        return builder.add("Профіль")
                .add("Меню")
                .add("Кошик")
                .build();
    }
}
