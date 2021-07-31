package br.com.borelanjo.infrastructure.repository;

import br.com.borelanjo.domain.model.User;

import java.util.List;

public class UserArrayRepository implements UserRepository {
    private final List<User> users;

    public UserArrayRepository(List<User> users) {
        this.users = users;
    }

    @Override
    public User findByUserName(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username))
                .findAny()
                .orElse(null);
    }

    @Override
    public void create(User user) {
        users.add(user);
    }
}
