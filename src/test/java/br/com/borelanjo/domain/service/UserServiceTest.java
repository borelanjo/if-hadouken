package br.com.borelanjo.domain.service;

import br.com.borelanjo.application.service.UserServiceImpl;
import br.com.borelanjo.domain.model.User;
import br.com.borelanjo.infrastructure.repository.UserArrayRepository;
import br.com.borelanjo.infrastructure.repository.UserRepository;
import br.com.borelanjo.presentation.dto.UserTo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        var result = userService.register(userTo);

        User validUser = userRepository.findByUserName("validUser");
        assertNotNull(result);
        assertEquals("Created", result);
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
        var result = userService.register(userTo);
        assertEquals("Empty Username", result);
    }

    @Test
    void shouldNotRegisterUserWithUsernameNull() {
        UserTo userTo = UserTo
                .builder()
                .email("blahblah@gmail.com")
                .password("blahblah12345")
                .build();
        var result = userService.register(userTo);
        assertEquals("Empty Username", result);
    }

    @Test
    void shouldNotRegisterUserWithPasswordEmpty() {
        UserTo userTo = UserTo
                .builder()
                .username("blahblah")
                .email("blahblah@gmail.com")
                .password("")
                .build();
        var result = userService.register(userTo);
        assertEquals("Empty Password", result);
    }

    @Test
    void shouldNotRegisterUserWithPasswordNull() {
        UserTo userTo = UserTo
                .builder()
                .username("blahblah")
                .email("blahblah@gmail.com")
                .build();
        var result = userService.register(userTo);
        assertEquals("Empty Password", result);
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
        var result = userService.register(userTo);
        assertEquals("Passwords do not match", result);
    }

    @Test
    void shouldNotRegisterUserWhenUsernameIsShort() {
        UserTo userTo = UserTo
                .builder()
                .username("bl")
                .email("blahblah@gmail.com")
                .password("blahblah12345")
                .passwordRepeat("blahblah12345")
                .build();
        var result = userService.register(userTo);
        assertEquals("Username must be between 2 and 64 characters", result);
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
        var result = userService.register(userTo);
        assertEquals("Username must be between 2 and 64 characters", result);
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
        var result = userService.register(userTo);
        assertEquals("Username Valid characters: a-z, A-Z, 0-9, points, dashes and underscores", result);
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
        var result = userService.register(userTo);
        assertEquals("Username already exists", result);
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
        var result = userService.register(userTo);
        assertEquals("Email cannot be empty", result);
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
        var result = userService.register(userTo);
        assertEquals("Email must be less than 64 characters", result);
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
        var result = userService.register(userTo);
        assertEquals("You must provide a valid email address", result);
    }
}