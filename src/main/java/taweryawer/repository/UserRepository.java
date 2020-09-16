package taweryawer.repository;

import taweryawer.entities.User;

public interface UserRepository {
    User getUserByTelegramId(String telegramId);
    void changeUserName(String telegramId, String newName);
    void changeUserPhoneNumber(String telegramId, String newNumber);
}
