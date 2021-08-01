package br.com.borelanjo.domain.service;

import br.com.borelanjo.application.service.UserServiceImpl;
import br.com.borelanjo.application.service.exception.UserAlreadyExistsException;
import br.com.borelanjo.domain.model.User;
import br.com.borelanjo.infrastructure.repository.UserArrayRepository;
import br.com.borelanjo.infrastructure.repository.UserRepository;
import br.com.borelanjo.infrastructure.validator.exception.FieldInvalidException;
import br.com.borelanjo.presentation.dto.UserTo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceTest {
    private final UserService userService;
    private final UserRepository userRepository;

    UserServiceTest() {
        this.userRepository = new UserArrayRepository(new ArrayList<>(
                List.of(User.builder()
                        .username("admin")
                        .email("admin@gmail.com")
                        .password("admin123")
                        .build())));
        this.userService = new UserServiceImpl(userRepository);
    }

    @Test
    void shouldRegisterValidUser() {
        UserTo userTo = UserTo
                .builder()
                .username("validUser")
                .email("validUser@gmail.com")
                .password("blahblah12345")
                .passwordRepeat("blahblah12345")
                .build();
        userService.register(userTo);

        User validUser = userRepository.findByUserName("validUser");

        assertEquals(userTo.getUsername(), validUser.getUsername());
        assertEquals(userTo.getPassword(), validUser.getPassword());
        assertEquals(userTo.getEmail(), validUser.getEmail());
    }

    @Test
    void shouldNotRegisterUserWithUsernameEmpty() {
        UserTo userTo = UserTo
                .builder()
                .username("")
                .email("blahblah@gmail.com")
                .password("blahblah12345")
                .build();
        FieldInvalidException thrown = Assertions.assertThrows(FieldInvalidException.class, () ->
                userService.register(userTo));

        assertEquals("'username' não pode ser vazio.", thrown.getMessage());

    }

    @Test
    void shouldNotRegisterUserWithUsernameNull() {
        UserTo userTo = UserTo
                .builder()
                .email("blahblah@gmail.com")
                .password("blahblah12345")
                .build();

        FieldInvalidException thrown = Assertions.assertThrows(FieldInvalidException.class, () ->
                userService.register(userTo));

        assertEquals("'username' não pode ser vazio.", thrown.getMessage());
    }

    @Test
    void shouldNotRegisterUserWithPasswordEmpty() {
        UserTo userTo = UserTo
                .builder()
                .username("blahblah")
                .email("blahblah@gmail.com")
                .password("")
                .build();

        FieldInvalidException thrown = Assertions.assertThrows(FieldInvalidException.class, () ->
                userService.register(userTo));

        assertEquals("'senha' não pode ser vazio.", thrown.getMessage());
    }

    @Test
    void shouldNotRegisterUserWithPasswordNull() {
        UserTo userTo = UserTo
                .builder()
                .username("blahblah")
                .email("blahblah@gmail.com")
                .build();

        FieldInvalidException thrown = Assertions.assertThrows(FieldInvalidException.class, () ->
                userService.register(userTo));

        assertEquals("'senha' não pode ser vazio.", thrown.getMessage());
    }

    @Test
    void shouldNotRegisterUserWhenPasswordNotMatch() {
        UserTo userTo = UserTo
                .builder()
                .username("blahblah")
                .email("blahblah@gmail.com")
                .password("blahblah12345")
                .passwordRepeat("blahblah1234")
                .build();

        FieldInvalidException thrown = Assertions.assertThrows(FieldInvalidException.class, () -> userService.register(userTo));

        assertEquals("As senhas precisam ser iguais.", thrown.getMessage());
    }

    @Test
    void shouldNotRegisterUserWhenUsernameIsShort() {
        UserTo userTo = UserTo
                .builder()
                .username("b")
                .email("blahblah@gmail.com")
                .password("blahblah12345")
                .passwordRepeat("blahblah12345")
                .build();

        FieldInvalidException thrown = Assertions.assertThrows(FieldInvalidException.class, () ->
                userService.register(userTo));

        assertEquals("'username' deve ser maior que 2 e menor que 64, mas tem 1.", thrown.getMessage());
    }

    @Test
    void shouldNotRegisterUserWhenUsernameIsLarge() {
        UserTo userTo = UserTo
                .builder()
                .username("bl12345677834555555555555555555555555555555555555555555555555555555555555555555555555555555")
                .email("blahblah@gmail.com")
                .password("blahblah12345")
                .passwordRepeat("blahblah12345")
                .build();

        FieldInvalidException thrown = Assertions.assertThrows(FieldInvalidException.class, () ->
                userService.register(userTo));

        assertEquals("'username' deve ser maior que 2 e menor que 64, mas tem 91.", thrown.getMessage());
    }

    @Test
    void shouldNotRegisterUserWhenPasswordIsLarge() {
        UserTo userTo = UserTo
                .builder()
                .username("blahblah")
                .email("blahblah@gmail.com")
                .password("blahblah12345111111111111111111111111111111111111111111111111111111111111111111111111111")
                .passwordRepeat("blahblah12345")
                .build();

        FieldInvalidException thrown = Assertions.assertThrows(FieldInvalidException.class, () ->
                userService.register(userTo));

        assertEquals("'senha' deve ser maior que 6 e menor que 30, mas tem 88.", thrown.getMessage());
    }

    @Test
    void shouldNotRegisterUserWhenPasswordIsShort() {
        UserTo userTo = UserTo
                .builder()
                .username("blahblah")
                .email("blahblah@gmail.com")
                .password("blahb")
                .passwordRepeat("blahblah12345")
                .build();

        FieldInvalidException thrown = Assertions.assertThrows(FieldInvalidException.class, () ->
                userService.register(userTo));

        assertEquals("'senha' deve ser maior que 6 e menor que 30, mas tem 5.", thrown.getMessage());
    }

    @Test
    void shouldNotRegisterUserWhenUsernameIsInvalid() {
        UserTo userTo = UserTo
                .builder()
                .username("@@@@")
                .email("blahblah@gmail.com")
                .password("blahblah12345")
                .passwordRepeat("blahblah12345")
                .build();

        FieldInvalidException thrown = Assertions.assertThrows(FieldInvalidException.class, () ->
                userService.register(userTo));

        assertEquals("'username' precisa seguir essa regra: 'a-z', 'A-Z', '0-9', '.', '-' e '_'.",
                thrown.getMessage());
    }

    @Test
    void shouldNotRegisterUserWhenUsernameAlreadyExists() {
        UserTo userTo = UserTo
                .builder()
                .username("admin")
                .email("blahblah@gmail.com")
                .password("blahblah12345")
                .passwordRepeat("blahblah12345")
                .build();

        Assertions.assertThrows(UserAlreadyExistsException.class, () ->
                userService.register(userTo)
        );
    }

    @Test
    void shouldNotRegisterUserWhenEmailIsEmpty() {
        UserTo userTo = UserTo
                .builder()
                .username("blahblah")
                .email("")
                .password("blahblah12345")
                .passwordRepeat("blahblah12345")
                .build();

        FieldInvalidException thrown = Assertions.assertThrows(FieldInvalidException.class, () ->
                userService.register(userTo));

        assertEquals("'e-mail' não pode ser vazio.", thrown.getMessage());
    }

    @Test
    void shouldNotRegisterUserWhenEmailIsLarge() {
        UserTo userTo = UserTo
                .builder()
                .username("blahblah")
                .email("bl1234567783455555555555555555555555555555555555555555555555555555555555555555555@gmail.com")
                .password("blahblah12345")
                .passwordRepeat("blahblah12345")
                .build();

        FieldInvalidException thrown = Assertions.assertThrows(FieldInvalidException.class, () ->
                userService.register(userTo));

        assertEquals("'e-mail' deve ser maior que 0 e menor que 64, mas tem 91.", thrown.getMessage());
    }

    @Test
    void shouldNotRegisterUserWhenEmailIsInvalid() {
        UserTo userTo = UserTo
                .builder()
                .username("blahblah")
                .email("blah")
                .password("blahblah12345")
                .passwordRepeat("blahblah12345")
                .build();

        FieldInvalidException thrown = Assertions.assertThrows(FieldInvalidException.class, () ->
                userService.register(userTo));

        assertEquals("Você precisa fornecer um e-mail valido.", thrown.getMessage());
    }
}