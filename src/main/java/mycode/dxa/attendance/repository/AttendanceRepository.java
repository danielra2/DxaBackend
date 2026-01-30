package mycode.dxa.attendance.repository;

import mycode.dxa.attendance.models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByStudentIdAndDanceClassIdAndDate(Long studentId, Long classId, LocalDate date);
}