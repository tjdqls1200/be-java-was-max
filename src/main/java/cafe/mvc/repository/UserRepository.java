package cafe.mvc.repository;

import cafe.mvc.domain.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findByUsername(String username);
}
