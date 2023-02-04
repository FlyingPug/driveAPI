package ru.cft.shift.intensive.cool_drive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.cft.shift.intensive.cool_drive.dto.AccountDetails;
import ru.cft.shift.intensive.cool_drive.dto.AuthRequest;
import ru.cft.shift.intensive.cool_drive.dto.Token;
import ru.cft.shift.intensive.cool_drive.security.JwtUtils;
import ru.cft.shift.intensive.cool_drive.service.UserDetailsService;

import javax.validation.Valid;

/**
 * api для ауентификации
 */
@RestController
@RequestMapping(value = "driveAPI/tokens", produces = MediaType.APPLICATION_JSON_VALUE)
public class tokenController {

    private final UserDetailsService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    public tokenController(UserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.userService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Token> createToken(@Valid @RequestBody AuthRequest account) {
        AccountDetails tokenUser = userService.authUser(account);
        return ResponseEntity.ok(new Token(jwtUtils.generateToken(tokenUser)));
    }
}
