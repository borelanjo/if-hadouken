package br.com.borelanjo.infrastructure.repository;

import br.com.borelanjo.domain.model.User;

public interface UserRepository {
    User findByUserName(String username);
    void create(User user);
}
