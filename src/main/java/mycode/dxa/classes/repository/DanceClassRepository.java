package mycode.dxa.classes.repository;

import mycode.dxa.classes.models.DanceClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanceClassRepository extends JpaRepository<DanceClass, Long> {
    boolean existsByTitle(String title);
}