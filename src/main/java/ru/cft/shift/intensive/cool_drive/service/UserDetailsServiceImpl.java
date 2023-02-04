package ru.cft.shift.intensive.cool_drive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.shift.intensive.cool_drive.dto.AccountDetails;
import ru.cft.shift.intensive.cool_drive.dto.AuthRequest;
import ru.cft.shift.intensive.cool_drive.exception.PasswordNotConfirmedException;
import ru.cft.shift.intensive.cool_drive.exception.UserAlreadyExistsException;
import ru.cft.shift.intensive.cool_drive.exception.UserNotFoundException;
import ru.cft.shift.intensive.cool_drive.repository.UserRepository;
import ru.cft.shift.intensive.cool_drive.repository.entity.Account;
import ru.cft.shift.intensive.cool_drive.repository.entity.Role;
import ru.cft.shift.intensive.cool_drive.security.JwtUtils;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(JwtUtils jwtUtils, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @Override
    public AccountDetails getByToken(String token) {
        return findUserByUsername(jwtUtils.extractUsername(token));
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
    public Account authUser(AuthRequest acc) {
        Account account = userRepository.findByUsername(acc.getUsername());
        if (account == null) {
            log.warn("User {} not found", acc.getUsername());
            throw new UserNotFoundException("User " + acc.getUsername() + " not found");
        }
        if (Objects.equals(acc.getPassword(), account.getPassword())) return account;

        throw new PasswordNotConfirmedException("User " + acc.getUsername() + "enter the wrong password");
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
