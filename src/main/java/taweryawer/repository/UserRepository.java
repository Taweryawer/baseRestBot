package taweryawer.repository;

import taweryawer.entities.User;

public interface UserRepository {
    User getUserByTelegramId(String telegramId);
}
