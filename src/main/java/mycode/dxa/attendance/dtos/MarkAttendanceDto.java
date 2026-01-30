package mycode.dxa.attendance.dtos;

import java.time.LocalDate;

public record MarkAttendanceDto(
        Long studentId,
        Long classId,
        LocalDate date,
        boolean present
) {}