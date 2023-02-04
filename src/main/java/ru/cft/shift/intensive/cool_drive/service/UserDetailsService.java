package ru.cft.shift.intensive.cool_drive.service;


import ru.cft.shift.intensive.cool_drive.dto.AccountDetails;
import ru.cft.shift.intensive.cool_drive.dto.AuthRequest;

import java.util.List;

/**
 * Сервис для работы с пользователями/аутентификацией пользователей
 */
public interface UserDetailsService {

    AccountDetails getByToken(String token);

    /**
     * Поиск пользователя по логину
     *
     * @param username логин
     * @return пользователь
     */

    AccountDetails findUserByUsername(String username);

    /**
     * Проверка на совпадание логина и пароля
     */
    AccountDetails authUser(AuthRequest acc);

    /**
     * Список всех пользователей
     *
     * @return список пользователей
     */
    List<AccountDetails> findAll();

    /**
     * Добавление пользователя
     *
     * @param username        логин
     * @param password        пароль
     * @param confirmPassword подтверждение пароля
     * @return добавленный пользователь
     */
    AccountDetails save(String username, String password, String confirmPassword);
}
