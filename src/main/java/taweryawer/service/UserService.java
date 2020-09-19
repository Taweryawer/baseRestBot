package taweryawer.service;

import taweryawer.entities.User;

public interface UserService {

    /**
    User's statemachine id is always the same as user's telegram id
     */
    User getUserByTelegramId(String telegramId) throws Exception;

    void changeUserName(String telegramId, String newName) throws Exception;

    void changeUserPhoneNumber(String telegramId, String newNumber) throws Exception;
}
