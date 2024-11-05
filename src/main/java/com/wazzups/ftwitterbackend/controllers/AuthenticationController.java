package com.wazzups.ftwitterbackend.controllers;

import com.wazzups.ftwitterbackend.exceptions.EmailAlreadyTakenException;
import com.wazzups.ftwitterbackend.exceptions.EmailFailedToSendException;
import com.wazzups.ftwitterbackend.exceptions.IncorrectVerificationCodeException;
import com.wazzups.ftwitterbackend.exceptions.UserDoesNotExistException;
import com.wazzups.ftwitterbackend.models.ApplicationUser;
import com.wazzups.ftwitterbackend.models.RegistrationObject;
import com.wazzups.ftwitterbackend.services.UserService;
import java.util.LinkedHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<String> handleEmailAlreadyTakenException() {
        return new ResponseEntity<>("The email provided is already taken", HttpStatus.CONFLICT);
    }

    @PostMapping("/register")
    public ApplicationUser register(@RequestBody RegistrationObject user) {
        return userService.registerUser(user);
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<String> handleUserDoesNotExistException() {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/phone")
    public ApplicationUser updatePhoneNumber(@RequestBody LinkedHashMap<String, String> body) {
        String username = body.get("username");
        String phone = body.get("phone");
        ApplicationUser user = userService.getUserByUsername(username);
        user.setPhone(phone);

        return userService.updateUser(user);
    }

    @PostMapping("/email/code")
    public ResponseEntity<String> createEmailVerificationCode(@RequestBody LinkedHashMap<String, String> body) {
        userService.generateEmailVerificationCode(body.get("username"));

        return new ResponseEntity<>("Email verification code generated", HttpStatus.OK);
    }

    @ExceptionHandler(EmailFailedToSendException.class)
    public ResponseEntity<String> handleEmailFailedToSendException() {
        return new ResponseEntity<>("Email failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/email/verify")
    public ApplicationUser verifyEmail(@RequestBody LinkedHashMap<String, String> body) {
        Long code = Long.parseLong(body.get("code"));
        String username = body.get("username");

        return userService.verifyEmail(username, code);
    }

    @ExceptionHandler(IncorrectVerificationCodeException.class)
    public ResponseEntity<String> handleIncorrectVerificationCodeException() {
        return new ResponseEntity<>("Incorrect verification code", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/password")
    public ApplicationUser updatePassword(@RequestBody LinkedHashMap<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        return userService.setPassword(username, password);
    }
}
