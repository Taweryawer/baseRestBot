package taweryawer.service;

import taweryawer.entities.User;

public interface UserService {

    /**
    User's statemachine id is always the same as user's telegram id
     */
    User getUserByTelegramId(String telegramId);

    void changeUserName(String telegramId, String newName);
}
