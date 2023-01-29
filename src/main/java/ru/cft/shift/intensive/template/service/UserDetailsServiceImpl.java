package ru.cft.shift.intensive.template.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.shift.intensive.template.dto.AccountDetails;
import ru.cft.shift.intensive.template.exception.PasswordNotConfirmedException;
import ru.cft.shift.intensive.template.exception.UserAlreadyExistsException;
import ru.cft.shift.intensive.template.exception.UserNotFoundException;
import ru.cft.shift.intensive.template.repository.UserRepository;
import ru.cft.shift.intensive.template.repository.entity.Account;
import ru.cft.shift.intensive.template.repository.entity.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDetails findUserByUsername(String username) {
        Account account = userRepository.findByUsername(username);
        if (account == null) {
            log.warn("User {} not found", username);
            throw new UserNotFoundException("User " + username + " not found");
        }
        return account;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDetails> findAll() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    @Transactional
    public AccountDetails save(String username, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new PasswordNotConfirmedException("Password not confirmed for user " + username);
        }

        Account account = userRepository.findByUsername(username);
        if (account != null) {
            log.warn("User {} already exists", username);
            throw new UserAlreadyExistsException(String.format("User %s already exists", username));
        }

        account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRoles(Collections.singleton(Role.USER_ROLE));
        return userRepository.save(account);
    }
}
