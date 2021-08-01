package br.com.borelanjo.application.service;

import br.com.borelanjo.application.service.exception.UserAlreadyExistsException;
import br.com.borelanjo.domain.model.User;
import br.com.borelanjo.domain.service.UserService;
import br.com.borelanjo.infrastructure.repository.UserRepository;
import br.com.borelanjo.infrastructure.validator.Validator;
import br.com.borelanjo.infrastructure.validator.exception.FieldInvalidException;
import br.com.borelanjo.infrastructure.validator.rule.*;
import br.com.borelanjo.presentation.dto.UserTo;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void register(UserTo userTo) {

        validate(userTo);

        userRepository.create(userTo.toUser());
    }

    public void validate(UserTo userTo) {
        final Validator validator = new Validator();

        if (userTo == null) {
            throw new FieldInvalidException("Usuário não pode ser nulo");
        }

        validator
                .addRule(new EmptyFieldRule("username", userTo.getUsername()))
                .addRule(new EmptyFieldRule("senha", userTo.getPassword()))
                .addRule(new EmptyFieldRule("e-mail", userTo.getEmail()))
                .addRule(new SizeFieldRule("username", userTo.getUsername(), 2, 64))
                .addRule(new SizeFieldRule("senha", userTo.getPassword(), 6, 30))
                .addRule(new SizeFieldRule("e-mail", userTo.getEmail(), 0, 64))
                .addRule(new UsernameRule(userTo.getUsername()))
                .addRule(new EmailRule(userTo.getEmail()))
                .addRule(new PasswordRule(userTo))
                .process();

        final User user = userRepository.findByUserName(userTo.getUsername());
        if (user != null) {
            throw new UserAlreadyExistsException();
        }
    }
}
