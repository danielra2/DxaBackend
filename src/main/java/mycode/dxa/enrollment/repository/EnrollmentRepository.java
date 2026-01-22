package mycode.dxa.enrollment.repository;

import mycode.dxa.enrollment.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByStudentIdAndDanceClassId(Long studentId, Long danceClassId);
    boolean existsByStudentIdAndDanceClassId(Long studentId, Long danceClassId);
}