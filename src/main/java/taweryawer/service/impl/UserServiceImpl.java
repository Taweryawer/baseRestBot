package taweryawer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import taweryawer.entities.User;
import taweryawer.repository.UserRepository;
import taweryawer.service.UserService;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByTelegramId(String telegramId) {
        return userRepository.getUserByTelegramId(telegramId);
    }
}
