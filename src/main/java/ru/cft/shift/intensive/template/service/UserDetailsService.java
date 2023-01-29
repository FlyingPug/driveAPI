package ru.cft.shift.intensive.template.service;


import ru.cft.shift.intensive.template.dto.AccountDetails;

import java.util.List;

/**
 * Сервис для работы с пользователями/аутентификацией пользователей
 */
public interface UserDetailsService {
    /**
     * Поиск пользователя по логину
     *
     * @param username логин
     * @return пользователь
     */
    AccountDetails findUserByUsername(String username);

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
