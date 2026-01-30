package mycode.dxa.attendance.service;

import mycode.dxa.attendance.dtos.MarkAttendanceDto;

public interface AttendanceCommandService {
    void markAttendance(MarkAttendanceDto dto);
}