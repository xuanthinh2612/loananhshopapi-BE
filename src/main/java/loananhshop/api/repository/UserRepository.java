package loananhshop.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import loananhshop.api.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    User findByEmail(String email);

    User findByPhoneNumber(String phoneNumber);

}
