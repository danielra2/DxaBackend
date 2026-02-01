package mycode.dxa.attendance.controller;

import mycode.dxa.attendance.dtos.MarkAttendanceDto;
import mycode.dxa.attendance.dtos.StudentStatsDto;
import mycode.dxa.attendance.service.AttendanceCommandService;
import mycode.dxa.attendance.service.AttendanceQueryService;
import mycode.dxa.classes.dtos.DanceClassResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class AttendanceController implements AttendanceControllerApi {

    private final AttendanceCommandService attendanceCommandService;
    private final AttendanceQueryService attendanceQueryService;

    public AttendanceController(AttendanceCommandService attendanceCommandService, AttendanceQueryService attendanceQueryService) {
        this.attendanceCommandService = attendanceCommandService;
        this.attendanceQueryService = attendanceQueryService;
    }

    @Override
    public ResponseEntity<DanceClassResponse> getAttendanceSheet(Long classId, LocalDate date) {
        // Delegăm către Query Service-ul din pachetul attendance
        return ResponseEntity.ok(attendanceQueryService.getClassDetailsWithAttendance(classId, date));
    }

    @Override
    public ResponseEntity<Void> markAttendance(MarkAttendanceDto dto) {
        // Delegăm către Command Service-ul din pachetul attendance
        attendanceCommandService.markAttendance(dto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<StudentStatsDto> getStudentStats(Long studentId, String range) {
        return ResponseEntity.ok(attendanceQueryService.getStudentStats(studentId, range));
    }
}