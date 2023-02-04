package ru.cft.shift.intensive.cool_drive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.cft.shift.intensive.cool_drive.dto.Account;
import ru.cft.shift.intensive.cool_drive.dto.AccountDetails;
import ru.cft.shift.intensive.cool_drive.service.UserDetailsService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * API для работы с пользователями
 */
@RestController
@RequestMapping(value = "/driveAPI/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    private final UserDetailsService userService;

    @Autowired
    public AccountController(UserDetailsService userDetailsService) {
        this.userService = userDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<String>> list() {
        List<String> accounts = userService.findAll()
                .stream()
                .map(AccountDetails::getUsername)
                .collect(Collectors.toList());
        return ResponseEntity.ok(accounts);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody Account account) {
        AccountDetails accountDetails = userService.save(account.getUsername(), account.getPassword(), account.getConfirmPassword());

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(accountDetails.getUsername())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE) // не по api вышло, но иначе никак
    public ResponseEntity<AccountDetails> get(@RequestParam String username) {
        var accountDetails = userService.findUserByUsername(username).clearPassword();
        return ResponseEntity.ok(accountDetails);
    }
}
