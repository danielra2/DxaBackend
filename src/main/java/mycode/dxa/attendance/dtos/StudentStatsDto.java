package mycode.dxa.attendance.dtos;

import java.util.List;

public record StudentStatsDto(
        double attendanceRate,
        int totalClasses,            // Total cursuri marcate
        int attendedClasses,         // Cursuri la care a fost prezent
        List<AttendanceRecordDto> history // Istoricul detaliat
) {}