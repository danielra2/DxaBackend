package mycode.dxa.enrollment.service;

public interface EnrollmentCommandService {
    void enrollStudent(Long studentId, Long classId);
    void unenrollStudent(Long studentId, Long classId);
    void toggleParticipation(Long studentId, Long classId, boolean participated);
}