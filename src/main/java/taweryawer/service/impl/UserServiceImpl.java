package taweryawer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taweryawer.entities.User;
import taweryawer.repository.UserRepository;
import taweryawer.service.UserService;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByTelegramId(String telegramId) throws Exception {
        return userRepository.getUserByTelegramId(telegramId);
    }

    @Override
    public void changeUserName(String telegramId, String newName) throws Exception {
        userRepository.changeUserName(telegramId, newName);
    }

    @Override
    public void changeUserPhoneNumber(String telegramId, String newNumber) throws Exception {
        userRepository.changeUserPhoneNumber(telegramId, newNumber);
    }

    @Override
    public void changeUserAddress(String telegramId, String newAddress) throws Exception {
        userRepository.changeUserAddress(telegramId, newAddress);
    }
}
