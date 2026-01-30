package mycode.dxa.attendance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mycode.dxa.attendance.dtos.MarkAttendanceDto;
import mycode.dxa.classes.dtos.DanceClassResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/api")
@Tag(name = "Attendance", description = "Managementul prezentelor")
public interface AttendanceControllerApi {

    // Mutat de la ClassController: Obține detaliile pentru prezență (Lista studenți + status)
    @GetMapping("/attendance/class/{classId}")
    @Operation(summary = "Fisa de prezenta", description = "Returneaza studentii si statusul prezentei pentru un curs la o data anume")
    ResponseEntity<DanceClassResponse> getAttendanceSheet(
            @PathVariable Long classId,
            @RequestParam LocalDate date
    );

    // Mutat de la ClassController: Marchează prezența
    @PostMapping("/attendance")
    @Operation(summary = "Marcare prezenta", description = "Bifeaza sau debifeaza prezenta unui student")
    ResponseEntity<Void> markAttendance(@RequestBody MarkAttendanceDto dto);
}