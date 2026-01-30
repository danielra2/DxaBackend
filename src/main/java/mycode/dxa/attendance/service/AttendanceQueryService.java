package mycode.dxa.attendance.service;

import mycode.dxa.classes.dtos.DanceClassResponse;
import java.time.LocalDate;

public interface AttendanceQueryService {
    boolean isStudentPresent(Long studentId, Long classId, LocalDate date);
    DanceClassResponse getClassDetailsWithAttendance(Long classId, LocalDate date);
}