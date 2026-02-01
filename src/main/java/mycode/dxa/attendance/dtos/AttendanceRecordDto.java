package mycode.dxa.attendance.dtos;

import java.time.LocalDate;

public record AttendanceRecordDto(
        String className,
        LocalDate date,
        boolean present
) {}