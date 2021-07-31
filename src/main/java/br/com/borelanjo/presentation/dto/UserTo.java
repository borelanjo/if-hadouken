package br.com.borelanjo.presentation.dto;

import br.com.borelanjo.domain.model.User;

public class UserTo {

    private String username;
    private String password;
    private String passwordRepeat;
    private String email;

    public UserTo(String username, String password, String passwordRepeat, String email) {
        this.username = username;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public String getEmail() {
        return email;
    }

    public User toUser() {
        return User
                .builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }

    public static UserToBuilder builder() {
        return new UserToBuilder();
    }

    public static class UserToBuilder {
        private String username;
        private String password;
        private String passwordRepeat;
        private String email;


        public UserToBuilder() {
        }

        public UserToBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserToBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserToBuilder passwordRepeat(String passwordRepeat) {
            this.passwordRepeat = passwordRepeat;
            return this;
        }

        public UserToBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserTo build() {
            return new UserTo(this.username, this.password, this.passwordRepeat, this.email);
        }

    }
}
