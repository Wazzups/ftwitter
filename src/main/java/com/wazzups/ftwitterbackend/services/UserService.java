package com.wazzups.ftwitterbackend.services;

import com.wazzups.ftwitterbackend.exceptions.EmailAlreadyTakenException;
import com.wazzups.ftwitterbackend.exceptions.UserDoesNotExistException;
import com.wazzups.ftwitterbackend.models.ApplicationUser;
import com.wazzups.ftwitterbackend.models.RegistrationObject;
import com.wazzups.ftwitterbackend.models.Role;
import com.wazzups.ftwitterbackend.repositories.RoleRepository;
import com.wazzups.ftwitterbackend.repositories.UserRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MailService mailService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mailService = mailService;
    }

    public ApplicationUser registerUser(RegistrationObject ro) {
        ApplicationUser user = new ApplicationUser();
        user.setFirstName(ro.getFirstName());
        user.setLastName(ro.getLastName());
        user.setEmail(ro.getEmail());
        user.setDateOfBirth(ro.getDateOfBirth());

        String name = ro.getFirstName() + ro.getLastName();
        String tempName = "";
        boolean taken = true;
        while (taken) {
            tempName = generateUsername(name);
            if (userRepository.findByUsername(tempName).isEmpty()) {
                taken = false;
            }
        }
        user.setUsername(tempName);

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findRoleByAuthority("USER").get());
        user.setAuthorities(roles);

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new EmailAlreadyTakenException();
        }
    }

    public ApplicationUser getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserDoesNotExistException::new);
    }

    public ApplicationUser updateUser(ApplicationUser user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new EmailAlreadyTakenException();
        }
    }

    public void generateEmailVerificationCode(String username) {
        ApplicationUser user = userRepository.findByUsername(username).orElseThrow(UserDoesNotExistException::new);
        user.setVerificationCode(generateVerificationCode());

        try {
            mailService.sendMail(user.getEmail(), "Your verification code", "Here is your verification code" + user.getVerificationCode());
            userRepository.save(user);
        } catch (Exception e) {
            throw new EmailAlreadyTakenException();
        }
    }

    private Long generateVerificationCode() {
        return (long) Math.floor(Math.random() * 100_000_000);
    }


    public String generateUsername(String name) {
        long generateNumber = (long) Math.floor(Math.random() * 1_000_000_000);
        return name + generateNumber;

    }
}
