package taweryawer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taweryawer.entities.User;
import taweryawer.repository.UserRepository;
import taweryawer.service.UserService;

@Service
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

    @Override
    public void changeUserName(String telegramId, String newName) {
        userRepository.changeUserName(telegramId, newName);
    }
}
