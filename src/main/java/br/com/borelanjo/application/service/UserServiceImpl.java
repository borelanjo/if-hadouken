package br.com.borelanjo.application.service;

import br.com.borelanjo.domain.model.User;
import br.com.borelanjo.domain.service.UserService;
import br.com.borelanjo.infrastructure.repository.UserRepository;
import br.com.borelanjo.infrastructure.validator.EmailValidator;
import br.com.borelanjo.presentation.dto.UserTo;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public String register(UserTo userTo) {
        String message = null;
        if (userTo != null) {
            String msg = "";
            if (userTo.getUsername() != null && !userTo.getUsername().isBlank()) {
                if (userTo.getPassword() != null && !userTo.getPassword().isBlank()) {
                    if (userTo.getPassword().equals(userTo.getPasswordRepeat())) {
                        if (userTo.getPassword().length() > 5) {
                            if (userTo.getUsername().length() < 65 && userTo.getUsername().length() > 3) {
                                if (userTo.getUsername().matches("^[a-zA-Z0-9._-]{3,}$")) {
                                    final User user = userRepository.findByUserName(userTo.getUsername());
                                    if (user == null) {
                                        if (userTo.getEmail() != null && !userTo.getEmail().isEmpty()) {
                                            if (userTo.getEmail().length() < 65) {
                                                if (EmailValidator.isValid(userTo.getEmail())) {
                                                    userRepository.create(userTo.toUser());
                                                } else msg = "You must provide a valid email address";
                                            } else msg = "Email must be less than 64 characters";
                                        } else msg = "Email cannot be empty";
                                    } else msg = "Username already exists";
                                } else msg = "Username Valid characters: a-z, A-Z, 0-9, points, dashes and underscores";
                            } else msg = "Username must be between 2 and 64 characters";
                        } else msg = "Password must be at least 6 characters";
                    } else msg = "Passwords do not match";
                } else msg = "Empty Password";
            } else msg = "Empty Username";
            message = msg.isBlank() ? "Created" : msg;
        }
        return message;
    }
}
