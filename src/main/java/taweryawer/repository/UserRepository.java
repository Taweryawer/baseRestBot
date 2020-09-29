package taweryawer.repository;

import taweryawer.entities.User;

public interface UserRepository {
    User getUserByTelegramId(String telegramId) throws Exception;
    void changeUserName(String telegramId, String newName) throws Exception;
    void changeUserPhoneNumber(String telegramId, String newNumber) throws Exception;
    void changeUserAddress(String telegramId, String address) throws Exception;
}
