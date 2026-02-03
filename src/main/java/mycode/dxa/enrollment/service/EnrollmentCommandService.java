package mycode.dxa.enrollment.service;

import java.time.LocalDate;

public interface EnrollmentCommandService {
    void enrollStudent(Long studentId, Long classId, LocalDate expirationDate);
    void unenrollStudent(Long studentId, Long classId);
    void toggleParticipation(Long studentId, Long classId, boolean participated);
}