package mycode.dxa.user.repository;

import mycode.dxa.user.models.User;
import mycode.dxa.user.models.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByUserType(UserType userType);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}