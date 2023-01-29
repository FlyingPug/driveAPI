package ru.cft.shift.intensive.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.cft.shift.intensive.template.dto.Account;
import ru.cft.shift.intensive.template.dto.AccountDetails;
import ru.cft.shift.intensive.template.service.UserDetailsService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * API для работы с пользователями
 */
@RestController
@RequestMapping(value = "accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    private final UserDetailsService userDetailsService;

    @Autowired
    public AccountController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<String>> list() {
        List<String> accounts = userDetailsService.findAll()
                .stream()
                .map(AccountDetails::getUsername)
                .collect(Collectors.toList());
        return ResponseEntity.ok(accounts);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody Account account) {
        AccountDetails accountDetails = userDetailsService.save(account.getUsername(), account.getPassword(), account.getConfirmPassword());

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(accountDetails.getUsername())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{username}")
    public ResponseEntity<AccountDetails> get(@PathVariable String username) {
        var accountDetails = userDetailsService.findUserByUsername(username).clearPassword();
        return ResponseEntity.ok(accountDetails);
    }
}
