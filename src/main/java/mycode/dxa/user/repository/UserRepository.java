package mycode.dxa.user.repository;

import mycode.dxa.user.models.User;
import mycode.dxa.user.models.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // Păstrăm și metoda veche, e utilă
    List<User> findAllByUserType(UserType userType);

    // --- ACEASTA ESTE METODA CARE ÎȚI LIPSEȘTE PENTRU FRONTEND ---
    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN u.enrollments e " +
            "WHERE u.userType = 'STUDENT' " +
            "AND (:search IS NULL OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:status IS NULL OR " +
            "    (:status = 'Active' AND u.subscriptionExpirationDate >= CURRENT_DATE) OR " +
            "    (:status = 'Inactive' AND (u.subscriptionExpirationDate < CURRENT_DATE OR u.subscriptionExpirationDate IS NULL))" +
            ") " +
            "AND (:courseId IS NULL OR e.danceClass.id = :courseId)")
    List<User> findStudentsWithFilters(
            @Param("search") String search,
            @Param("status") String status,
            @Param("courseId") Long courseId
    );
}