package mycode.dxa.attendance.service;

import mycode.dxa.attendance.models.Attendance;
import mycode.dxa.attendance.repository.AttendanceRepository;
import mycode.dxa.classes.dtos.ClassStudentDto;
import mycode.dxa.classes.dtos.DanceClassResponse;
import mycode.dxa.classes.models.DanceClass;
import mycode.dxa.classes.repository.DanceClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AttendanceQueryServiceImpl implements AttendanceQueryService {

    private final AttendanceRepository attendanceRepository;
    private final DanceClassRepository danceClassRepository; // Avem nevoie de repo-ul de cursuri

    public AttendanceQueryServiceImpl(AttendanceRepository attendanceRepository, DanceClassRepository danceClassRepository) {
        this.attendanceRepository = attendanceRepository;
        this.danceClassRepository = danceClassRepository;
    }

    @Override
    public boolean isStudentPresent(Long studentId, Long classId, LocalDate date) {
        return attendanceRepository.findByStudentIdAndDanceClassIdAndDate(studentId, classId, date)
                .map(Attendance::isPresent)
                .orElse(false);
    }

    @Override
    public DanceClassResponse getClassDetailsWithAttendance(Long classId, LocalDate date) {
        DanceClass danceClass = danceClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Curs nu exista"));
        List<ClassStudentDto> studentDtos = danceClass.getEnrollments().stream()
                .map(enrollment -> {
                    var s = enrollment.getStudent();
                    boolean isPresent = isStudentPresent(s.getId(), classId, date);
                    return new ClassStudentDto(s.getId(), s.getFirstName() + " " + s.getLastName(), isPresent);
                })
                .toList();
        return new DanceClassResponse(
                danceClass.getId(),
                danceClass.getTitle(),
                danceClass.getDescription(),
                danceClass.getSchedule(),
                danceClass.getLocation(),
                studentDtos
        );
    }
}